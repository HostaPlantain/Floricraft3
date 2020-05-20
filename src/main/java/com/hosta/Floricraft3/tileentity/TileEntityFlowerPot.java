package com.hosta.Floricraft3.tileentity;

import com.hosta.Flora.tileentity.TileEntityBaseInventoryWithRender;
import com.hosta.Flora.util.UtilHelper;
import com.hosta.Floricraft3.module.ModuleOrnamental;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;

public class TileEntityFlowerPot extends TileEntityBaseInventoryWithRender {

	private static final Ingredient INGREDIENT = Ingredient.fromTag(ModuleOrnamental.PLANTABLE);

	private BlockState stateIn = Blocks.AIR.getDefaultState();

	public TileEntityFlowerPot()
	{
		super(ModuleOrnamental.typeFlowerPot, 1, INGREDIENT);
		setInventoryStackLimit(1);
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.stateIn = UtilHelper.getBlockState(getStackInSlot(0));
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		this.stateIn = UtilHelper.getBlockState(getStackInSlot(0));
	}

	public BlockState getFlower()
	{
		return stateIn;
	}
}
