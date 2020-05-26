package com.hosta.Floricraft3.module;

import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemFailyDust;
import com.hosta.Floricraft3.recipe.RecipeAttributeModifier;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleFaily extends AbstractModule {

	@ObjectHolder(Reference.MOD_ID + ":crafting_attribute")
	public static IRecipeSerializer<?> recipeAttribute;

	public ModuleFaily(String name)
	{
		super(name);
	}

	@Override
	public void registerItems()
	{
		register("dust_faily", new ItemFailyDust(this.mod));
	}

	@Override
	public void registerRecipes()
	{
		register("crafting_attribute", new SpecialRecipeSerializer<RecipeAttributeModifier>(RecipeAttributeModifier::new));
	}
}
