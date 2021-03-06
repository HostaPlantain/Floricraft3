package com.hosta.Floricraft3.potion;

import java.util.EnumSet;
import java.util.List;

import com.hosta.Flora.potion.EffectBase;
import com.hosta.Flora.util.EffectHelper;
import com.hosta.Flora.util.ItemHelper;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class EffectAntis extends EffectBase {

	private final Tag<EntityType<?>>	ANTI_ENTITY;
	private final Ingredient			RECIPE;

	public EffectAntis(EffectType typeIn, int liquidColorIn, Tag<EntityType<?>> anits, ResourceLocation recipe)
	{
		super(typeIn, liquidColorIn);
		this.ANTI_ENTITY = anits;
		this.RECIPE = ItemHelper.getIngredient(recipe);
	}

	public Ingredient getRecipe()
	{
		return this.RECIPE;
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn.world.isRemote())
		{
			return;
		}
		else if (entityLivingBaseIn instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
			double rangeHori = 8.0d;
			double rangeVert = 4.0d;
			AxisAlignedBB antiArea = player.getBoundingBox().expand(rangeHori, rangeVert, rangeHori).expand(-rangeHori, -rangeVert, -rangeHori);
			List<Entity> list = player.world.getEntitiesInAABBexcluding(player, antiArea, e -> matchType(e.getType()));

			affect(list);
		}
		else if (entityLivingBaseIn instanceof CreatureEntity)
		{
			eableTarget((CreatureEntity) entityLivingBaseIn);
		}
	}

	public void affect(List<Entity> list)
	{
		for (Entity entity : list)
		{
			CreatureEntity mob = (CreatureEntity) entity;
			disableTarget(mob);
			EffectHelper.mergeEffect(mob, new EffectInstance(this, 10, 0, false, false), 20);
		}
	}

	private void disableTarget(CreatureEntity mob)
	{
		if (mob.getActivePotionEffect(this) == null || mob.getActivePotionEffect(this).getDuration() < 5)
		{
			mob.targetSelector.disableFlag(Flag.TARGET);
			mob.setAttackTarget(null);
			mob.targetSelector.addGoal(20, getDumyGoal(mob));
		}
	}

	private void eableTarget(CreatureEntity mob)
	{
		if (mob.getActivePotionEffect(this).getDuration() == 5)
		{
			mob.targetSelector.enableFlag(Flag.TARGET);
			mob.targetSelector.removeGoal(getDumyGoal(mob));
		}
	}

	private static Goal getDumyGoal(CreatureEntity mob)
	{
		Goal goal = new HurtByTargetGoal(mob);
		goal.setMutexFlags(EnumSet.of(Flag.JUMP));
		return goal;
	}

	protected boolean matchType(EntityType<?> entityType)
	{
		return ANTI_ENTITY.contains(entityType);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}
}
