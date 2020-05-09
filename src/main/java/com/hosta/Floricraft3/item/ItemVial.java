package com.hosta.Floricraft3.item;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBaseBottle;
import com.hosta.Flora.util.EntityHelper;
import com.hosta.Floricraft3.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class ItemVial extends ItemBaseBottle {

	@ObjectHolder(Reference.MOD_ID + ":vial_water")
	public static Item vial_water;

	@ObjectHolder(Reference.MOD_ID + ":vial_moon")
	public static Item vial_moon;

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
					return new ItemStack(vial_moon);
				}
			}
			return new ItemStack(vial_water);
		}
		return ItemStack.EMPTY;
	}
}
