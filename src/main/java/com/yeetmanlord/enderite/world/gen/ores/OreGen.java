package com.yeetmanlord.enderite.world.gen.ores;

import java.util.Random;

import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.core.init.BlockInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGen 
{
	private static final RuleTest END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);
	public static void genOres(final BiomeLoadingEvent event) 
	{
		Enderite.LOGGER.debug("ORE GENERATED");
		 if(event.getCategory().equals(Category.THEEND))
		 {
			genOre(event.getGeneration(), END_STONE, randOre(BlockInit.ENDERITE_ORE.get().getDefaultState(), 10f, BlockInit.UNCHARGED_ENDERITE_ORE.get()
					.getDefaultState(), 10f), 2, 10, 40);
		 }
	}
	
	private static void genOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, 
			int minHeight, int maxHeight)
	{
		settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
				 Feature.ORE.withConfiguration(new OreFeatureConfig(fillerType, state, veinSize))
				 .withPlacement(Placement.RANGE.configure
				 (new TopSolidRangeConfig(minHeight, 0, maxHeight))).square());
	}
	
	//Chance is based on percents
	private static BlockState randOre(BlockState state1, float chance1, BlockState state2, float chance2)
	{
		Random rand = new Random();
		float chance = rand.nextFloat();
		Enderite.LOGGER.debug(chance);
		float chanceAdjusted = Math.round(chance * 100f);
		Enderite.LOGGER.debug(chanceAdjusted);
		if(chanceAdjusted <= chance1)
		{
			Enderite.LOGGER.debug("Picked State 1");
			return state1;
		}
		//CHANCE1 AND CHANCE2 SHOULD BE EQUAL
		//CHANCE2 WILL HAVE A HIGHER CHANCE TO SPAWN
		else if(chanceAdjusted >= chance2)
		{
			Enderite.LOGGER.debug("Picked State 2");
			return state2;
		} else {Enderite.LOGGER.debug("NEITHER PICKED USED END STONE"); return Blocks.END_STONE.getDefaultState();}
	}
}
