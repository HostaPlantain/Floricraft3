package com.hosta.Floricraft3.tileentity;

import java.util.ArrayList;
import java.util.Collection;

import com.hosta.Flora.tileentity.TileEntityBaseInventoryWithRender;
import com.hosta.Floricraft3.module.ModuleCore;
import com.hosta.Floricraft3.recipe.RecipeDrying;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class TileEntityRope extends TileEntityBaseInventoryWithRender implements ITickableTileEntity {

	private static final String KEY_TICKABLE = "tikable";

	private static final Collection<RecipeDrying>	RECIPES			= new ArrayList<RecipeDrying>();
	private static RecipeManager					recipeManager	= null;

	private boolean	tikable			= false;
	private int		tick			= 0;
	private int		craftingTime	= 4000;

	public TileEntityRope()
	{
		super(ModuleCore.typeRope, 1);
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
		if (!this.world.isRemote() && this.tikable)
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

	public static Collection<RecipeDrying> getRecipes(RecipeManager manager)
	{
		if (RECIPES.isEmpty() || recipeManager != manager)
		{
			reload(manager);
		}
		return RECIPES;
	}

	public static void reload(RecipeManager manager)
	{
		RECIPES.clear();
		for (IRecipe<?> recipe : manager.getRecipes())
		{
			if (recipe instanceof RecipeDrying)
			{
				RECIPES.add((RecipeDrying) recipe);
			}
		}
		recipeManager = manager;
	}

	private RecipeDrying matches()
	{
		for (RecipeDrying recipe : getRecipes(this.world.getRecipeManager()))
		{
			if (recipe.matches(this, this.world))
			{
				return recipe;
			}
		}
		return null;
	}
}
