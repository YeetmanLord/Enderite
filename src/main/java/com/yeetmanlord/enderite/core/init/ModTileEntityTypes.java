package com.yeetmanlord.enderite.core.init;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.common.tiles.EnergizerTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes 
{
	public static final DeferredRegister<TileEntityType<?>>
	 	TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Enderite.MOD_ID);
	
	
	public static final RegistryObject<TileEntityType
		<EnergizerTileEntity>> TILE_ENERGIZED = TILE_ENTITY_TYPES.register
		("tile_energizer", () -> TileEntityType.Builder.create(EnergizerTileEntity::new, BlockInit.ENERGIZER_BLOCK.get()).build(null));
}
