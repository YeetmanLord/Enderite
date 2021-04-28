package com.yeetmanlord.enderite.common.objects.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GlintedItem extends Item 
{

	public GlintedItem(Properties p_i48487_1_) 
	{
		super(p_i48487_1_);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) 
	{
		return true;
	}
}
