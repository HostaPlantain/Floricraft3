package com.hosta.Floricraft3.item;

import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseTool;
import com.hosta.Flora.util.Helper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemSachet extends ItemBaseTool {

	public ItemSachet(int maxDamageIn, IMod mod)
	{
		super(maxDamageIn, mod);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn instanceof PlayerEntity)
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
			if (Helper.mergeEffect(player, effect, 10))
			{
				i += effect.getAmplifier() + 1;
			}
		}
		return i;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
