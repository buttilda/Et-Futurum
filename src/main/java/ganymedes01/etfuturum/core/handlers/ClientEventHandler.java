package ganymedes01.etfuturum.core.handlers;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.OpenGLHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

	@SubscribeEvent
	public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
		if (EtFuturum.enableTransparentAmour)
			OpenGLHelper.enableBlend();
	}

	@SubscribeEvent
	public void renderPlayerSetArmour(SetArmorModel event) {
		if (EtFuturum.enableTransparentAmour)
			OpenGLHelper.enableBlend();
	}

	@SubscribeEvent
	public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
		if (EtFuturum.enableTransparentAmour)
			OpenGLHelper.disableBlend();
	}
}