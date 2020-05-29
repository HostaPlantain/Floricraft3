package com.hosta.Floricraft3.mod.jei;

import java.util.Arrays;
import java.util.List;

import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.module.ModuleCore;
import com.hosta.Floricraft3.recipe.RecipeDrying;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipeCategoryDryingy extends AbstractRecipeCategory<RecipeDrying> {

	private static final ResourceLocation	LOCATION			= Reference.getResourceLocation("textures/gui/jei/recipe_drying.png");
	private static final ResourceLocation	RECIPE_GUI_VANILLA	= new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

	private static final int	WIDTH	= 82;
	private static final int	HEIGHT	= 26;

	private static final int[][]	SLOTS			= new int[][] { { 0, 3 }, { 60, 3 } };
	private static final int[]		ARROW_OFFSET	= new int[] { 24, 4 };

	private final IDrawableAnimated ARROW;

	public RecipeCategoryDryingy(IGuiHelper guiHelper, ResourceLocation resourceLocation)
	{
		super(guiHelper, resourceLocation, ModuleCore.rope, guiHelper.createDrawable(LOCATION, 0, 0, WIDTH, HEIGHT));
		this.ARROW = guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public Class<? extends RecipeDrying> getRecipeClass()
	{
		return RecipeDrying.class;
	}

	@Override
	protected int[] getSlotOffset(int slotNumber)
	{
		return SLOTS[slotNumber];
	}

	@Override
	protected List<ItemStack> getInput(RecipeDrying recipe, int slot)
	{
		List<ItemStack> input = NonNullList.create();
		for (Ingredient in : recipe.getIngredients())
		{
			input.addAll(Arrays.asList(in.getMatchingStacks()));
		}
		return input;
	}

	@Override
	protected List<ItemStack> getOutput(RecipeDrying recipe, int slot)
	{
		List<ItemStack> output = NonNullList.create();
		output.add(recipe.getRecipeOutput());
		return output;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RecipeDrying recipe, IIngredients ingredients)
	{
		final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		setStacks(guiItemStacks, 0, getInput(recipe, 0));
		setStacks(guiItemStacks, 1, getOutput(recipe, 0));
	}

	@Override
	public void draw(RecipeDrying recipe, double mouseX, double mouseY)
	{
		this.ARROW.draw(ARROW_OFFSET[0], ARROW_OFFSET[1]);
	}
}
