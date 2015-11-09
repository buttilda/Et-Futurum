package ganymedes01.etfuturum.tileentities;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityNewBeacon extends TileEntityBeacon {

	private final List<BeamSegment> segments = Lists.newArrayList();

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		int height = worldObj == null ? 256 : worldObj.getActualHeight();
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, height, zCoord + 1);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (worldObj.isRemote && worldObj.getTotalWorldTime() % 80L == 0L)
			updateSegments();
	}

	private void updateSegments() {
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		segments.clear();
		TileEntityNewBeacon.BeamSegment beamsegment = new TileEntityNewBeacon.BeamSegment(EntitySheep.fleeceColorTable[0]);
		segments.add(beamsegment);
		boolean flag = true;

		for (int i = y + 1; i < worldObj.getActualHeight(); i++) {
			Block iblockstate = worldObj.getBlock(x, i, z);
			float[] colours;

			if (iblockstate == Blocks.stained_glass)
				colours = EntitySheep.fleeceColorTable[worldObj.getBlockMetadata(x, i, z)];
			else {
				if (iblockstate != Blocks.stained_glass_pane) {
					if (iblockstate.getLightOpacity() >= 15) {
						segments.clear();
						break;
					}

					beamsegment.func_177262_a();
					continue;
				}

				colours = EntitySheep.fleeceColorTable[worldObj.getBlockMetadata(x, i, z)];
			}

			if (!flag)
				colours = new float[] { (beamsegment.func_177263_b()[0] + colours[0]) / 2.0F, (beamsegment.func_177263_b()[1] + colours[1]) / 2.0F, (beamsegment.func_177263_b()[2] + colours[2]) / 2.0F };

			if (Arrays.equals(colours, beamsegment.func_177263_b()))
				beamsegment.func_177262_a();
			else {
				beamsegment = new TileEntityNewBeacon.BeamSegment(colours);
				segments.add(beamsegment);
			}

			flag = false;
		}
	}

	public List<BeamSegment> getSegments() {
		return segments;
	}

	public static class BeamSegment {

		private final float[] colours;
		private int field_177265_b;

		public BeamSegment(float[] colours) {
			this.colours = colours;
			field_177265_b = 1;
		}

		protected void func_177262_a() {
			field_177265_b++;
		}

		public float[] func_177263_b() {
			return colours;
		}

		public int func_177264_c() {
			return field_177265_b;
		}
	}
}