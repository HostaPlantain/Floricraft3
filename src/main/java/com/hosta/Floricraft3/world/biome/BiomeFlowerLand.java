package com.hosta.Floricraft3.world.biome;

import com.hosta.Flora.world.biome.BiomeBaseNullNoise;
import com.hosta.Floricraft3.world.gen.WorldGenRoadWithWaterPath;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

public class BiomeFlowerLand extends BiomeBaseNullNoise {

	protected final WorldGenRoadWithWaterPath[] ROADS = { new WorldGenRoadWithWaterPath(2, -1.0f, 80.0f, 100.0f, 1000), new WorldGenRoadWithWaterPath(2.0f, 5.0f, 5.0f, 300), new WorldGenRoadWithWaterPath(-0.5f, 50.0f, 50.0f, 200) };

	public BiomeFlowerLand(Builder biomeBuilder)
	{
		super(biomeBuilder);
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
				BlockPos topPos = new BlockPos(posX + x, 0, posZ + z);
				topPos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, topPos);
				if (isOnRoad(topPos))
				{
					genRoad((WorldGenRegion) worldIn, topPos.down());
				}
			}
		}
	}

	protected boolean isOnRoad(BlockPos topPos)
	{
		boolean flag = false;
		for (WorldGenRoadWithWaterPath simpleRoad : ROADS)
		{
			simpleRoad.setSegment(topPos.getX());
			flag = flag || simpleRoad.isOnPath(topPos.getZ());
		}
		return flag;
	}

	protected boolean genRoad(WorldGenRegion worldIn, BlockPos topPos)
	{
		boolean flag = false;
		for (WorldGenRoadWithWaterPath.Stage stage : WorldGenRoadWithWaterPath.Stage.values())
		{
			if (!flag)
			{
				for (WorldGenRoadWithWaterPath simpleRoad : ROADS)
				{
					flag = flag || simpleRoad.gen(stage, worldIn, topPos);
				}
			}
		}
		return flag;
	}
}
