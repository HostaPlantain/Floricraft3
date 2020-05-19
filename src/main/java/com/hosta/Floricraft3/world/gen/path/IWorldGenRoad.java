package com.hosta.Floricraft3.world.gen.path;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IWorldGenRoad {

	public void setSegment(int posX);

	public boolean isOnPath(int posZ);

	public boolean isOnPath(Stage stage, IWorld world, BlockPos pos);

	public boolean gen(Stage stage, IWorld world, BlockPos pos);

	public static enum Stage
	{
		PATH(0),
		SIDE_0(1),
		SIDE_1(2),
		SIDE_2(3);

		public final int ID;

		private Stage(int id)
		{
			this.ID = id;
		}
	}
}
