package com.hosta.Floricraft3.recipe;

import com.hosta.Flora.util.ColorHelper;
import com.hosta.Floricraft3.Reference;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class RecipeNaming extends SpecialRecipe {

	public RecipeNaming(ResourceLocation idIn)
	{
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		boolean hasName = false;
		boolean hasColor = false;
		boolean hasItem = false;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (!hasName && itemIn.getItem() instanceof NameTagItem && itemIn.hasDisplayName())
			{
				hasName = true;
			}
			else if (!hasColor && itemIn.getItem() instanceof DyeItem)
			{
				hasColor = true;
			}
			else if (!hasItem && !itemIn.isEmpty() && !(itemIn.getItem() instanceof NameTagItem) && !(itemIn.getItem() instanceof DyeItem))
			{
				hasItem = true;
			}
			else if (!itemIn.isEmpty())
			{
				return false;
			}
		}
		return hasName && hasItem;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ITextComponent name = null;
		ColorHelper color = ColorHelper.WHITE;
		ItemStack output = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (itemIn.getItem() instanceof NameTagItem && itemIn.hasDisplayName())
			{
				name = itemIn.getDisplayName();
			}
			else if (itemIn.getItem() instanceof DyeItem)
			{
				color = ColorHelper.getColor(DyeColor.getColor(itemIn));
			}
			else if (!itemIn.isEmpty())
			{
				output = itemIn.copy();
				output.setCount(1);
			}
		}

		if (!output.isEmpty() && name != null)
		{
			name.getStyle().setItalic(false);
			name.getStyle().setColor(color.getTextFormatting());
			output.setDisplayName(name);
		}
		return output;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return (width * height) >= 2;
	}

	@ObjectHolder(Reference.MOD_ID + ":crafting_special_naming")
	public static IRecipeSerializer<RecipeNaming> recipe;

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return recipe;
	}
}
