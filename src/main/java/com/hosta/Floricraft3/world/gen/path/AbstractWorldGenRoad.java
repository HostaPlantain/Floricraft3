package com.hosta.Floricraft3.world.gen.path;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public abstract class AbstractWorldGenRoad implements IWorldGenRoad {

	protected final int		PATH_WIDTH;
	protected final float	SLOPE;
	protected final float	SCALE_WIDTH;
	protected final float	SCALE_HIGHT;
	protected final int		RARE;

	protected int[][] stage;

	protected AbstractWorldGenRoad(int roadWidth, float slope, float scaleWidth, float scaleHight, int rare, int stage)
	{
		this.PATH_WIDTH = roadWidth;
		this.SLOPE = slope;
		this.SCALE_WIDTH = scaleWidth;
		this.SCALE_HIGHT = scaleHight;
		this.RARE = rare;
		this.stage = new int[stage][2];
	}

	protected float function(float x)
	{
		return (SLOPE * x) + (MathHelper.cos((x) / SCALE_WIDTH) * SCALE_HIGHT);
	}

	protected int[] getSegmentSided(int posX, int width, int side)
	{
		float[] way1 = getSegment(posX - side, width);
		float[] way2 = getSegment(posX + side, width);
		int min = getRared((int) (Math.min(way1[0], way2[0]) - side));
		int max = getRared((int) (Math.max(way1[1], way2[1]) + side));
		return new int[] { min, max };
	}

	protected float[] getSegment(int posX, int width)
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

	@Override
	public boolean isOnPath(int posZ)
	{
		return isOnPath(posZ, getMaxHeightRange());
	}

	protected int[] getMaxHeightRange()
	{
		return this.stage[stage.length - 1];
	}

	protected boolean isOnPath(int posZ, int[] range)
	{
		return isOnPath(posZ, range[0], range[1]);
	}

	protected boolean isOnPath(int posZ, float min, float max)
	{
		float rareZ = getRared(posZ);
		return (min < max && min <= rareZ && rareZ < max) || (max < min && (rareZ < max || min <= rareZ));
	}

	protected int getRared(int raw)
	{
		int rared = raw % RARE;
		return rared <= 0 ? rared + RARE : rared;
	}

	@Override
	public boolean isOnPath(Stage stage, IWorld world, BlockPos pos)
	{
		int i = stage.ID;
		return i < this.stage.length && isOnPath(pos.getZ(), this.stage[i]) && (i == 0 || !isOnPath(pos.getZ(), this.stage[--i]));
	}

	@Override
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
					genSide0(world, pos);
					break;
				case SIDE_1:
					genSide1(world, pos);
					break;
				case SIDE_2:
					genSide2(world, pos);
					break;
			}
		}
		return flag;
	}

	protected abstract void genPath(IWorld world, BlockPos pos);

	protected void genSide0(IWorld world, BlockPos pos)
	{
	};

	protected void genSide1(IWorld world, BlockPos pos)
	{
	};

	protected void genSide2(IWorld world, BlockPos pos)
	{
	};

}
