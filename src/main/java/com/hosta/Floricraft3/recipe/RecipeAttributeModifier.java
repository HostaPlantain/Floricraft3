package com.hosta.Floricraft3.recipe;

import com.google.common.collect.Multimap;
import com.hosta.Flora.recipe.RecipeBase;
import com.hosta.Floricraft3.item.ItemFailyDust;
import com.hosta.Floricraft3.module.ModuleFaily;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

public class RecipeAttributeModifier extends RecipeBase {

	public RecipeAttributeModifier(ResourceLocation idIn)
	{
		super(idIn, 2, 2);
	}

	@Override
	protected boolean match(int index, ItemStack itemIn)
	{
		switch (index)
		{
			case 0:
				return itemIn.getItem() instanceof ItemFailyDust;
			case 1:
				return !itemIn.isEmpty() && !(itemIn.getItem() instanceof ItemFailyDust);
			default:
				return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack output = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (!itemIn.isEmpty() && !(itemIn.getItem() instanceof ItemFailyDust))
			{
				output = itemIn.copy();
				output.setCount(1);
				addAttributeModifier(output);
			}
		}
		return output;
	}

	private static void addAttributeModifier(ItemStack output)
	{
		EquipmentSlotType slot = output.getItem() instanceof ArmorItem ? ((ArmorItem) output.getItem()).getEquipmentSlot() : output.getEquipmentSlot();
		IAttribute attribute = ItemFailyDust.getIAttribute(output, slot);
		if (slot != null)
		{
			addAttributeModifier(output, attribute, slot);
		}
		else
		{
			addAttributeModifier(output, attribute, EquipmentSlotType.MAINHAND);
			addAttributeModifier(output, attribute, EquipmentSlotType.OFFHAND);
		}
	}

	private static final String NAME = "crafted_attribute";

	private static void addAttributeModifier(ItemStack output, IAttribute attribute, EquipmentSlotType slot)
	{
		ListNBT list = new ListNBT();
		for (EquipmentSlotType slotType : EquipmentSlotType.values())
		{
			addAttributeModifier(output, attribute, slotType, list, slotType == slot);
		}
		output.getOrCreateTag().put("AttributeModifiers", list);
	}

	private static void addAttributeModifier(ItemStack output, IAttribute attribute, EquipmentSlotType slotType, ListNBT list, boolean selectedSlot)
	{
		boolean flag = !selectedSlot;
		Multimap<String, AttributeModifier> map = output.getAttributeModifiers(slotType);
		for (String key : map.keySet())
		{
			boolean flag2 = selectedSlot && attribute.getName().equals(key);
			for (AttributeModifier modifierOld : map.get(key))
			{
				flag2 &= modifierOld.getName().equals(NAME);
				list.add(getModifier(flag2 ? getAttributeModifier(true, modifierOld.getAmount() + 0.01D) : modifierOld, key, slotType));
				flag = flag || flag2;
			}
		}
		if (!flag)
		{
			list.add(getModifier(getAttributeModifier(true, 0.01D), attribute.getName(), slotType));
		}
	}

	private static CompoundNBT getModifier(AttributeModifier modifier, String attribute, EquipmentSlotType slot)
	{
		CompoundNBT compoundnbt = SharedMonsterAttributes.writeAttributeModifier(modifier);
		compoundnbt.putString("AttributeName", attribute);
		compoundnbt.putString("Slot", slot.getName());
		return compoundnbt;
	}

	private static AttributeModifier getAttributeModifier(boolean isMultiply, double amount)
	{
		AttributeModifier.Operation operation = isMultiply ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION;
		return new AttributeModifier(NAME, amount, operation);
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModuleFaily.recipeAttribute;
	}
}
