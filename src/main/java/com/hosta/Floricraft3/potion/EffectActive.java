package com.hosta.Floricraft3.potion;

import com.hosta.Flora.potion.EffectBase;
import com.hosta.Flora.util.EffectHelper;
import com.hosta.Floricraft3.module.ModuleFloricraft;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

public class EffectActive extends EffectBase {

	public EffectActive(EffectType typeIn, int liquidColorIn)
	{
		super(typeIn, liquidColorIn);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (this == ModuleFloricraft.effectFloric)
		{
			EffectHelper.healBadEffect(entityLivingBaseIn, 20, true);
			if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
			{
				entityLivingBaseIn.heal(1.0f);
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		boolean ready = false;
		if (this == ModuleFloricraft.effectFloric)
		{
			int i = 256 >> amplifier;
			if (i <= 0)
			{
				i = 1;
			}
			if (duration % i == 0)
			{
				ready = true;
			}
		}
		return ready;
	}
}
