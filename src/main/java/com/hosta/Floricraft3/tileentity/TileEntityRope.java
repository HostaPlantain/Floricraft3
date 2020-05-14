package com.hosta.Floricraft3.tileentity;

import java.util.List;

import com.hosta.Flora.tileentity.TileEntityInventoryWithRender;
import com.hosta.Floricraft3.module.ModuleFloricraft;
import com.hosta.Floricraft3.recipe.RecipeDrying;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class TileEntityRope extends TileEntityInventoryWithRender implements ITickableTileEntity {

	private static final String	KEY_TICKABLE	= "tikable";
	private List<RecipeDrying>	recipes			= null;

	private boolean	tikable			= false;
	private int		tick			= 0;
	private int		craftingTime	= 4000;

	public TileEntityRope()
	{
		super(ModuleFloricraft.typeRope, 1);
		setInventoryStackLimit(1);
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		if (compound.contains(KEY_TICKABLE))
		{
			this.tikable = true;
			this.tick = compound.getInt(KEY_TICKABLE);
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound = super.write(compound);
		if (this.tikable)
		{
			compound.putInt(KEY_TICKABLE, this.tick);
		}
		return compound;
	}

	@Override
	public void tick()
	{
		if (!this.world.isRemote && this.tikable)
		{
			if (++this.tick > this.craftingTime)
			{
				setCraftingResult();
			}
		}
	}

	private void setCraftingResult()
	{
		int slot = 0;
		ItemStack stackIn = this.items.get(slot);
		ItemStack stackResult = matches().getCraftingResult(this);
		stackResult.setTag(stackIn.getTag());
		setInventorySlotContents(slot, stackResult);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		super.setInventorySlotContents(index, stack);
		this.tikable = matches() != null;
		if (this.tikable)
		{
			this.tick = 0;
		}
	}

	private RecipeDrying matches()
	{
		if (recipes == null)
		{
			recipes = this.world.getRecipeManager().getRecipes(RecipeDrying.TYPE, this, this.world);
		}
		for (RecipeDrying recipe : recipes)
		{
			if (recipe.matches(this, this.world))
			{
				return recipe;
			}
		}
		return null;
	}
}
