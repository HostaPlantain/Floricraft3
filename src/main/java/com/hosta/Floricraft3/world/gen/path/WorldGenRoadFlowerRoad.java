package com.hosta.Floricraft3.world.gen.path;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public class WorldGenRoadFlowerRoad extends AbstractWorldGenRoad {

	private static final WorldGenRoadFlowerRoad.Config DEFAULT = new Config(Blocks.GRASS_PATH, Blocks.OAK_SLAB, Blocks.GRASS, Blocks.WATER, Blocks.OAK_FENCE);

	private final WorldGenRoadFlowerRoad.Config CONFIG;

	public WorldGenRoadFlowerRoad(int roadWidth, float slope, float scaleWidth, float scaleHight, int rare)
	{
		this(roadWidth, DEFAULT, slope, scaleWidth, scaleHight, rare);
	}

	public WorldGenRoadFlowerRoad(int roadWidth, WorldGenRoadFlowerRoad.Config config, float slope, float scaleWidth, float scaleHight, int rare)
	{
		super(roadWidth, slope, scaleWidth, scaleHight, rare, 4);
		this.CONFIG = config;
	}

	@Override
	protected float function(float x)
	{
		return (SLOPE * x) + (MathHelper.cos((x) / SCALE_WIDTH) * SCALE_HIGHT);
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
		world.setBlockState(pos, isAirNearBy(world, pos) ? CONFIG.getPathSlab() : CONFIG.getPath(), 3);
	}

	@Override
	protected void genSide0(IWorld world, BlockPos pos)
	{
		pos = pos.up();
		world.setBlockState(pos, CONFIG.getGrass(), 3);
		if (CONFIG.GRASS instanceof DoublePlantBlock)
		{
			world.setBlockState(pos.up(), CONFIG.getGrass().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
		}
	}

	@Override
	protected void genSide1(IWorld world, BlockPos pos)
	{
		if (isAirNearBy(world, pos))
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			pos = pos.down();
		}
		world.setBlockState(pos, CONFIG.getWater(), 3);
	}

	@Override
	protected void genSide2(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos.up(), CONFIG.getFence(), 3);
	}

	private boolean isAirNearBy(IWorld world, BlockPos pos)
	{
		boolean flag = false;
		for (Direction d : Direction.values())
		{
			flag = d.getHorizontalIndex() != -1 && isAirNotOnWater(world, pos.offset(d));
			if (flag)
			{
				break;
			}
		}
		return flag;
	}

	private boolean isAirNotOnWater(IWorld world, BlockPos pos)
	{
		return isAir(world.getBlockState(pos).getBlock()) && world.isAirBlock(pos.up()) && world.getBlockState(pos.down()).getBlock() != CONFIG.WATER;
	}

	private boolean isAir(Block block)
	{
		return block == Blocks.AIR || block == CONFIG.GRASS || block == CONFIG.FENCE;
	}

	public static class Config {

		private final Block	PATH;
		private final Block	PATH_SLAB;
		private final Block	GRASS;
		private final Block	WATER;
		private final Block	FENCE;

		protected Config(Block path, Block pathSlab, Block grass, Block water, Block fence)
		{
			this.PATH = path;
			this.PATH_SLAB = pathSlab;
			this.GRASS = grass;
			this.WATER = water;
			this.FENCE = fence;
		}

		protected BlockState getPath()
		{
			return PATH.getDefaultState();
		}

		protected BlockState getPathSlab()
		{
			return PATH_SLAB.getDefaultState();
		}

		protected BlockState getGrass()
		{
			return GRASS.getDefaultState();
		}

		protected BlockState getWater()
		{
			return WATER.getDefaultState();
		}

		protected BlockState getFence()
		{
			return FENCE.getDefaultState();
		}
	}
}
