package com.hosta.Floricraft3.item;

import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.IhasPotionList;
import com.hosta.Flora.item.ItemBasePotionTooltip;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

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

	public static void setPotionList(List<Potion> list)
	{
		potions = IhasPotionList.getPotionList(list);
	}

	@Override
	public Potion[] getPotionList()
	{
		return potions;
	}
}
