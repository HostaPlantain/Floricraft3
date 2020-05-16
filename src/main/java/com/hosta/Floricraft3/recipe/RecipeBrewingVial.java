package com.hosta.Floricraft3.recipe;

import com.hosta.Flora.recipe.RecipeBaseBrewing;
import com.hosta.Floricraft3.module.ModuleFloricraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;

public class RecipeBrewingVial extends RecipeBaseBrewing {

	protected static final ItemStack	VIAL_FLOWER	= new ItemStack(ModuleFloricraft.vialFlower);
	protected final Potion				POTION;

	public RecipeBrewingVial(Ingredient ingredient, Potion potion, boolean onlyWater)
	{
		super(Ingredient.fromItems(onlyWater ? new Item[] { ModuleFloricraft.vialWater } : new Item[] { ModuleFloricraft.vialWater, ModuleFloricraft.vialFlower }), ingredient, PotionUtils.addPotionToItemStack(VIAL_FLOWER.copy(), potion));
		this.POTION = potion;
	}

	@Override
	protected ItemStack getResultOutput(ItemStack input)
	{
		ItemStack itemStack = super.getResultOutput(input);
		return PotionUtils.addPotionToItemStack(itemStack, POTION);
	}
}
