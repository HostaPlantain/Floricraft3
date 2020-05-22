package com.hosta.Floricraft3.world.biome;

import com.hosta.Flora.world.biome.BiomeBase;
import com.hosta.Flora.world.biome.BiomeBaseNullNoise;
import com.hosta.Flora.world.gen.path.IWorldGenRoad;
import com.hosta.Floricraft3.world.gen.path.WorldGenRoadFlowerRoad;
import com.hosta.Floricraft3.world.gen.path.WorldGenRoadWaterPath;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;

public class BiomeFlowerLand extends BiomeBaseNullNoise {

	protected final IWorldGenRoad[] ROADS = { new WorldGenRoadFlowerRoad(4, 2.0f, 5.0f, 5.0f, 300), new WorldGenRoadFlowerRoad(2, -0.5f, 50.0f, 50.0f, 200), new WorldGenRoadWaterPath(5, -1.0f, 80.0f, 100.0f, 1000) };

	protected final Block[] FLOWER;

	public BiomeFlowerLand(Builder biomeBuilder, Block... flowers)
	{
		super(biomeBuilder);
		this.FLOWER = flowers;
		this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
		DefaultBiomeFeatures.addStoneVariants(this);
		DefaultBiomeFeatures.addOres(this);
	}

	@Override
	public void decorate(Decoration stage, ChunkGenerator<? extends GenerationSettings> chunkGenerator, IWorld worldIn, long seed, SharedSeedRandom random, BlockPos pos)
	{
		if (stage == Decoration.LOCAL_MODIFICATIONS)
		{
			decorate(worldIn, pos.getX(), pos.getZ());
		}
	}

	protected void decorate(IWorld worldIn, int posX, int posZ)
	{
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				BlockPos topPos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(posX + x, 0, posZ + z));
				if (canDecorate(worldIn, topPos))
				{
					decorate(worldIn, topPos);
				}
			}
		}
	}

	protected boolean canDecorate(IWorld worldIn, BlockPos topPos)
	{
		return worldIn.getBlockState(topPos.down()) == getSurfaceBuilderConfig().getTop();
	}

	protected void decorate(IWorld worldIn, BlockPos topPos)
	{
		if (isOnRoad(topPos))
		{
			genRoad((WorldGenRegion) worldIn, topPos.down());
		}
		else if (topPos.getZ() % 4 != 0 && topPos.getX() % 16 != 0)
		{
			BiomeBase.setBlockState(worldIn, topPos, getFlowerState(topPos.getZ()));
		}
	}

	protected BlockState getFlowerState(int pos)
	{
		int i = pos >> 3 & (FLOWER.length - 1);
		return FLOWER[i].getDefaultState();
	}

	protected boolean isOnRoad(BlockPos topPos)
	{
		boolean flag = false;
		for (IWorldGenRoad road : ROADS)
		{
			road.setSegment(topPos.getX());
			flag = flag || road.isOnPath(topPos.getZ());
		}
		return flag;
	}

	protected boolean genRoad(WorldGenRegion worldIn, BlockPos topPos)
	{
		for (IWorldGenRoad.Stage stage : IWorldGenRoad.Stage.values())
		{
			for (IWorldGenRoad road : ROADS)
			{
				if (road.gen(stage, worldIn, topPos))
				{
					return true;
				}
			}
		}
		return false;
	}
}
