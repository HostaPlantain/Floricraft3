package com.hosta.Floricraft3.item;

import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBasePotionTooltip;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;

public class ItemVialFlower extends ItemBasePotionTooltip {

	private static Potion[] potions;

	public ItemVialFlower(IMod mod)
	{
		this(mod.getDefaultProp().maxStackSize(1));
	}

	public ItemVialFlower(Item.Properties property)
	{
		super(property);
	}

	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (this.isInGroup(group))
		{
			System.out.println(potions);
			for (Potion potion : potions)
			{
				System.out.println(potion);
				items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potion));
			}
		}
	}

	public static void setPotionList(List<Potion> list)
	{
		System.out.println(potions);
		potions = list.toArray(new Potion[list.size()]);
	}
}
