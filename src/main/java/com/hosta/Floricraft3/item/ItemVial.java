package com.hosta.Floricraft3.item;

import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseBottle;
import com.hosta.Flora.util.EntityHelper;
import com.hosta.Floricraft3.module.ModuleCore;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
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
			List<ItemEntity> list = worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos), (e -> e.getItem().getItem() == getKeyItem()));
			for (ItemEntity entityItem : list)
			{
				EntityHelper.splitItem(entityItem, 1);
				return new ItemStack(getResultItem());
			}
			return new ItemStack(ModuleCore.vialWater);
		}
		return ItemStack.EMPTY;
	}

	public Item getKeyItem()
	{
		return Items.GHAST_TEAR;
	}

	public Item getResultItem()
	{
		return ModuleCore.vialMoon;
	}
}
