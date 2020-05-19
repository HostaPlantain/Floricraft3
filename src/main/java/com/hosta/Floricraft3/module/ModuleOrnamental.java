package com.hosta.Floricraft3.module;

import com.hosta.Flora.module.Module;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.world.biome.BiomeFlowerLand;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class ModuleOrnamental extends Module {

	@Override
	public boolean isEnable()
	{
		return Floricraft3.CONFIG_COMMON.IS_ENABLE_MODDED_MODULE.get(Reference.MODULE_ORNAMENTAL).get();
	}

	@Override
	public void registerBiomes()
	{
		Biome.Builder builder = new Biome.Builder();
		builder.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
		builder.precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS);
		builder.depth(0.8F).scale(0.0F).temperature(0.8F).downfall(0.4F).waterColor(4159204).waterFogColor(329011).parent((String) null);
		Biome flowerLand = register("flower_land", new BiomeFlowerLand(builder));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(flowerLand, 10));
	}
}
