package com.hosta.Floricraft3.item;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBase;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ItemFailyDust extends ItemBase {

	public ItemFailyDust(IMod mod)
	{
		super(mod);
	}

	public static IAttribute getIAttribute(ItemStack output, EquipmentSlotType slot)
	{
		if (slot == null)
		{
			return SharedMonsterAttributes.LUCK;
		}
		switch (slot)
		{
			case HEAD:
				return SharedMonsterAttributes.ATTACK_SPEED;
			case CHEST:
				return SharedMonsterAttributes.ARMOR_TOUGHNESS;
			case LEGS:
				return SharedMonsterAttributes.MOVEMENT_SPEED;
			case FEET:
				return SharedMonsterAttributes.KNOCKBACK_RESISTANCE;
			default:
			case MAINHAND:
				return SharedMonsterAttributes.ATTACK_DAMAGE;
			case OFFHAND:
				return SharedMonsterAttributes.ATTACK_SPEED;
		}
	}
}
