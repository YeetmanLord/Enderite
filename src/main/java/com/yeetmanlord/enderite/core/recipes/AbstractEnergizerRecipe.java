package com.yeetmanlord.enderite.core.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;


public abstract class AbstractEnergizerRecipe implements IRecipe<IInventory> {
	   protected final IRecipeType<?> type;
	   protected final ResourceLocation id;
	   protected final String group;
	   protected final Ingredient ingredient;
	   protected final ItemStack result;
	   protected final float experience;
	   protected final int cookTime;
	   protected final IRecipeSerializer<?> serializer;

	   public AbstractEnergizerRecipe(IRecipeType<?> typeIn, IRecipeSerializer<?> serializerIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, float experienceIn, int cookTimeIn, ItemStack resultIn) {
	      this.type = typeIn;
	      this.id = idIn;
	      this.group = groupIn;
	      this.ingredient = ingredientIn;
	      this.result = resultIn;
	      this.experience = experienceIn;
	      this.cookTime = cookTimeIn;
	      this.serializer = serializerIn;
	   }

	   /**
	    * Used to check if a recipe matches current crafting inventory
	    */
	   public boolean matches(IInventory inv, World worldIn) {
	      return this.ingredient.test(inv.getStackInSlot(0));
	   }

	   /**
	    * Returns an Item that is the result of this recipe
	    */
	   public ItemStack getCraftingResult(IInventory inv) {
	      return this.result.copy();
	   }

	   /**
	    * Used to determine if this recipe can fit in a grid of the given width/height
	    */
	   public boolean canFit(int width, int height) {
	      return true;
	   }

	   public NonNullList<Ingredient> getIngredients() {
	      NonNullList<Ingredient> nonnulllist = NonNullList.create();
	      nonnulllist.add(this.ingredient);
	      return nonnulllist;
	   }

	   /**
	    * Gets the experience of this recipe
	    */
	   public float getExperience() {
	      return this.experience;
	   }

	   /**
	    * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	    * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	    */
	   public ItemStack getRecipeOutput() {
	      return this.result;
	   }

	   /**
	    * Recipes with equal group are combined into one button in the recipe book
	    */
	   public String getGroup() {
	      return this.group;
	   }

	   /**
	    * Gets the cook time in ticks
	    */
	   public int getCookTime() {
	      return this.cookTime;
	   }

	   public ResourceLocation getId() {
	      return this.id;
	   }
	   
	   public IRecipeType<?> getType() {
	      return this.type;
	   }
	   
	   public IRecipeSerializer<?> getSerializer() {
		   return this.serializer;
	   }
	   
	   
	   public static class ModRecipeSerializer<T extends AbstractEnergizerRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
		   private final int cookingTime;
		   private final IRecipeFactory<T> factory;
		   public ModRecipeSerializer(IRecipeFactory<T> factory, int cookingTime) {
		      this.cookingTime = cookingTime;
		      this.factory = factory;
		   }

		@SuppressWarnings("deprecation")
		public T read(ResourceLocation recipeId, JsonObject json) {
		      String group = JSONUtils.getString(json, "group", "");
		      JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
		      Ingredient ingredient = Ingredient.deserialize(jsonelement);
		      //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
		      if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
		      ItemStack result;
		      if (json.get("result").isJsonObject()) result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
		      else {
		      String s1 = JSONUtils.getString(json, "result");
		      ResourceLocation resourcelocation = new ResourceLocation(s1);
		      result = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
		         return new IllegalStateException("Item: " + s1 + " does not exist");
		      }));
		      }
		      float xp = JSONUtils.getFloat(json, "experience", 0.0F);
		      int cookTime = JSONUtils.getInt(json, "cookingtime", this.cookingTime);
		      return this.factory.create(recipeId, group, ingredient, result, xp, cookTime);
		   }

		   public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		      String group = buffer.readString(32767);
		      Ingredient ingredient = Ingredient.read(buffer);
		      ItemStack result = buffer.readItemStack();
		      float xp = buffer.readFloat();
		      int cookTime = buffer.readVarInt();
		      return this.factory.create(recipeId, group, ingredient, result, xp, cookTime);
		   }

		   public void write(PacketBuffer buffer, T recipe) {
		      buffer.writeString(recipe.group);
		      recipe.ingredient.write(buffer);
		      buffer.writeItemStack(recipe.result);
		      buffer.writeFloat(recipe.experience);
		      buffer.writeVarInt(recipe.cookTime);
		   }

		   public interface IRecipeFactory<T extends AbstractEnergizerRecipe> 
		   {
		      T create(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime);
		   }
	   }
}
