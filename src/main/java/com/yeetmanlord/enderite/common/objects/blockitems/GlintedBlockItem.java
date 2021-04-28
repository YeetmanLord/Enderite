package com.yeetmanlord.enderite.common.objects.blockitems;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GlintedBlockItem extends BlockItem
{
	public GlintedBlockItem(Block block, Item.Properties properties) 
	{
		super(block,properties);
		
	}
	
	
	@Override
	public boolean hasEffect(ItemStack stack) 
	{
		return true;
	}
}
