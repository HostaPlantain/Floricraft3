package com.hosta.Floricraft3.recipe;

import com.hosta.Floricraft3.module.ModuleFloricraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class RecipeBrewingVial extends BrewingRecipe {

	protected static final ItemStack	VIAL_FLOWER	= new ItemStack(ModuleFloricraft.vialFlower);
	protected final Potion				POTION;

	public RecipeBrewingVial(Ingredient ingredient, Potion potion, boolean onlyWater)
	{
		super(Ingredient.fromItems(onlyWater ? new Item[] { ModuleFloricraft.vialWater } : new Item[] { ModuleFloricraft.vialWater, ModuleFloricraft.vialFlower }), ingredient, PotionUtils.addPotionToItemStack(VIAL_FLOWER.copy(), potion));
		this.POTION = potion;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient)
	{
		return isInput(input) && isIngredient(ingredient) ? getVialFlower(input) : ItemStack.EMPTY;
	}

	private ItemStack getVialFlower(ItemStack input)
	{
		ItemStack itemStack = getOutput().copy();
		itemStack.setTag(input.getTag());
		itemStack = PotionUtils.addPotionToItemStack(itemStack, POTION);
		return itemStack;
	}
}
