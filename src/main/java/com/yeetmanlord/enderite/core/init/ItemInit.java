package com.yeetmanlord.enderite.core.init;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.Enderite.ItemsItemGroup;
import com.yeetmanlord.enderite.common.objects.blockitems.GlintedBlockItem;
import com.yeetmanlord.enderite.common.objects.items.GlintedItem;
import com.yeetmanlord.enderite.core.enums.ArmorMaterials;
import com.yeetmanlord.enderite.core.enums.ItemTiers;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	public static final DeferredRegister<Item>
		ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Enderite.MOD_ID);
	
	
// 	ITEMS
	public static final RegistryObject<Item> ENDERITE_SHARD =
			ITEMS.register("energized_enderite_shard", () -> new GlintedItem(new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire()));
	
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_SHARD =
			ITEMS.register("enderite_shard", () -> new Item(new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire()));
	
	
	public static final RegistryObject<Item> ENDERITE_INGOT =
			ITEMS.register("energized_enderite_ingot", () -> new GlintedItem(new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire()));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_INGOT =
			ITEMS.register("enderite_ingot", () -> new Item(new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire()));
	
	
//	BLOCKITEMS
	public static final RegistryObject<BlockItem> ENDERITE_ORE =
			ITEMS.register("energized_enderite_ore", () -> new GlintedBlockItem(BlockInit.ENDERITE_ORE.get(), new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire()));
	
	public static final RegistryObject<BlockItem> UNCHARGED_ENDERITE_ORE =
			ITEMS.register("enderite_ore", () -> new BlockItem(BlockInit.UNCHARGED_ENDERITE_ORE.get(), new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire()));
	
	public static final RegistryObject<BlockItem> ENERGIZER =
			ITEMS.register("energizer", () -> new BlockItem(BlockInit.ENERGIZER_BLOCK.get(), new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON)));
	
	
//	TOOLS
	
	public static final RegistryObject<Item> ENDERITE_PICKAXE =
			ITEMS.register("energized_enderite_pickaxe", () -> new PickaxeItem(ItemTiers.ENDERITE, 1, -2.65f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	
	
	public static final RegistryObject<Item> ENDERITE_HOE =
			ITEMS.register("energized_enderite_hoe", () -> new HoeItem(ItemTiers.ENDERITE, -6, 0.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));

	
	public static final RegistryObject<Item> ENDERITE_SHOVEL =
			ITEMS.register("energized_enderite_shovel", () -> new ShovelItem(ItemTiers.ENDERITE, 1.5f, -3.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	
	public static final RegistryObject<Item> ENDERITE_SWORD =
			ITEMS.register("energized_enderite_sword", () -> new SwordItem(ItemTiers.ENDERITE, 3, -2.1f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	public static final RegistryObject<Item> ENDERITE_AXE =
			ITEMS.register("energized_enderite_axe", () -> new AxeItem(ItemTiers.ENDERITE, 5.0f, -3.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_PICKAXE =
			ITEMS.register("enderite_pickaxe", () -> new PickaxeItem(ItemTiers.UNCHARGED_ENDERITE, 1, -2.65f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_HOE =
			ITEMS.register("enderite_hoe", () -> new HoeItem(ItemTiers.UNCHARGED_ENDERITE, -5, 0.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_SWORD =
			ITEMS.register("enderite_sword", () -> new SwordItem(ItemTiers.UNCHARGED_ENDERITE, 3, -2.3f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_AXE =
			ITEMS.register("enderite_axe", () -> new AxeItem(ItemTiers.UNCHARGED_ENDERITE, 5.0f, -3.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_SHOVEL =
			ITEMS.register("enderite_shovel", () -> new ShovelItem(ItemTiers.UNCHARGED_ENDERITE, 1.5f, -3.0f, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	
//	ARMOR
	
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_HELM = 
			ITEMS.register("enderite_helmet", () -> new ArmorItem(ArmorMaterials.UNCHARGED_ENDERITE, EquipmentSlotType.HEAD, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_CHEST = 
			ITEMS.register("enderite_chestplate", () -> new ArmorItem(ArmorMaterials.UNCHARGED_ENDERITE, EquipmentSlotType.CHEST, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	public static final RegistryObject<Item> UNCHARGED_ENDERITE_LEGS = 
			ITEMS.register("enderite_leggings", () -> new ArmorItem(ArmorMaterials.UNCHARGED_ENDERITE, EquipmentSlotType.LEGS, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	

	public static final RegistryObject<Item> UNCHARGED_ENDERITE_BOOTS = 
			ITEMS.register("enderite_boots", () -> new ArmorItem(ArmorMaterials.UNCHARGED_ENDERITE, EquipmentSlotType.FEET, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.UNCOMMON).isImmuneToFire())));
	
	
	
	public static final RegistryObject<Item> ENDERITE_HELM = 
			ITEMS.register("energized_enderite_helmet", () -> new ArmorItem(ArmorMaterials.ENDERITE, EquipmentSlotType.HEAD, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	public static final RegistryObject<Item> ENDERITE_CHEST = 
			ITEMS.register("energized_enderite_chestplate", () -> new ArmorItem(ArmorMaterials.ENDERITE, EquipmentSlotType.CHEST, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	
	public static final RegistryObject<Item> ENDERITE_LEGS = 
			ITEMS.register("energized_enderite_leggings", () -> new ArmorItem(ArmorMaterials.ENDERITE, EquipmentSlotType.LEGS, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
	
	public static final RegistryObject<Item> ENDERITE_BOOTS = 
			ITEMS.register("energized_enderite_boots", () -> new ArmorItem(ArmorMaterials.ENDERITE, EquipmentSlotType.FEET, (new Item.Properties().group(ItemsItemGroup.instance).rarity(Rarity.RARE).isImmuneToFire())));
}
