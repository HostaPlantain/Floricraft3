package com.hosta.Floricraft3.tileentity;

import com.hosta.Floricraft3.module.ModuleOrnamental;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class TileEntityFlowerPotWater extends TileEntityFlowerPot {

	private static final Ingredient INGREDIENT = Ingredient.fromTag(ModuleOrnamental.PLANTABLE_WATER);

	public TileEntityFlowerPotWater()
	{
		super(ModuleOrnamental.typeFlowerPotWater, INGREDIENT);
	}

	@Override
	protected boolean additionalWhiteList(ItemStack stack)
	{
		return false;
	}
}
