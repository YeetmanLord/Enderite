package com.yeetmanlord.enderite.core.util;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.client.screens.EnergizerGUI;
import com.yeetmanlord.enderite.core.init.ModContainerTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Enderite.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber
{
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{
		ScreenManager.registerFactory(ModContainerTypes.CONTAINER_ENERGIZER.get(), EnergizerGUI::new);
	}

}

