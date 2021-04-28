package com.yeetmanlord.enderite.core.enums;

import java.util.function.Supplier;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.init.ItemInit;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ArmorMaterials implements IArmorMaterial 
{
	UNCHARGED_ENDERITE(Enderite.MOD_ID + ":enderite",41 , new int[] {4,6,8,3}, 19, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.5f, 0.15f,() -> 
		{return Ingredient.fromItems(ItemInit.UNCHARGED_ENDERITE_INGOT.get(),ItemInit.UNCHARGED_ENDERITE_SHARD.get()); }),
	
	ENDERITE(Enderite.MOD_ID + ":energized_enderite",47 ,new int[] {5,8,8,5}, 23, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 5.0f, 0.2f, () -> 
		{return Ingredient.fromItems(ItemInit.ENDERITE_INGOT.get(),ItemInit.ENDERITE_SHARD.get()); });

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	   private final String name;
	   private final int maxDamageFactor;
	   private final int[] damageReductionAmountArray;
	   private final int enchantability;
	   private final SoundEvent soundEvent;
	   private final float toughness;
	   private final float knockbackResistance;
	   private final LazyValue<Ingredient> repairMaterial;

	   private ArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
	      this.name = name;
	      this.maxDamageFactor = maxDamageFactor;
	      this.damageReductionAmountArray = damageReductionAmountArray;
	      this.enchantability = enchantability;
	      this.soundEvent = soundEvent;
	      this.toughness = toughness;
	      this.knockbackResistance = knockbackResistance;
	      this.repairMaterial = new LazyValue<>(repairMaterial);
	   }

	   public int getDurability(EquipmentSlotType slotIn) {
	      return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	   }

	   public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	      return this.damageReductionAmountArray[slotIn.getIndex()];
	   }

	   public int getEnchantability() {
	      return this.enchantability;
	   }

	   public SoundEvent getSoundEvent() {
	      return this.soundEvent;
	   }

	   public Ingredient getRepairMaterial() {
	      return this.repairMaterial.getValue();
	   }

	   @OnlyIn(Dist.CLIENT)
	   public String getName() {
	      return this.name;
	   }

	   public float getToughness() {
	      return this.toughness;
	   }

	   public float getKnockbackResistance() {
	      return this.knockbackResistance;
	   }
}
