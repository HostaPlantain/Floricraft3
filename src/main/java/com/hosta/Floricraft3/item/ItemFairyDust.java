package com.hosta.Floricraft3.item;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseAttributeAbstract;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ItemFairyDust extends ItemBaseAttributeAbstract {

	public ItemFairyDust(IMod mod)
	{
		super(mod, true, 0.01);
	}

	@Override
	public IAttribute getIAttribute(ItemStack output, EquipmentSlotType slot)
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
				return PlayerEntity.REACH_DISTANCE;
			case LEGS:
				return SharedMonsterAttributes.KNOCKBACK_RESISTANCE;
			case FEET:
				return SharedMonsterAttributes.MOVEMENT_SPEED;
			case MAINHAND:
				return SharedMonsterAttributes.ATTACK_DAMAGE;
			case OFFHAND:
				return SharedMonsterAttributes.ATTACK_SPEED;
			default:
				return SharedMonsterAttributes.LUCK;
		}
	}
}
