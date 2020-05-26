package com.hosta.Floricraft3.tileentity;

import com.hosta.Flora.tileentity.TileEntityBaseInventoryWithRender;
import com.hosta.Flora.util.ItemHelper;
import com.hosta.Floricraft3.module.ModuleOrnamental;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.IPlantable;

public class TileEntityFlowerPot extends TileEntityBaseInventoryWithRender {

	private static final Ingredient INGREDIENT = Ingredient.fromTag(ModuleOrnamental.PLANTABLE);

	private BlockState stateIn = Blocks.AIR.getDefaultState();

	public TileEntityFlowerPot()
	{
		this(ModuleOrnamental.typeFlowerPot, INGREDIENT);
	}

	public TileEntityFlowerPot(TileEntityType<?> tileEntityTypeIn, Ingredient ingredient)
	{
		super(tileEntityTypeIn, 1, ingredient);
		setInventoryStackLimit(1);
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.stateIn = ItemHelper.getBlockState(getStackInSlot(0));
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		this.stateIn = ItemHelper.getBlockState(getStackInSlot(0));
	}

	@Override
	public boolean isWhiteListed(ItemStack stack)
	{
		return super.isWhiteListed(stack) || additionalWhiteList(stack);
	}

	protected boolean additionalWhiteList(ItemStack stack)
	{
		Block block = Block.getBlockFromItem(stack.getItem());
		return block != Blocks.AIR && block instanceof IPlantable;
	}

	public BlockState getFlower()
	{
		return stateIn;
	}
}
