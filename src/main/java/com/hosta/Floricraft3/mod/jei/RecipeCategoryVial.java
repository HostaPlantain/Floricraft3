package com.hosta.Floricraft3.mod.jei;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemVial;
import com.hosta.Floricraft3.module.ModuleCore;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipeCategoryVial extends AbstractRecipeCategory<ItemVial> {

	private static final int	WIDTH	= 160;
	private static final int	HEIGHT	= 100;

	private static final int[][] SLOTS = new int[][] { { 71, 3 }, { 3, 79 }, { 22, 79 }, };

	private final IDrawableStatic	SLOT_DRAWABLE;
	private final String			INFO;
	private final String			INFO2;

	public RecipeCategoryVial(IGuiHelper guiHelper, ResourceLocation resourceLocation)
	{
		super(guiHelper, resourceLocation, ModuleCore.vialMoon, guiHelper.createBlankDrawable(WIDTH, HEIGHT));
		this.SLOT_DRAWABLE = guiHelper.getSlotDrawable();
		this.INFO = I18n.format(Reference.MOD_ID + "." + getUid().getPath() + ".info");
		this.INFO2 = I18n.format(Reference.MOD_ID + "." + getUid().getPath() + ".info2");
	}

	@Override
	public Class<? extends ItemVial> getRecipeClass()
	{
		return ItemVial.class;
	}

	@Override
	protected int[] getSlotOffset(int slotNumber)
	{
		return SLOTS[slotNumber];
	}

	@Override
	public void setIngredients(ItemVial recipe, IIngredients ingredients)
	{
		ingredients.setInputLists(VanillaTypes.ITEM, getInputs(recipe));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutput(recipe, 1));
	}

	protected List<List<ItemStack>> getInputs(ItemVial recipe)
	{
		List<ItemStack> input = getInput(recipe, 0);
		List<ItemStack> input2 = getInput(recipe, 1);
		return Arrays.asList(input, input2);
	}

	@Override
	protected List<ItemStack> getInput(ItemVial recipe, int slot)
	{
		List<ItemStack> input = NonNullList.create();
		if (slot == 0)
		{
			input.add(new ItemStack(recipe));
		}
		else if (slot == 1)
		{
			input.add(new ItemStack(recipe.getKeyItem()));
		}
		return input;
	}

	@Override
	protected List<ItemStack> getOutput(ItemVial recipe, int slot)
	{
		List<ItemStack> output = NonNullList.create();
		output.add(new ItemStack(recipe.getResultItem()));
		return output;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ItemVial recipe, IIngredients ingredients)
	{
		final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		setStacks(guiItemStacks, 0, getOutput(recipe, 0));
		setStacks(guiItemStacks, 1, getInput(recipe, 0));
		setStacks(guiItemStacks, 2, getInput(recipe, 1));
	}

	@Override
	public void draw(ItemVial recipe, double mouseX, double mouseY)
	{
		for (int[] slot : SLOTS)
		{
			SLOT_DRAWABLE.draw(slot[0], slot[1]);
		}
		drawInfo();
	}

	private static final int[][]	INFO_OFFSET		= new int[][] { { 3, 24 }, { 3, 69 } };
	private static final int		STRING_WIDTH	= 160 - (3 * 2);

	@SuppressWarnings("resource")
	private void drawInfo()
	{
		Minecraft.getInstance().fontRenderer.drawSplitString(INFO, INFO_OFFSET[0][0], INFO_OFFSET[0][1], STRING_WIDTH, Color.darkGray.getRGB());
		Minecraft.getInstance().fontRenderer.drawSplitString(INFO2, INFO_OFFSET[1][0], INFO_OFFSET[1][1], STRING_WIDTH, Color.darkGray.getRGB());
	}
}
