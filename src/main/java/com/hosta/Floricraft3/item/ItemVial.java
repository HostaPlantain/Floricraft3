package com.hosta.Floricraft3.item;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseBottle;
import com.hosta.Flora.util.EntityHelper;
import com.hosta.Floricraft3.module.ModuleCore;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemVial extends ItemBaseBottle {

	public ItemVial(IMod mod)
	{
		super(mod);
	}

	public ItemVial(Properties builder)
	{
		super(builder);
	}

	@Override
	protected ItemStack getFilledItem(World worldIn, BlockPos pos)
	{
		if (worldIn.getBlockState(pos).getMaterial() == Material.WATER)
		{
			for (ItemEntity entityItem : worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos)))
			{
				if (entityItem.getItem().getItem() == Items.GHAST_TEAR)
				{
					EntityHelper.splitItem(entityItem, 1);
					return new ItemStack(ModuleCore.vialMoon);
				}
			}
			return new ItemStack(ModuleCore.vialWater);
		}
		return ItemStack.EMPTY;
	}
}
