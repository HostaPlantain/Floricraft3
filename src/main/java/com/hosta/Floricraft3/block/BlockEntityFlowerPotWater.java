package com.hosta.Floricraft3.block;

import java.util.function.Supplier;

import com.hosta.Floricraft3.module.ModuleOrnamental;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPotWater;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEntityFlowerPotWater extends BlockEntityFlowerPot {

	public BlockEntityFlowerPotWater(Properties property, Supplier<TileEntity> supplier)
	{
		super(property, supplier);
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public TileEntityType<TileEntityFlowerPotWater> getTileEntityType()
	{
		return ModuleOrnamental.typeFlowerPotWater;
	}
}
