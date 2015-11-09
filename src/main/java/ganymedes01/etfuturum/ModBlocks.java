package ganymedes01.etfuturum;

import java.lang.reflect.Field;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.blocks.BlockBanner;
import ganymedes01.etfuturum.blocks.BlockBeetroot;
import ganymedes01.etfuturum.blocks.BlockRose;
import ganymedes01.etfuturum.blocks.BlockSilkedMushroom;
import ganymedes01.etfuturum.blocks.BlockWoodDoor;
import ganymedes01.etfuturum.blocks.BlockWoodFence;
import ganymedes01.etfuturum.blocks.BlockWoodFenceGate;
import ganymedes01.etfuturum.blocks.ChorusFlower;
import ganymedes01.etfuturum.blocks.ChorusPlant;
import ganymedes01.etfuturum.blocks.CoarseDirt;
import ganymedes01.etfuturum.blocks.CryingObsidian;
import ganymedes01.etfuturum.blocks.EndBricks;
import ganymedes01.etfuturum.blocks.EndRod;
import ganymedes01.etfuturum.blocks.FrostedIce;
import ganymedes01.etfuturum.blocks.GrassPath;
import ganymedes01.etfuturum.blocks.InvertedDaylightDetector;
import ganymedes01.etfuturum.blocks.IronTrapdoor;
import ganymedes01.etfuturum.blocks.NewBrewingStand;
import ganymedes01.etfuturum.blocks.OldGravel;
import ganymedes01.etfuturum.blocks.PrismarineBlocks;
import ganymedes01.etfuturum.blocks.PurpurBlock;
import ganymedes01.etfuturum.blocks.PurpurPillar;
import ganymedes01.etfuturum.blocks.PurpurSlab;
import ganymedes01.etfuturum.blocks.PurpurStairs;
import ganymedes01.etfuturum.blocks.RedSandstone;
import ganymedes01.etfuturum.blocks.RedSandstoneSlab;
import ganymedes01.etfuturum.blocks.RedSandstoneStairs;
import ganymedes01.etfuturum.blocks.SeaLantern;
import ganymedes01.etfuturum.blocks.SlimeBlock;
import ganymedes01.etfuturum.blocks.Sponge;
import ganymedes01.etfuturum.blocks.Stone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

public class ModBlocks {

	public static final Block stone = new Stone();
	public static final Block iron_trapdoor = new IronTrapdoor();
	public static final Block prismarine = new PrismarineBlocks();
	public static final Block sea_lantern = new SeaLantern();
	public static final Block inverted_daylight_detector = new InvertedDaylightDetector();
	public static final Block red_sandstone = new RedSandstone();
	public static final Block red_sandstone_slab = new RedSandstoneSlab();
	public static final Block red_sandstone_stairs = new RedSandstoneStairs();
	public static final Block brown_mushroom_block = new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown");
	public static final Block red_mushroom_block = new BlockSilkedMushroom(Blocks.red_mushroom_block, "red");
	public static final Block coarse_dirt = new CoarseDirt();
	public static final Block banner = new BlockBanner();
	public static final Block slime = new SlimeBlock();
	public static final Block sponge = new Sponge();
	public static final Block old_gravel = new OldGravel();
	public static final Block beetroot = new BlockBeetroot();
	public static final Block purpur_block = new PurpurBlock();
	public static final Block purpur_pillar = new PurpurPillar();
	public static final Block purpur_stairs = new PurpurStairs();
	public static final Block purpur_slab = new PurpurSlab();
	public static final Block end_bricks = new EndBricks();
	public static final Block grass_path = new GrassPath();
	public static final Block end_rod = new EndRod();
	public static final Block chorus_plant = new ChorusPlant();
	public static final Block chorus_flower = new ChorusFlower();
	public static final Block crying_obsidian = new CryingObsidian();
	public static final Block frosted_ice = new FrostedIce();
	public static final Block brewing_stand = new NewBrewingStand();
	public static final Block rose = new BlockRose();

	public static final Block[] doors = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block[] fences = new Block[BlockWood.field_150096_a.length];
	public static final Block[] gates = new Block[BlockWood.field_150096_a.length - 1];

	static {
		for (int i = 0; i < doors.length; i++)
			doors[i] = new BlockWoodDoor(i + 1);

		for (int i = 0; i < fences.length; i++)
			fences[i] = new BlockWoodFence(i);

		for (int i = 0; i < gates.length; i++)
			gates[i] = new BlockWoodFenceGate(i + 1);
	}

	public static void init() {
		try {
			for (Field f : ModBlocks.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Block)
					registerBlock((Block) obj);
				else if (obj instanceof Block[])
					for (Block block : (Block[]) obj)
						if (block != null)
							registerBlock(block);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerBlock(Block block) {
		if (!(block instanceof IConfigurable) || ((IConfigurable) block).isEnabled()) {
			String name = block.getUnlocalizedName();
			String[] strings = name.split("\\.");

			if (block instanceof ISubBlocksBlock)
				GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), strings[strings.length - 1]);
			else
				GameRegistry.registerBlock(block, strings[strings.length - 1]);

			if (block instanceof IBurnableBlock)
				Blocks.fire.setFireInfo(block, 5, 20);
		}
	}

	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();
	}

	public static interface IBurnableBlock {
	}
}