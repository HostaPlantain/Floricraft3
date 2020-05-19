package com.hosta.Floricraft3.world.gen.path;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class WorldGenRoadWaterPath extends AbstractWorldGenRoad {

	private static final WorldGenRoadWaterPath.Config DEFAULT = new Config(Blocks.WATER, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB);

	private final WorldGenRoadWaterPath.Config CONFIG;

	public WorldGenRoadWaterPath(int roadWidth, float slope, float scaleWidth, float scaleHight, int rare)
	{
		this(roadWidth, DEFAULT, slope, scaleWidth, scaleHight, rare);
	}

	public WorldGenRoadWaterPath(int roadWidth, WorldGenRoadWaterPath.Config config, float slope, float scaleWidth, float scaleHight, int rare)
	{
		super(roadWidth, slope, scaleWidth, scaleHight, rare, 2);
		this.CONFIG = config;
	}

	@Override
	public void setSegment(int posX)
	{
		for (int i = 0; i < stage.length; ++i)
		{
			this.stage[i] = getSegmentSided(posX, PATH_WIDTH, i);
		}
	}

	@Override
	protected void genPath(IWorld world, BlockPos pos)
	{
		BlockState state;
		for (int i = 0; i < 6; ++i)
		{
			if (i < 2)
			{
				state = Blocks.AIR.getDefaultState();
			}
			else if (i < 5)
			{
				state = CONFIG.getWater();
			}
			else
			{
				state = CONFIG.getBrick();
			}
			world.setBlockState(pos.down(i), state, 3);
		}
	}

	@Override
	protected void genSide0(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos.up(), CONFIG.getSlab(), 3);
		for (int i = 0; i < 6; ++i)
		{
			world.setBlockState(pos.down(i), CONFIG.getBrick(), 3);
		}
	}

	public static class Config {

		private final Block	WATER;
		private final Block	BRICK;
		private final Block	SLAB;

		protected Config(Block water, Block brick, Block slab)
		{
			this.WATER = water;
			this.BRICK = brick;
			this.SLAB = slab;
		}

		protected BlockState getWater()
		{
			return WATER.getDefaultState();
		}

		protected BlockState getBrick()
		{
			return BRICK.getDefaultState();
		}

		protected BlockState getSlab()
		{
			return SLAB.getDefaultState();
		}
	}
}
