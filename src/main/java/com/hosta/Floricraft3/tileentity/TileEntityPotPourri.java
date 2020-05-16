package com.hosta.Floricraft3.tileentity;

import java.util.List;

import com.hosta.Flora.tileentity.TileEntityBaseInventoryWithRender;
import com.hosta.Floricraft3.module.ModuleFloricraft;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityPotPourri extends TileEntityBaseInventoryWithRender implements ITickableTileEntity {
	private static final int AFFECTIVE_TIME = 6000;

	public TileEntityPotPourri()
	{
		super(ModuleFloricraft.typePotPourri, 8, Ingredient.fromTag(ModuleFloricraft.PETALS_SALT));
		setInventoryStackLimit(1);
	}

	public void onActivated(PlayerEntity player, Hand handIn)
	{
		ItemStack stackIn = player.getHeldItem(handIn);
		if (isWhiteListed(stackIn))
		{
			for (int i = 0; i < getSizeInventory(); ++i)
			{
				if (getStackInSlot(i).isEmpty())
				{
					putHoldItemIn(player, handIn, i);
					break;
				}
			}
		}
		else
		{
			for (int i = getSizeInventory() - 1; i >= 0; --i)
			{
				if (!getStackInSlot(i).isEmpty())
				{
					putHoldItemIn(player, handIn, i);
					break;
				}
			}
		}
	}

	@Override
	public void tick()
	{
		if (!isEmpty())
		{
			avoidEntity();
			if (world.getDayTime() % AFFECTIVE_TIME == 0)
			{
				setInventorySlotContents(0, ItemStack.EMPTY);
			}
			for (int i = 0; i < getSizeInventory() - 1; ++i)
			{
				if (getStackInSlot(i).isEmpty())
				{
					setInventorySlotContents(i, getStackInSlot(i + 1));
					setInventorySlotContents(i + 1, ItemStack.EMPTY);
				}
			}
		}
	}

	protected void avoidEntity()
	{
		double range = 8.0d;
		double amplifier = 0.2d;
		AxisAlignedBB antiArea = new AxisAlignedBB(getPos()).expand(range, range, range).expand(-range, -range, -range);
		List<Entity> list = world.getEntitiesWithinAABB(CreatureEntity.class, antiArea);
		for (Entity entity : list)
		{
			CreatureEntity mob = (CreatureEntity) entity;
			moveEntity(mob, getPos(), amplifier);
		}
	}

	protected static void moveEntity(CreatureEntity mob, BlockPos pos, double amplifier)
	{
		double x = mob.getPosX() - pos.getX() - 0.5D;
		double y = mob.getPosY() - pos.getY() - 0.5D;
		double z = mob.getPosZ() - pos.getZ() - 0.5D;
		amplifier /= Math.sqrt((x * x) + (y * y) + (z * z));
		x *= amplifier;
		z *= amplifier;
		mob.setMotion(x, mob.getMotion().y, z);
	}
}
