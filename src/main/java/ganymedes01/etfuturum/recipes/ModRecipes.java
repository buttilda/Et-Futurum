package ganymedes01.etfuturum.recipes;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.Stones18;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.EnumColour;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {

	public static String[] dyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };

	public static void init() {
		if (EtFuturum.enableBanners) {
			RecipeSorter.register("RecipeDuplicatePattern", RecipeDuplicatePattern.class, Category.SHAPELESS, "after:minecraft:shapeless");
			RecipeSorter.register("RecipeAddPattern", RecipeAddPattern.class, Category.SHAPED, "after:minecraft:shaped");
		}

		registerOreDictionary();

		registerBlockRecipes();
		registerItemRecipes();

		add18Recipes();

		tweakRecipes();
	}

	private static void tweakRecipes() {
		if (EtFuturum.enableDoors) {
			Items.wooden_door.setMaxStackSize(64);
			Items.iron_door.setMaxStackSize(64);
			Items.wooden_door.setTextureName(Utils.getItemTexture("door_wood"));
			Items.iron_door.setTextureName(Utils.getItemTexture("door_iron"));
			removeFirstRecipeFor(Items.wooden_door);
			removeFirstRecipeFor(Items.iron_door);
		}

		if (EtFuturum.enableFences) {
			removeFirstRecipeFor(Blocks.fence);
			removeFirstRecipeFor(Blocks.fence_gate);
			Blocks.fence.setCreativeTab(null);
			Blocks.fence_gate.setCreativeTab(null);
		}

		if (EtFuturum.enableBurnableBlocks) {
			Blocks.fire.setFireInfo(Blocks.fence_gate, 5, 20);
			Blocks.fire.setFireInfo(Blocks.fence, 5, 20);
			Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
		}
	}

	private static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));
		OreDictionary.registerOre("trapdoorWood", Blocks.trapdoor);

		if (EtFuturum.enablePrismarineStuff) {
			OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarineItems, 1, 0));
			OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarineItems, 1, 1));
			OreDictionary.registerOre("blockPrismarine", new ItemStack(ModBlocks.prismarineBlocks, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (EtFuturum.enable18Stones) {
			OreDictionary.registerOre("stoneGranite", new ItemStack(ModBlocks.newStones, 1, Stones18.GRANITE));
			OreDictionary.registerOre("stoneDiorite", new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE));
			OreDictionary.registerOre("stoneAndesite", new ItemStack(ModBlocks.newStones, 1, Stones18.ANDESITE));
			OreDictionary.registerOre("stoneGranitePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_GRANITE));
			OreDictionary.registerOre("stoneDioritePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_DIORITE));
			OreDictionary.registerOre("stoneAndesitePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_ANDESITE));
		}

		if (EtFuturum.enableSlimeBlock)
			OreDictionary.registerOre("blockSlime", new ItemStack(ModBlocks.slimeBlock));

		if (EtFuturum.enableBeetroots)
			OreDictionary.registerOre("cropBeetroot", ModItems.beetroot);

		if (EtFuturum.enableIronTrapdoor)
			OreDictionary.registerOre("trapdoorIron", ModBlocks.ironTrapdoor);
	}

	private static void registerItemRecipes() {
		if (EtFuturum.enableBeetroots) {
			addShapelessRecipe(new ItemStack(ModItems.beetrootSoup), "cropBeetroot", "cropBeetroot", "cropBeetroot", "cropBeetroot", Items.bowl);
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), "cropBeetroot");
		}
	}

	private static void registerBlockRecipes() {
	}

	private static void add18Recipes() {
		addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
		addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.vine));
		addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 5));
		GameRegistry.addSmelting(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 0.0F);

		if (EtFuturum.enableSlimeBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.slimeBlock), "xxx", "xxx", "xxx", 'x', "slimeball");
			addShapelessRecipe(new ItemStack(Items.slime_ball, 9), ModBlocks.slimeBlock);
		}

		if (EtFuturum.enableCoarseDirt)
			addShapedRecipe(new ItemStack(ModBlocks.coarseDirt, 4), "xy", "yx", 'x', new ItemStack(Blocks.dirt), 'y', new ItemStack(Blocks.gravel));

		if (EtFuturum.enableMutton)
			GameRegistry.addSmelting(ModItems.rawMutton, new ItemStack(ModItems.cookedMutton), 1.0F);

		if (EtFuturum.enableIronTrapdoor)
			addShapedRecipe(new ItemStack(ModBlocks.ironTrapdoor), "xx", "xx", 'x', "ingotIron");

		if (EtFuturum.enable18Stones) {
			// Diorite
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.DIORITE), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', "gemQuartz");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_DIORITE), "xx", "xx", 'x', "stoneDiorite");
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.ANDESITE), new ItemStack(Blocks.cobblestone), "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_ANDESITE), "xx", "xx", 'x', "stoneAndesite");
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.GRANITE), "gemQuartz", "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_GRANITE), "xx", "xx", 'x', "stoneGranite");
		}

		if (EtFuturum.enablePrismarineStuff) {
			int PLAIN = 0;
			int BRICKS = 1;
			int DARK = 2;

			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, DARK), "xxx", "xyx", "xxx", 'x', "shardPrismarine", 'y', "dyeBlack");
			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, PLAIN), "xx", "xx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, BRICKS), "xxx", "xxx", "xxx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.seaLantern), "xyx", "yyy", "xyx", 'x', "shardPrismarine", 'y', "crystalPrismarine");
		}

		if (EtFuturum.enableDoors) {
			for (int i = 0; i < ModItems.doors.length; i++)
				addShapedRecipe(new ItemStack(ModItems.doors[i], 3), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Items.wooden_door, 3), "xx", "xx", "xx", 'x', "plankWood");
			addShapedRecipe(new ItemStack(Items.iron_door, 3), "xx", "xx", "xx", 'x', "ingotIron");
		}

		if (EtFuturum.enableRedSandstone) {
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.redSandstoneSlab));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.redSandstone));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneSlab, 6), "xxx", 'x', ModBlocks.redSandstone);
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.redSandstone);
		}

		if (EtFuturum.enableFences) {
			for (int i = 0; i < ModBlocks.fences.length; i++) {
				Object wood = i == 0 ? "plankWood" : new ItemStack(Blocks.planks, 1, i);
				addShapedRecipe(new ItemStack(ModBlocks.fences[i], 3), "xyx", "xyx", 'x', wood, 'y', "stickWood");
			}
			addShapelessRecipe(new ItemStack(Blocks.fence), ModBlocks.fences[0]);
			addShapelessRecipe(new ItemStack(ModBlocks.fences[0]), Blocks.fence);

			for (int i = 0; i < ModBlocks.gates.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.gates[i]), "yxy", "yxy", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			addShapedRecipe(new ItemStack(Blocks.fence_gate), "yxy", "yxy", 'x', "plankWood", 'y', "stickWood");
		}

		if (EtFuturum.enableBanners) {
			for (EnumColour colour : EnumColour.values())
				addShapedRecipe(new ItemStack(ModBlocks.banner, 1, colour.getDamage()), new Object[] { "xxx", "xxx", " y ", 'x', new ItemStack(Blocks.wool, 1, colour.getDamage()), 'y', "stickWood" });
			GameRegistry.addRecipe(new RecipeDuplicatePattern());
			GameRegistry.addRecipe(new RecipeAddPattern());
		}
	}

	private static void addShapedRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
	}

	private static void addShapelessRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, objects));
	}

	private static void removeFirstRecipeFor(Block block) {
		removeFirstRecipeFor(Item.getItemFromBlock(block));
	}

	private static void removeFirstRecipeFor(Item item) {
		for (Object recipe : CraftingManager.getInstance().getRecipeList())
			if (recipe != null) {
				ItemStack stack = ((IRecipe) recipe).getRecipeOutput();
				if (stack != null && stack.getItem() == item) {
					CraftingManager.getInstance().getRecipeList().remove(recipe);
					return;
				}
			}
	}
}