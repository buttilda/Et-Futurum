package ganymedes01.etfuturum.recipes;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.Stone;
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

		if (EtFuturum.enablePrismarine) {
			OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarine, 1, 0));
			OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarine, 1, 1));
			OreDictionary.registerOre("blockPrismarine", new ItemStack(ModBlocks.prismarine, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (EtFuturum.enableStones) {
			OreDictionary.registerOre("stoneGranite", new ItemStack(ModBlocks.stone, 1, Stone.GRANITE));
			OreDictionary.registerOre("stoneDiorite", new ItemStack(ModBlocks.stone, 1, Stone.DIORITE));
			OreDictionary.registerOre("stoneAndesite", new ItemStack(ModBlocks.stone, 1, Stone.ANDESITE));
			OreDictionary.registerOre("stoneGranitePolished", new ItemStack(ModBlocks.stone, 1, Stone.POLISHED_GRANITE));
			OreDictionary.registerOre("stoneDioritePolished", new ItemStack(ModBlocks.stone, 1, Stone.POLISHED_DIORITE));
			OreDictionary.registerOre("stoneAndesitePolished", new ItemStack(ModBlocks.stone, 1, Stone.POLISHED_ANDESITE));
		}

		if (EtFuturum.enableSlimeBlock)
			OreDictionary.registerOre("blockSlime", new ItemStack(ModBlocks.slime));

		if (EtFuturum.enableIronTrapdoor)
			OreDictionary.registerOre("trapdoorIron", ModBlocks.iron_trapdoor);
	}

	private static void registerItemRecipes() {
	}

	private static void registerBlockRecipes() {
	}

	private static void add18Recipes() {
		addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
		addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.vine));
		addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 5));
		GameRegistry.addSmelting(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 0.0F);

		if (EtFuturum.enableSlimeBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.slime), "xxx", "xxx", "xxx", 'x', "slimeball");
			addShapelessRecipe(new ItemStack(Items.slime_ball, 9), ModBlocks.slime);
		}

		if (EtFuturum.enableCoarseDirt)
			addShapedRecipe(new ItemStack(ModBlocks.coarse_dirt, 4), "xy", "yx", 'x', new ItemStack(Blocks.dirt), 'y', new ItemStack(Blocks.gravel));

		if (EtFuturum.enableMutton)
			GameRegistry.addSmelting(ModItems.raw_mutton, new ItemStack(ModItems.cooked_mutton), 1.0F);

		if (EtFuturum.enableIronTrapdoor)
			addShapedRecipe(new ItemStack(ModBlocks.iron_trapdoor), "xx", "xx", 'x', "ingotIron");

		if (EtFuturum.enableStones) {
			// Diorite
			addShapedRecipe(new ItemStack(ModBlocks.stone, 2, Stone.DIORITE), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', "gemQuartz");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, Stone.POLISHED_DIORITE), "xx", "xx", 'x', "stoneDiorite");
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.stone, 2, Stone.ANDESITE), new ItemStack(Blocks.cobblestone), "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, Stone.POLISHED_ANDESITE), "xx", "xx", 'x', "stoneAndesite");
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.stone, 2, Stone.GRANITE), "gemQuartz", "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, Stone.POLISHED_GRANITE), "xx", "xx", 'x', "stoneGranite");
		}

		if (EtFuturum.enablePrismarine) {
			int PLAIN = 0;
			int BRICKS = 1;
			int DARK = 2;

			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, DARK), "xxx", "xyx", "xxx", 'x', "shardPrismarine", 'y', "dyeBlack");
			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, PLAIN), "xx", "xx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, BRICKS), "xxx", "xxx", "xxx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.sea_lantern), "xyx", "yyy", "xyx", 'x', "shardPrismarine", 'y', "crystalPrismarine");
		}

		if (EtFuturum.enableDoors) {
			for (int i = 0; i < ModBlocks.doors.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.doors[i], 3), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Items.wooden_door, 3), "xx", "xx", "xx", 'x', "plankWood");
			addShapedRecipe(new ItemStack(Items.iron_door, 3), "xx", "xx", "xx", 'x', "ingotIron");
		}

		if (EtFuturum.enableRedSandstone) {
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.red_sandstone_slab));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone, 1, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.red_sandstone));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone_slab, 6), "xxx", 'x', ModBlocks.red_sandstone);
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone_stairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.red_sandstone);
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