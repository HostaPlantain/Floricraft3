package com.hosta.Floricraft3.tileentity;

import com.hosta.Floricraft3.module.ModuleOrnamental;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlock;
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
		Block block = Block.getBlockFromItem(stack.getItem());
		return block != Blocks.AIR && block instanceof CoralBlock;
	}
}
