package ganymedes01.etfuturum.core.handlers;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.PrismarineBlocks;
import ganymedes01.etfuturum.client.PrismarineIcon;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityEndermite;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.items.TippedArrow;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.lib.Reference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModEventHandler {

	@SubscribeEvent
	public void livingAttack(LivingAttackEvent event) {
		if (event.source instanceof EntityDamageSourceIndirect) {
			EntityDamageSourceIndirect dmgSrc = (EntityDamageSourceIndirect) event.source;
			if (dmgSrc.getSourceOfDamage() instanceof EntityTippedArrow) {
				EntityTippedArrow tippedArrow = (EntityTippedArrow) dmgSrc.getSourceOfDamage();
				if (!tippedArrow.worldObj.isRemote)
					event.entityLiving.addPotionEffect(tippedArrow.getEffect());
			}
		}
	}

	@SubscribeEvent
	public void arrowNock(ArrowNockEvent event) {
		if (event.result == null)
			return;
		IInventory invt = event.entityPlayer.inventory;
		for (int i = 0; i < invt.getSizeInventory(); i++) {
			ItemStack stack = invt.getStackInSlot(i);
			if (stack == null || stack.stackSize <= 0)
				continue;
			if (stack.getItem() == Items.arrow)
				return;
			if (stack.getItem() == ModItems.tipped_arrow) {
				event.setCanceled(true);
				event.entityPlayer.setItemInUse(event.result, event.result.getItem().getMaxItemUseDuration(event.result));
				return;
			}
		}
	}

	@SubscribeEvent
	public void arrowLoose(ArrowLooseEvent event) {
		if (event.bow == null)
			return;

		IInventory invt = event.entityPlayer.inventory;
		for (int i = 0; i < invt.getSizeInventory(); i++) {
			ItemStack arrow = invt.getStackInSlot(i);
			if (arrow != null && arrow.stackSize > 0 && arrow.getItem() == ModItems.tipped_arrow) {
				float charge = event.charge / 20.0F;
				charge = (charge * charge + charge * 2.0F) / 3.0F;

				if (charge < 0.1D)
					return;
				if (charge > 1.0F)
					charge = 1.0F;

				EntityTippedArrow arrowEntity = new EntityTippedArrow(event.entityPlayer.worldObj, event.entityPlayer, charge * 2.0F);
				arrowEntity.setEffect(TippedArrow.getEffect(arrow));

				if (charge == 1.0F)
					arrowEntity.setIsCritical(true);

				int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
				if (power > 0)
					arrowEntity.setDamage(arrowEntity.getDamage() + power * 0.5D + 0.5D);

				int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
				if (punch > 0)
					arrowEntity.setKnockbackStrength(punch);

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
					arrowEntity.setFire(100);

				event.bow.damageItem(1, event.entityPlayer);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.bow", 1.0F, 1.0F / (event.entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

				if (!event.entityPlayer.capabilities.isCreativeMode && --arrow.stackSize <= 0)
					event.entityPlayer.inventory.setInventorySlotContents(i, null);

				if (!event.entityPlayer.worldObj.isRemote)
					event.entityPlayer.worldObj.spawnEntityInWorld(arrowEntity);
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		if (EtFuturum.enablePrismarine)
			if (event.map.getTextureType() == 0) {
				TextureAtlasSprite icon = new PrismarineIcon("prismarine_rough");
				if (event.map.setTextureEntry("prismarine_rough", icon))
					((PrismarineBlocks) ModBlocks.prismarine).setIcon(0, icon);
				else
					((PrismarineBlocks) ModBlocks.prismarine).setIcon(0, event.map.registerIcon("prismarine_rough"));
			}
	}

	@SubscribeEvent
	public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		if (!EtFuturum.enableEnchants)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null) {
				int seed = Integer.parseInt(line);
				ContainerEnchantment.seeds.put(event.playerUUID, seed);
				br.close();
			}
		} catch (Exception e) {
		}
	}

	@SubscribeEvent
	public void onPlayerSaveFromFileEvent(PlayerEvent.SaveToFile event) {
		if (!EtFuturum.enableEnchants)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			Integer seed = ContainerEnchantment.seeds.get(event.playerUUID);
			if (seed != null) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(seed.toString());
				bw.close();
			}
		} catch (IOException e) {
		}
	}

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (EtFuturum.enableSilkTouchingMushrooms && event.isSilkTouching)
			if (event.block == Blocks.brown_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.brown_mushroom_block));
			} else if (event.block == Blocks.red_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.red_mushroom_block));
			}

		if (EtFuturum.enableSticksFromDeadBushes)
			if (event.block == Blocks.deadbush) {
				boolean isShears = event.harvester != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() instanceof ItemShears;
				if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null || !isShears)
					for (int i = 0; i < event.world.rand.nextInt(3); i++)
						event.drops.add(new ItemStack(Items.stick));
			}
	}

	@SubscribeEvent
	public void onPlayerInteract0(PlayerInteractEvent event) {
		if (!EtFuturum.enableEnchants)
			return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			World world = event.world;
			EntityPlayer player = event.entityPlayer;
			int x = event.x;
			int y = event.y;
			int z = event.z;

			if (world == null || world.isRemote)
				return;
			if (player.isSneaking())
				return;
			else {
				TileEntityEnchantmentTable tile = Utils.getTileEntity(world, x, y, z, TileEntityEnchantmentTable.class);
				if (tile != null && world.getBlock(x, y, z) == Blocks.enchanting_table) {
					player.openGui(EtFuturum.instance, GUIsID.ENCHANTING_TABLE, world, x, y, z);
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract1(PlayerInteractEvent event) {
		if (EtFuturum.enableInvertedDaylightSensor)
			if (event.entityPlayer != null) {
				World world = event.entityPlayer.worldObj;
				if (event.action == Action.RIGHT_CLICK_BLOCK)
					if (world.getBlock(event.x, event.y, event.z) == Blocks.daylight_detector) {
						world.setBlock(event.x, event.y, event.z, ModBlocks.inverted_daylight_detector);
						event.entityPlayer.swingItem();
					} else if (world.getBlock(event.x, event.y, event.z) == ModBlocks.inverted_daylight_detector) {
						world.setBlock(event.x, event.y, event.z, Blocks.daylight_detector);
						event.entityPlayer.swingItem();
					}
			}
	}

	@SubscribeEvent
	public void onPlayerInteract2(PlayerInteractEvent event) {
		if (EtFuturum.enableGrassPath)
			if (event.entityPlayer != null) {
				World world = event.entityPlayer.worldObj;
				if (event.action == Action.RIGHT_CLICK_BLOCK)
					if (world.getBlock(event.x, event.y, event.z) == Blocks.grass) {
						ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
						if (stack != null && stack.getItem() instanceof ItemSpade) {
							world.setBlock(event.x, event.y, event.z, ModBlocks.grass_path);
							event.entityPlayer.swingItem();
							stack.damageItem(1, event.entityPlayer);
							world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
						}
					}
			}
	}

	@SubscribeEvent
	public void onHoeUseEvent(UseHoeEvent event) {
		if (EtFuturum.enableCoarseDirt) {
			World world = event.world;
			if (world.getBlock(event.x, event.y, event.z) == ModBlocks.coarse_dirt) {
				world.setBlock(event.x, event.y, event.z, Blocks.dirt);
				world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void dropEvent(LivingDropsEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;

		if (EtFuturum.enableSkullDrop)
			dropHead(event.entityLiving, event.source, event.lootingLevel, event.drops);

		Random rand = event.entityLiving.worldObj.rand;
		if (EtFuturum.enableMutton && event.entityLiving instanceof EntitySheep) {
			int amount = rand.nextInt(3) + 1 + rand.nextInt(1 + event.lootingLevel);
			for (int i = 0; i < amount; i++)
				if (event.entityLiving.isBurning())
					addDrop(new ItemStack(ModItems.cooked_mutton), event.entityLiving, event.drops);
				else
					addDrop(new ItemStack(ModItems.raw_mutton), event.entityLiving, event.drops);
		}
	}

	private void dropHead(EntityLivingBase entity, DamageSource source, int looting, List<EntityItem> drops) {
		if (isPoweredCreeper(source)) {
			int meta = getHeadMetadata(entity);
			if (meta >= 0)
				addDrop(new ItemStack(Items.skull, 1, meta), entity, drops);
		}

	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.delayBeforeCanPickup = 10;
		list.add(entityItem);
	}

	private boolean isPoweredCreeper(DamageSource source) {
		if (source.isExplosion() && source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getEntity();
			if (entity != null && entity instanceof EntityCreeper)
				return ((EntityCreeper) entity).getPowered();
		}

		return false;
	}

	private int getHeadMetadata(EntityLivingBase entity) {
		if (entity.getClass() == EntityZombie.class)
			return 2;
		else if (entity.getClass() == EntitySkeleton.class && ((EntitySkeleton) entity).getSkeletonType() == 0)
			return 0;
		else if (entity.getClass() == EntityCreeper.class)
			return 4;

		return -1;
	}

	@SubscribeEvent
	public void teleportEvent(EnderTeleportEvent event) {
		if (EtFuturum.enableEndermite) {
			EntityLivingBase entity = event.entityLiving;
			if (entity instanceof EntityPlayerMP)
				if (entity.getRNG().nextFloat() < 0.05F && entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
					EntityEndermite entityendermite = new EntityEndermite(entity.worldObj);
					entityendermite.setSpawnedByPlayer(true);
					entityendermite.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
					entity.worldObj.spawnEntityInWorld(entityendermite);
				}
		}
	}
}