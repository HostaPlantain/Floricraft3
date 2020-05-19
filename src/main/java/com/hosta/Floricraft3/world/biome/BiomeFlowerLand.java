package com.hosta.Floricraft3.world.biome;

import com.hosta.Flora.world.biome.BiomeBaseNullNoise;
import com.hosta.Floricraft3.world.gen.path.IWorldGenRoad;
import com.hosta.Floricraft3.world.gen.path.WorldGenRoadFlowerRoad;
import com.hosta.Floricraft3.world.gen.path.WorldGenRoadWaterPath;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

public class BiomeFlowerLand extends BiomeBaseNullNoise {

	protected final IWorldGenRoad[] ROADS = { new WorldGenRoadFlowerRoad(4, 2.0f, 5.0f, 5.0f, 300), new WorldGenRoadFlowerRoad(2, -0.5f, 50.0f, 50.0f, 200), new WorldGenRoadWaterPath(5, -1.0f, 80.0f, 100.0f, 1000) };

	protected Block flower = Blocks.ROSE_BUSH;

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
				else if (z % 4 != 0 && x % 16 != 0)
				{
					worldIn.setBlockState(topPos, getFlower(), 0);
					if (flower instanceof DoublePlantBlock)
					{
						worldIn.setBlockState(topPos.up(), getFlower().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 0);
					}
				}
			}
		}
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
		boolean flag = false;
		for (IWorldGenRoad.Stage stage : IWorldGenRoad.Stage.values())
		{
			if (!flag)
			{
				for (IWorldGenRoad road : ROADS)
				{
					flag = flag || road.gen(stage, worldIn, topPos);
				}
			}
		}
		return flag;
	}

	protected BlockState getFlower()
	{
		return this.flower.getDefaultState();
	}
}
