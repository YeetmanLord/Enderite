package com.yeetmanlord.enderite.core.enums;

import com.google.common.base.Supplier;
import com.yeetmanlord.enderite.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ItemTiers implements IItemTier
{
	UNCHARGED_ENDERITE(5, 2645, 10.5f, 5f, 19, () -> {return Ingredient.fromItems(ItemInit.UNCHARGED_ENDERITE_INGOT.get(),ItemInit.UNCHARGED_ENDERITE_SHARD.get()); }),
	
	
	ENDERITE(5, 3457, 12.0f, 6f, 23, () -> {return Ingredient.fromItems(ItemInit.ENDERITE_INGOT.get(),ItemInit.ENDERITE_SHARD.get()); });
	
	
	private final int harvestLevel;
	   private final int maxUses;
	   private final float efficiency;
	   private final float attackDamage;
	   private final int enchantability;
	   private final LazyValue<Ingredient> repairMaterial;

	   private ItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
	      this.harvestLevel = harvestLevelIn;
	      this.maxUses = maxUsesIn;
	      this.efficiency = efficiencyIn;
	      this.attackDamage = attackDamageIn;
	      this.enchantability = enchantabilityIn;
	      this.repairMaterial = new LazyValue<>(repairMaterialIn);
	   }

	   public int getMaxUses() {
		      return this.maxUses;
		   }

		   public float getEfficiency() {
		      return this.efficiency;
		   }

		   public float getAttackDamage() {
		      return this.attackDamage;
		   }

		   public int getHarvestLevel() {
		      return this.harvestLevel;
		   }

		   public int getEnchantability() {
		      return this.enchantability;
		   }

		   public Ingredient getRepairMaterial() {
		      return this.repairMaterial.getValue();
		   }
}
