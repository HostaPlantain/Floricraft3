package com.hosta.Floricraft3.tileentity;

import com.hosta.Flora.tileentity.TileEntityBaseInventoryWithRender;
import com.hosta.Flora.util.UtilHelper;
import com.hosta.Floricraft3.module.ModuleOrnamental;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;

public class TileEntityFlowerBed extends TileEntityBaseInventoryWithRender {

	private static final Ingredient INGREDIENT = Ingredient.fromTag(ModuleOrnamental.PLANTABLE);

	private NonNullList<BlockState> stateIn;

	public TileEntityFlowerBed()
	{
		this(ModuleOrnamental.typeFlowerBed, INGREDIENT);
	}

	public TileEntityFlowerBed(TileEntityType<?> tileEntityTypeIn, Ingredient ingredient)
	{
		super(tileEntityTypeIn, 3, ingredient);
		this.stateIn = NonNullList.withSize(getSizeInventory(), Blocks.AIR.getDefaultState());
		setInventoryStackLimit(1);
	}

	public void onActivated(PlayerEntity player, Hand handIn)
	{
		ItemStack stackIn = player.getHeldItem(handIn);
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			int solt = isWhiteListed(stackIn) ? i : getSizeInventory() - i - 1;
			boolean flag = isWhiteListed(stackIn) == getStackInSlot(solt).isEmpty();
			if (flag)
			{
				putHoldItemIn(player, handIn, solt);
				break;
			}
		}
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.stateIn = genList();
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		this.stateIn = genList();
	}

	@Override
	public void clear()
	{
		super.clear();
		stateIn.clear();
	}

	public BlockState getFlower(int i)
	{
		return stateIn.get(i);
	}

	private NonNullList<BlockState> genList()
	{
		NonNullList<BlockState> list = initList();
		for (int i = 0; i < list.size(); ++i)
		{
			list.set(i, UtilHelper.getBlockState(getStackInSlot(i)));
		}
		return list;
	}

	protected NonNullList<BlockState> initList()
	{
		return NonNullList.withSize(getSizeInventory(), Blocks.AIR.getDefaultState());
	}
}
