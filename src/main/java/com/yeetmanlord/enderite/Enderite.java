package com.yeetmanlord.enderite;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yeetmanlord.enderite.core.init.BlockInit;
import com.yeetmanlord.enderite.core.init.ItemInit;
import com.yeetmanlord.enderite.core.init.ModContainerTypes;
import com.yeetmanlord.enderite.core.init.ModRecipeTypes;
import com.yeetmanlord.enderite.core.init.ModTileEntityTypes;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("enderite")
@Mod.EventBusSubscriber(modid = Enderite.MOD_ID, bus = Bus.MOD)
public class Enderite 
{
	public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "enderite";
    public static Enderite instance;
    
    public Enderite() {
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    		modEventBus.addListener(this::setup);
    		
    		modEventBus.addListener(this::doClientStuff);
    			
    			ModRecipeTypes.RECIPES.register(modEventBus);
    			ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        		ModContainerTypes.CONTIAINER_TYPES.register(modEventBus);
        		BlockInit.BLOCKS.register(modEventBus);
        		ItemInit.ITEMS.register(modEventBus);
    				
    	instance=this;
    	
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("PREINIT IS FUNCTIONING");
        
//        event.enqueueWork(() ->
//        {
//        	
//        });
    }
    
    

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
       
    }
    
    public static class ItemsItemGroup extends ItemGroup 
    {
    	public static final ItemGroup instance = new ItemsItemGroup(ItemGroup.GROUPS.length, "moditemstab");

		private ItemsItemGroup(int index, String label) 
		{
			super(index, label);
		}

		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(ItemInit.ENDERITE_SHARD.get());
		}
		
		
	}
}
