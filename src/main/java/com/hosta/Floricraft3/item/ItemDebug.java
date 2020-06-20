package com.hosta.Floricraft3.item;

import com.hosta.Flora.Flora;
import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseTool;
import com.hosta.Flora.util.UtilHelper;
import com.hosta.Floricraft3.module.ModuleCore;
import com.hosta.Floricraft3.module.ModuleOrnamental;
import com.hosta.Floricraft3.particle.ParticleDataPetal;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemDebug extends ItemBaseTool {

	public ItemDebug(int maxDamageIn, IMod mod)
	{
		super(maxDamageIn, mod);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (!worldIn.isRemote())
		{
			spawnParticle((ServerWorld) worldIn, new Vec3d(playerIn.getPosX(), playerIn.getPosYEye(), playerIn.getPosZ()));
		}
		else
		{
			Minecraft mc = Minecraft.getInstance();
			RayTraceResult.Type type = mc.objectMouseOver.getType();
			Flora.LOGGER.debug(type);
			if (type == RayTraceResult.Type.ENTITY)
			{
					Entity entity = ((EntityRayTraceResult) mc.objectMouseOver).getEntity();
					Flora.LOGGER.debug(entity);
					Flora.LOGGER.debug(mc.player.canEntityBeSeen(entity));
					Flora.LOGGER.debug(mc.player.getDistanceSq(entity));
					mc.playerController.attackEntity(mc.player, entity);
			}/**
			double speed = playerIn.isSneaking() ? -2.0D : 4.0D;
			Vec3d motion = playerIn.getLookVec().mul(speed, speed / 2, speed);
			playerIn.setMotion(playerIn.getMotion().add(motion));**/
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		if (!attacker.getEntityWorld().isRemote())
		{
			spawnParticle((ServerWorld) attacker.getEntityWorld(), new Vec3d(target.getPosX(), target.getPosYEye(), target.getPosZ()));
		}
		return super.hitEntity(stack, target, attacker);
	}

	private void spawnParticle(ServerWorld world, Vec3d pos)
	{
		IParticleData particleData = new ParticleDataPetal(ModuleOrnamental.particlePetals, ModuleCore.petalPink);
		UtilHelper.spawnParticle(world, particleData, 20, pos);
	}
}
