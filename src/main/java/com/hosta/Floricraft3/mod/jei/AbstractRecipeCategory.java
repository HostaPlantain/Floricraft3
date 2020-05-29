package com.hosta.Floricraft3.mod.jei;

import java.util.List;

import com.hosta.Floricraft3.Reference;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractRecipeCategory<T> implements IRecipeCategory<T> {

	private final ResourceLocation	RESOURCE_LOCATION;
	private final IDrawable			ICON;
	private final IDrawableStatic	BACKGROUND;
	private final String			LOCALIZED_NAME;

	public AbstractRecipeCategory(IGuiHelper guiHelper, ResourceLocation resourceLocation, IItemProvider item, IDrawableStatic background)
	{
		this.RESOURCE_LOCATION = resourceLocation;
		this.ICON = guiHelper.createDrawableIngredient(new ItemStack(item));
		this.BACKGROUND = background;
		this.LOCALIZED_NAME = I18n.format(Reference.MOD_ID + "." + getUid().getPath() + ".title");
	}

	protected abstract int[] getSlotOffset(int slotNumber);

	@Override
	public ResourceLocation getUid()
	{
		return RESOURCE_LOCATION;
	}

	@Override
	public IDrawable getIcon()
	{
		return ICON;
	}

	@Override
	public IDrawable getBackground()
	{
		return BACKGROUND;
	}

	@Override
	public String getTitle()
	{
		return LOCALIZED_NAME;
	}

	@Override
	public void setIngredients(T recipe, IIngredients ingredients)
	{
		ingredients.setInputs(VanillaTypes.ITEM, getInput(recipe, 0));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutput(recipe, 0));
	}

	protected abstract List<ItemStack> getInput(T recipe, int slot);

	protected abstract List<ItemStack> getOutput(T recipe, int slot);

	protected void setStacks(final IGuiItemStackGroup guiItemStacks, int slotNumber, List<ItemStack> list)
	{
		int[] slot = getSlotOffset(slotNumber);
		guiItemStacks.init(slotNumber, true, slot[0], slot[1]);
		guiItemStacks.set(slotNumber, list);
	}
}
