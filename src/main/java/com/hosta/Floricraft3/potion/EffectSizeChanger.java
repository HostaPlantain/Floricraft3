package com.hosta.Floricraft3.potion;

import java.lang.reflect.Field;
import java.util.Map;

import com.hosta.Flora.potion.EffectBase;
import com.hosta.Flora.util.UtilHelper;
import com.hosta.Floricraft3.Floricraft3;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

public class EffectSizeChanger extends EffectBase {

	private final boolean BIG;

	public EffectSizeChanger(EffectType typeIn, int liquidColorIn, boolean isPossitive)
	{
		super(typeIn, liquidColorIn);
		this.BIG = isPossitive;
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn instanceof PlayerEntity)
		{
			float size = BIG ? amplifier + 2.0F : 1 / (amplifier + 2.0F);
			resize((PlayerEntity) entityLivingBaseIn, amplifier, size);
		}
	}

	@SuppressWarnings("unchecked")
	private void resize(PlayerEntity player, int amplifier, float size)
	{
		try
		{
			Field field = UtilHelper.getAccesable(PlayerEntity.class, "SIZE_BY_POSE");
			Map<Pose, EntitySize> map = (Map<Pose, EntitySize>) field.get(player);
			for (EntitySize entitySize : map.values())
			{
				if (!entitySize.fixed)
				{
					for (String key : new String[] { "width", "height" })
					{
						Field resize = UtilHelper.getAccesable(entitySize.getClass(), key);
						resize.setFloat(entitySize, resize.getFloat(entitySize) * size);
					}
				}
			}
		}
		catch (Exception e)
		{
			Floricraft3.LOGGER.warn("failed to resize " + player + " using " + this);
			e.printStackTrace();
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return false;
	}

	@Override
	public boolean isInstant()
	{
		return true;
	}
}
