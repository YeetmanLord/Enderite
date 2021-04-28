package com.yeetmanlord.enderite.core.init;

import java.util.function.ToIntFunction;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.common.objects.blocks.EnderiteOreBlock;
import com.yeetmanlord.enderite.common.objects.blocks.EnergizerBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit 
{
	public static final DeferredRegister<Block>
		BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Enderite.MOD_ID);


	public static final RegistryObject<Block> UNCHARGED_ENDERITE_ORE = BLOCKS.register
			("enderite_ore", () -> new EnderiteOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
					.harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).harvestLevel(4).hardnessAndResistance(25.0f,1500.0f).setLightLevel((p_235838_1_) -> {return 3;})));
	
	public static final RegistryObject<Block> ENDERITE_ORE = BLOCKS.register
			("energized_enderite_ore", () -> new EnderiteOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
					.harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).harvestLevel(4).hardnessAndResistance(35.0f,1500.0f).setLightLevel((p_235838_1_) -> {return 3;})));
	
	public static final RegistryObject<Block> ENERGIZER_BLOCK = BLOCKS.register("energizer", () ->  new EnergizerBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.5F).setLightLevel(getLightValueLit(11)).sound(SoundType.METAL)));
	
	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
	      return (state) -> {
	         return state.get(BlockStateProperties.LIT) ? lightValue : 0;
	      };
	   }
}