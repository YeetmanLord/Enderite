package com.yeetmanlord.enderite.common.containers.slots;

import com.yeetmanlord.enderite.common.containers.EnergizerContainer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EnergizerFuelSlot extends Slot 
{
	private final EnergizerContainer container;

	   public EnergizerFuelSlot(EnergizerContainer container, IInventory energizerInv, int index, int xPos, int yPos) {
	      super(energizerInv, index, xPos, yPos);
	      this.container = container;
	   }

	   /**
	    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	    */
	   public boolean isItemValid(ItemStack stack) {
	      return this.container.isFuel(stack) || isBucket(stack);
	   }

	   public int getItemStackLimit(ItemStack stack) {
	      return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	   }

	   public static boolean isBucket(ItemStack stack) {
	      return stack.getItem() == Items.BUCKET;
	   }
}
