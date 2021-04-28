package com.yeetmanlord.enderite.core.init;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.common.containers.EnergizerContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes 
{
	 public static final DeferredRegister<ContainerType<?>>
	 	CONTIAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Enderite.MOD_ID);
	 
	 
	 public static final RegistryObject<ContainerType<EnergizerContainer>> CONTAINER_ENERGIZER = 
			 CONTIAINER_TYPES.register("energizer",
			 () -> new ContainerType<EnergizerContainer>(EnergizerContainer::new));
}
