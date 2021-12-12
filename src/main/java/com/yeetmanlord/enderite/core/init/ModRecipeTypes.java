package com.yeetmanlord.enderite.core.init;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.recipes.AbstractEnergizerRecipe;
import com.yeetmanlord.enderite.core.recipes.AbstractEnergizerRecipe.ModRecipeSerializer;
import com.yeetmanlord.enderite.core.recipes.EnergizerRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {
	
	public static class Types {
		public static final IRecipeType<EnergizerRecipe> ENERGIZING = IRecipeType.register(Enderite.MOD_ID + ":energizing");
	}
	
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Enderite.MOD_ID);
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZING_SERIALIZER = RECIPES.register("energizing", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipe::new, 1000));
}
