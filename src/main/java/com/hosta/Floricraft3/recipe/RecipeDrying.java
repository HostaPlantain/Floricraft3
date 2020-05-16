package com.hosta.Floricraft3.recipe;

import com.hosta.Flora.recipe.RecipeBaseSingleItem;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.module.ModuleFloricraft;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeDrying extends RecipeBaseSingleItem {

	public static final IRecipeType<RecipeDrying> TYPE = register(Reference.getResourceLocation("drying"));

	public RecipeDrying(ResourceLocation id, String group, Ingredient ingredient, ItemStack result)
	{
		super(TYPE, ModuleFloricraft.recipeDrying, id, group, ingredient, result);
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		ItemStack stackIn = inv.getStackInSlot(0);
		return ingredient.test(stackIn);
	}
}
