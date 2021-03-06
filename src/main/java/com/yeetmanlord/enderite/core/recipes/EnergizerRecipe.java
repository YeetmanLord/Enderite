package com.yeetmanlord.enderite.core.recipes;

import com.yeetmanlord.enderite.core.init.BlockInit;
import com.yeetmanlord.enderite.core.init.ModRecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class EnergizerRecipe extends AbstractEnergizerRecipe 
{
	
	public static int cookTime = 1000;
	

	public EnergizerRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float xp, int time) {
		super(ModRecipeTypes.Types.ENERGIZING, ModRecipeTypes.ENERGIZING_SERIALIZER.get(), id, group, ingredient, xp, 1000, result);
	}
	
	
	public ItemStack getIcon() {
	      return new ItemStack(BlockInit.ENERGIZER_BLOCK.get());
	}
}
