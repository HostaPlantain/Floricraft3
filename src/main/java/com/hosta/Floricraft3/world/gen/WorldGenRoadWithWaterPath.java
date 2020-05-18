package com.hosta.Floricraft3.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public class WorldGenRoadWithWaterPath {

	private static final Config DEFAULT = new Config(Blocks.GRASS_PATH, Blocks.OAK_SLAB, Blocks.TALL_GRASS, Blocks.WATER, Blocks.OAK_FENCE);

	private final int	PATH_WIDTH;
	private final float	SLOPE;
	private final float	SCALE_WIDTH;
	private final float	SCALE_HIGHT;
	private final int	RARE;

	private final Config CONFIG;

	private int[]	path;
	private int[]	grass;
	private int[]	waterway;
	private int[]	fence;

	public WorldGenRoadWithWaterPath(float slope, float scaleWidth, float scaleHight, int rare)
	{
		this(3, slope, scaleWidth, scaleHight, rare);
	}

	public WorldGenRoadWithWaterPath(int roadWidth, float slope, float scaleWidth, float scaleHight, int rare)
	{
		this(roadWidth, DEFAULT, slope, scaleWidth, scaleHight, rare);
	}

	public WorldGenRoadWithWaterPath(int roadWidth, Config config, float slope, float scaleWidth, float scaleHight, int rare)
	{
		this.PATH_WIDTH = roadWidth;
		this.SLOPE = slope;
		this.SCALE_WIDTH = scaleWidth;
		this.SCALE_HIGHT = scaleHight;
		this.RARE = rare;
		this.CONFIG = config;
	}

	private float function(float x)
	{
		return (SLOPE * x) + (MathHelper.cos((x) / SCALE_WIDTH) * SCALE_HIGHT);
	}

	public void setSegment(int posX)
	{
		int i = -1;
		this.path = getSegmentSided(posX, PATH_WIDTH, ++i);
		this.grass = getSegmentSided(posX, PATH_WIDTH, ++i);
		this.waterway = getSegmentSided(posX, PATH_WIDTH, ++i);
		this.fence = getSegmentSided(posX, PATH_WIDTH, ++i);
	}

	private int[] getSegmentSided(int posX, int width, int side)
	{
		float[] way1 = getSegment(posX - side, width);
		float[] way2 = getSegment(posX + side, width);
		int min = getRared((int) (Math.min(way1[0], way2[0]) - side));
		int max = getRared((int) (Math.max(way1[1], way2[1]) + side));
		return new int[] { min, max };
	}

	private float[] getSegment(int posX, int width)
	{
		float z = function(posX);
		float min = z;
		float max = z;
		float widthHalf = (float) width / 2.0f;

		for (float pos = -widthHalf; pos < widthHalf; ++pos)
		{
			float range = pos + 0.5f;
			float hight = MathHelper.sqrt((widthHalf * widthHalf) - (range * range));
			z = function(posX + pos);

			min = Math.min(z - hight, min);
			max = Math.max(z + hight, max);
		}

		return new float[] { min, max };
	}

	private boolean isOnPath(Stage stage, IWorld world, BlockPos pos)
	{
		switch (stage)
		{
			case PATH:
				return isOnPath(pos.getZ(), this.path);
			case SIDE_0:
				return isOnPath(pos.getZ(), this.grass) && !isOnPath(pos.getZ(), this.path);
			case SIDE_1:
				return isOnPath(pos.getZ(), this.waterway) && !isOnPath(pos.getZ(), this.grass);
			case SIDE_2:
				return isOnPath(pos.getZ(), this.fence) && !isOnPath(pos.getZ(), this.waterway);
			default:
				return false;
		}
	}

	public boolean isOnPath(int posZ)
	{
		return isOnPath(posZ, fence);
	}

	private boolean isOnPath(int posZ, int[] range)
	{
		return isOnPath(posZ, range[0], range[1]);
	}

	private boolean isOnPath(int posZ, float min, float max)
	{
		float rareZ = getRared(posZ);
		return (min < max && min <= rareZ && rareZ < max) || (max < min && (rareZ < max || min <= rareZ));
	}

	private int getRared(int raw)
	{
		int rared = raw % RARE;
		return rared <= 0 ? rared + RARE : rared;
	}

	public boolean gen(Stage stage, IWorld world, BlockPos pos)
	{
		boolean flag = isOnPath(stage, world, pos);
		if (flag)
		{
			switch (stage)
			{
				case PATH:
					genPath(world, pos);
					break;
				case SIDE_0:
					genGrass(world, pos);
					break;
				case SIDE_1:
					genWaterway(world, pos);
					break;
				case SIDE_2:
					genFence(world, pos);
					break;
			}
		}
		return flag;
	}

	private void genPath(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, isAirNearBy(world, pos) ? CONFIG.getPathSlab() : CONFIG.getPath(), 0);
	}

	private void genGrass(IWorld world, BlockPos pos)
	{
		pos = pos.up();
		world.setBlockState(pos, CONFIG.getGrass(), 0);
		if (CONFIG.GRASS instanceof DoublePlantBlock)
		{
			world.setBlockState(pos.up(), CONFIG.getGrass().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 0);
		}
	}

	private void genWaterway(IWorld world, BlockPos pos)
	{
		if (isAirNearBy(world, pos))
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			pos = pos.down();
		}
		world.setBlockState(pos, CONFIG.getWater(), 3);
	}

	private void genFence(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos.up(), CONFIG.getFence(), 3);
	}

	private boolean isAirNearBy(IWorld world, BlockPos pos)
	{
		return isAirNotOnWater(world, pos.east()) || isAirNotOnWater(world, pos.west()) || isAirNotOnWater(world, pos.south()) || isAirNotOnWater(world, pos.north());
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

	public static enum Stage
	{
		PATH,
		SIDE_0,
		SIDE_1,
		SIDE_2
	}
}
