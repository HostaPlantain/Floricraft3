package com.hosta.Floricraft3.potion;

import java.util.EnumSet;
import java.util.List;

import com.hosta.Flora.potion.EffectBase;
import com.hosta.Flora.util.EffectHelper;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;

public class EffectAntis extends EffectBase {

	private final EntityType<?>[] ANTI_ENTITY;

	public EffectAntis(EffectType typeIn, int liquidColorIn, EntityType<?>[] anits)
	{
		super(typeIn, liquidColorIn);
		this.ANTI_ENTITY = anits;
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn.world.isRemote) return;

		if (entityLivingBaseIn instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
			double rangeHori = 8.0d;
			double rangeVert = 2.0d;
			AxisAlignedBB antiArea = player.getBoundingBox().expand(rangeHori, rangeVert, rangeHori).expand(-rangeHori, -rangeVert, -rangeHori);
			List<Entity> list = player.world.getEntitiesInAABBexcluding(player, antiArea, e -> matchType(e.getType()));

			for (Entity entity : list)
			{
				CreatureEntity mob = (CreatureEntity) entity;
				if (mob.getActivePotionEffect(this) == null || mob.getActivePotionEffect(this).getDuration() < 5)
				{
					mob.targetSelector.disableFlag(Flag.TARGET);
					mob.setAttackTarget(null);
					mob.targetSelector.addGoal(20, getDumyGoal(mob));
				}
				EffectHelper.mergeEffect(mob, new EffectInstance(this, 10, 0, false, false), 20);
			}
		}
		else if (entityLivingBaseIn instanceof MobEntity)
		{
			if (entityLivingBaseIn.getActivePotionEffect(this).getDuration() == 5)
			{
				CreatureEntity mob = (CreatureEntity) entityLivingBaseIn;
				mob.targetSelector.enableFlag(Flag.TARGET);
				mob.targetSelector.removeGoal(getDumyGoal(mob));
			}
		}
	}

	private Goal getDumyGoal(CreatureEntity mob)
	{
		Goal goal = new HurtByTargetGoal(mob);
		goal.setMutexFlags(EnumSet.of(Flag.JUMP));
		return goal;
	}

	protected boolean matchType(EntityType<?> entityType)
	{
		for (EntityType<?> type : ANTI_ENTITY)
		{
			if (entityType == type) {return true;}
		}
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}
}
