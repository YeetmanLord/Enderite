package com.yeetmanlord.enderite.core.recipes.energizing;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.init.ItemInit;
import com.yeetmanlord.enderite.core.recipes.EnergizerRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class EnergizerRecipeBoots extends EnergizerRecipe
{

	public EnergizerRecipeBoots(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float xp, int cookTime) {
		super(id, group, ingredient, result, xp);
	}
	
	public EnergizerRecipeBoots()
	{
		this(new ResourceLocation("energize_boots"), Enderite.MOD_ID + ":energizing", Ingredient.fromItems(ItemInit.UNCHARGED_ENDERITE_BOOTS.get()), new ItemStack(ItemInit.ENDERITE_BOOTS.get()), 2.5f, cookTime);
	}
	
}
