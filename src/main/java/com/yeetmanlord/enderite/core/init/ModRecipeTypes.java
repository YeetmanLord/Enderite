package com.yeetmanlord.enderite.core.init;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.recipes.*;
import com.yeetmanlord.enderite.core.recipes.AbstractEnergizerRecipe.ModRecipeSerializer;
import com.yeetmanlord.enderite.core.recipes.energizing.*;

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
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_SHARD = RECIPES.register("energize_shard", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeShard::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_AXE = RECIPES.register("energize_axe", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeAxe::new, 1000));
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_BOOTS = RECIPES.register("energize_boots", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeBoots::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_CHEST = RECIPES.register("energize_chest", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeChest::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_HELM = RECIPES.register("energize_helmet", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeHelm::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_HOE = RECIPES.register("energize_hoe", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeHoe::new, 1000));
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_INGOT = RECIPES.register("energize_ingot", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeIngot::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_LEGS = RECIPES.register("energize_leggings", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeLegs::new, 1000));
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_PICK = RECIPES.register("energize_pickaxe", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipePick::new, 1000));
	
	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_SHOVEL = RECIPES.register("energize_shovel", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeShovel::new, 1000));

	public static final RegistryObject<ModRecipeSerializer<AbstractEnergizerRecipe>> ENERGIZE_SWORD = RECIPES.register("energize_sword", () -> new AbstractEnergizerRecipe.ModRecipeSerializer<AbstractEnergizerRecipe> (EnergizerRecipeSword::new, 1000));
	
}
