package ganymedes01.etfuturum.client.skins;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

/**
 * Copied from vanilla and adapted to fit my needs
 */
@SideOnly(Side.CLIENT)
public class NewThreadDownloadImageData extends SimpleTexture {

	private static final Logger logger = LogManager.getLogger();
	private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
	private final File field_152434_e;
	private final String imageUrl;
	private final IImageBuffer imageBuffer;
	private BufferedImage bufferedImage;
	private Thread imageThread;
	private boolean textureUploaded;
	private NewImageBufferDownload imgDownload;
	private ResourceLocation resLocationOld;

	public NewThreadDownloadImageData(File file, String imageUrl, ResourceLocation texture, NewImageBufferDownload imgDownload, ResourceLocation resLocationOld, IImageBuffer imageBuffer) {
		super(texture);
		field_152434_e = file;
		this.imageUrl = imageUrl;
		this.imgDownload = imgDownload;
		this.resLocationOld = resLocationOld;
		this.imageBuffer = imageBuffer;
	}

	private void checkTextureUploaded() {
		if (!textureUploaded)
			if (bufferedImage != null) {
				if (textureLocation != null)
					deleteGlTexture();

				TextureUtil.uploadTextureImage(super.getGlTextureId(), bufferedImage);
				if (imgDownload != null) {
					BufferedImage oldStyleImg = imgDownload.getOldSyleImage();
					Minecraft.getMinecraft().getTextureManager().loadTexture(resLocationOld, new DynamicTexture(oldStyleImg));
				}
				textureUploaded = true;
			}
	}

	@Override
	public int getGlTextureId() {
		checkTextureUploaded();
		return super.getGlTextureId();
	}

	public void setBufferedImage(BufferedImage p_147641_1_) {
		bufferedImage = p_147641_1_;

		if (imageBuffer != null)
			imageBuffer.func_152634_a();
	}

	@Override
	public void loadTexture(IResourceManager p_110551_1_) throws IOException {
		if (bufferedImage == null && textureLocation != null)
			super.loadTexture(p_110551_1_);

		if (imageThread == null)
			if (field_152434_e != null && field_152434_e.isFile()) {
				logger.debug("Loading http texture from local cache ({})", new Object[] { field_152434_e });

				try {
					bufferedImage = ImageIO.read(field_152434_e);

					if (imageBuffer != null)
						setBufferedImage(imageBuffer.parseUserSkin(bufferedImage));
				} catch (IOException ioexception) {
					logger.error("Couldn\'t load skin " + field_152434_e, ioexception);
					func_152433_a();
				}
			} else
				func_152433_a();
	}

	protected void func_152433_a() {
		imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet()) {

			@Override
			public void run() {
				HttpURLConnection httpurlconnection = null;
				NewThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] { imageUrl, field_152434_e });

				try {
					httpurlconnection = (HttpURLConnection) new URL(imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
					httpurlconnection.setDoInput(true);
					httpurlconnection.setDoOutput(false);
					httpurlconnection.connect();

					if (httpurlconnection.getResponseCode() / 100 == 2) {
						BufferedImage bufferedimage;

						if (field_152434_e != null) {
							FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), field_152434_e);
							bufferedimage = ImageIO.read(field_152434_e);
						} else
							bufferedimage = ImageIO.read(httpurlconnection.getInputStream());

						if (imageBuffer != null)
							bufferedimage = imageBuffer.parseUserSkin(bufferedimage);

						NewThreadDownloadImageData.this.setBufferedImage(bufferedimage);
						return;
					}
				} catch (Exception exception) {
					NewThreadDownloadImageData.logger.error("Couldn\'t download http texture", exception);
					return;
				} finally {
					if (httpurlconnection != null)
						httpurlconnection.disconnect();
				}
			}
		};
		imageThread.setDaemon(true);
		imageThread.start();
	}
}