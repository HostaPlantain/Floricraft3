package com.hosta.Floricraft3.item;

import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.IPotionList;
import com.hosta.Flora.item.ItemBasePotionTooltip;
import com.hosta.Flora.util.EffectHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemSachet extends ItemBasePotionTooltip implements IPotionList {

	private static Potion[]		potions;
	private static final int	TICK	= 10;

	public ItemSachet(int maxDamageIn, IMod mod)
	{
		this(maxDamageIn, mod.getDefaultProp());
	}

	public ItemSachet(int maxDamageIn, Item.Properties property)
	{
		super(property.maxStackSize(1).defaultMaxDamage(maxDamageIn));
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn.ticksExisted % TICK == 0 && entityIn instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entityIn;
			int addedEffects = ItemSachet.appendEffects(player, stack);
			if (addedEffects > 0)
			{
				addedEffects = 1 << (addedEffects - 1);
				stack.damageItem(addedEffects, player, (entity) -> {
					entity.sendBreakAnimation(Hand.MAIN_HAND);
				});
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public static int appendEffects(PlayerEntity player, ItemStack stack)
	{
		int i = 0;
		for (EffectInstance effect : PotionUtils.getEffectsFromStack(stack))
		{
			if (EffectHelper.mergeEffect(player, effect, TICK))
			{
				i += effect.getAmplifier() + 1;
			}
		}
		return i;
	}

	public static void setPotionList(List<Potion> list)
	{
		potions = IPotionList.getPotionList(list);
	}

	@Override
	public Potion[] getPotionList()
	{
		return potions;
	}
}
