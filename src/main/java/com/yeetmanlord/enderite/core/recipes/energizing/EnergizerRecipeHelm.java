package com.yeetmanlord.enderite.core.recipes.energizing;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.init.ItemInit;
import com.yeetmanlord.enderite.core.recipes.EnergizerRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class EnergizerRecipeHelm extends EnergizerRecipe
{

	public EnergizerRecipeHelm(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float xp, int cookTime)
	{
		super(id, group, ingredient, result, xp);
	}
	
	public EnergizerRecipeHelm()
	{
		this(new ResourceLocation("energize_helmet"), Enderite.MOD_ID + ":energizing", Ingredient.fromItems(ItemInit.UNCHARGED_ENDERITE_HELM.get()), new ItemStack(ItemInit.ENDERITE_HELM.get()), 2.5f, cookTime);
	}
	
}
