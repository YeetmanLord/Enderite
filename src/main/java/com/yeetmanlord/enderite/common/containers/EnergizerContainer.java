package com.yeetmanlord.enderite.common.containers;

import com.yeetmanlord.enderite.common.containers.slots.EnergizerFuelSlot;
import com.yeetmanlord.enderite.common.containers.slots.EnergizerResultSlot;
import com.yeetmanlord.enderite.common.tiles.EnergizerTileEntity;
import com.yeetmanlord.enderite.core.init.ModContainerTypes;
import com.yeetmanlord.enderite.core.init.ModRecipeTypes;
import com.yeetmanlord.enderite.core.recipes.AbstractEnergizerRecipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergizerContainer extends Container
{
	
	   final IInventory energizerInventory;
	   private final IIntArray energizerData;
	   protected final World world;
	   private final IRecipeType<? extends AbstractEnergizerRecipe> recipeType;
	   
	   

	   public EnergizerContainer(final int windowId, final PlayerInventory playerInv)
		{
			super(ModContainerTypes.CONTAINER_ENERGIZER.get(), windowId);
			IInventory inv = new Inventory(3);
		    IIntArray invSlots = new IntArray(4);
		    this.recipeType = ModRecipeTypes.Types.ENERGIZING;
		    this.world = playerInv.player.world;
		      assertInventorySize(inv, 3);
		      assertIntArraySize(invSlots, 4);
		      this.energizerInventory = inv;
		      this.energizerData = invSlots;
			
	      this.addSlot(new Slot(inv, 0, 56, 17));
	      this.addSlot(new EnergizerFuelSlot(this, inv, 1, 56, 53));
	      this.addSlot(new EnergizerResultSlot(playerInv.player, inv, 2, 116, 35));

	  		
	  		createPlayerInventory(playerInv);
		}
	   
		private void createPlayerInventory(PlayerInventory playerInventory) {
			for(int y = 0; y < 3; ++y) {
				for(int x = 0; x < 9; ++x) {
					this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
				}
			}
			
			for(int x = 0; x < 9; ++x) {
		         this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
		      }
		}
		
	   
	   @Override
		public boolean canInteractWith(PlayerEntity playerIn) 
		{
		   return this.energizerInventory.isUsableByPlayer(playerIn);
		}
	   
	   public void fillStackedContents(RecipeItemHelper itemHelperIn) {
	      if (this.energizerInventory instanceof IRecipeHelperPopulator) {
	         ((IRecipeHelperPopulator)this.energizerInventory).fillStackedContents(itemHelperIn);
	      }
	      
	      
	   }
	   public void clear() {
	      this.energizerInventory.clear();
	   }
	   
	   public boolean matches(IRecipe<? super IInventory> recipeIn) {
	      return recipeIn.matches(this.energizerInventory, this.world);
	   }
	   
	   public int getOutputSlot() {
	      return 2;
	   }
	   
	   public int getWidth() {
	      return 1;
	   }
	   public int getHeight() {
	      return 1;
	   }
	   @OnlyIn(Dist.CLIENT)
	   public int getSize() {
	      return 3;
	   }

	   /**
	    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	    * inventory and the other inventory(s).
	    */
	   @Override
	   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.inventorySlots.get(index);
	      if (slot != null && slot.getHasStack()) {
	         ItemStack itemstack1 = slot.getStack();
	         itemstack = itemstack1.copy();
	         if (index == 2) {
	            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
	               return ItemStack.EMPTY;
	            }

	            slot.onSlotChange(itemstack1, itemstack);
	         } else if (index != 1 && index != 0) {
	            if (this.hasRecipe(itemstack1)) {
	               if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else if (this.isFuel(itemstack1)) {
	               if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else if (index >= 3 && index < 30) {
	               if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.putStack(ItemStack.EMPTY);
	         } else {
	            slot.onSlotChanged();
	         }

	         if (itemstack1.getCount() == itemstack.getCount()) {
	            return ItemStack.EMPTY;
	         }

	         slot.onTake(playerIn, itemstack1);
	      }

	      return itemstack;
	   }
	   
	   @SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean hasRecipe(ItemStack stack) {
	      return this.world.getRecipeManager().getRecipe((IRecipeType)this.recipeType, new Inventory(stack), this.world).isPresent();
	   }
	   
	   public boolean isFuel(ItemStack stack) {
	      return EnergizerTileEntity.isFuel(stack);
	   }

	   @OnlyIn(Dist.CLIENT)
	   public int getCookProgressionScaled() {
	      int i = this.energizerData.get(2);
	      int j = this.energizerData.get(3);
	      return j != 0 && i != 0 ? i * 24 / j : 0;
	   }

	   @OnlyIn(Dist.CLIENT)
	   public int getBurnLeftScaled() {
	      int i = this.energizerData.get(1);
	      if (i == 0) {
	         i = 200;
	      }

	      return this.energizerData.get(0) * 13 / i;
	   }

	   @OnlyIn(Dist.CLIENT)
	   public boolean isBurning() {
	      return this.energizerData.get(0) > 0;
	   }
}