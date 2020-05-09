package com.hosta.Floricraft3.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.hosta.Floricraft3.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ObjectHolder;

public class RecipeBrewingFloricraft implements IBrewingRecipe {

	@ObjectHolder(Reference.MOD_ID + ":vial_water")
	public static Item vialWater;

	@ObjectHolder(Reference.MOD_ID + ":vial_flower")
	public static Item vialFlower;

	@ObjectHolder(Reference.MOD_ID + ":floric")
	public static Effect floric;

	protected static final ResourceLocation VIALS = new ResourceLocation(Reference.MOD_ID, "vials"); 
	protected static final ResourceLocation PETALS_RAW = new ResourceLocation(Reference.MOD_ID, "petals/raw_all"); 

	@Override
	public boolean isInput(ItemStack input)
	{
		Set<ResourceLocation> tags = input.getItem().getTags();
		return tags.contains(VIALS);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient)
	{
		Set<ResourceLocation> tags = ingredient.getItem().getTags();
		return tags.contains(PETALS_RAW);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient)
	{
		if (input.getItem() == vialWater)
		{
			if (ingredient.getItem().getTags().contains(PETALS_RAW))
			{
				return getVialFlower(input, new EffectInstance(floric, 415, 0));
			}
		}
		return ItemStack.EMPTY;
	}

	private static ItemStack getVialFlower(ItemStack input, EffectInstance effectIn)
	{
		ItemStack itemStack = new ItemStack(vialFlower);
		itemStack.setTag(input.getTag());
		List<EffectInstance> list = new ArrayList<EffectInstance>();
		list.add(effectIn);
		itemStack = PotionUtils.appendEffects(itemStack, list);
		return itemStack;
	}
}
