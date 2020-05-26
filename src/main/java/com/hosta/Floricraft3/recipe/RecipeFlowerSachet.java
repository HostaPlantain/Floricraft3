package com.hosta.Floricraft3.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.hosta.Flora.potion.EffectInstanceBuilder;
import com.hosta.Flora.recipe.RecipeBaseShaped;
import com.hosta.Flora.recipe.RecipeBaseShapedSerializer;
import com.hosta.Flora.recipe.RecipeBaseShapelessSerializer;
import com.hosta.Flora.util.UtilHelper;
import com.hosta.Floricraft3.module.ModuleCore;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeFlowerSachet extends RecipeBaseShaped {

	private final int[] IDs;

	public RecipeFlowerSachet(RecipeBaseShapelessSerializer<?>.Builder builder, String[] strs)
	{
		this(builder, getIDs(strs));
	}

	public RecipeFlowerSachet(RecipeBaseShapelessSerializer<?>.Builder builder, int[] ids)
	{
		super(builder);
		IDs = ids;
		List<EffectInstance> list = new ArrayList<EffectInstance>();
		for (int id : IDs)
		{
			list.add(new EffectInstance(EffectInstanceBuilder.passiveOf(id)));
		}
		PotionUtils.appendEffects(this.OUTPUT, list);
	}

	private static int[] getIDs(String[] strs)
	{
		int[] ids = new int[strs.length];
		for (int i = 0; i < ids.length; ++i)
		{
			Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(strs[i]));
			ids[i] = Effect.getId(effect);
		}
		return ids;
	}

	public static class Serializer extends RecipeBaseShapedSerializer<RecipeFlowerSachet> {

		@Override
		public RecipeFlowerSachet read(ResourceLocation recipeId, JsonObject json)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, json);
			String[] effects = UtilHelper.getStringArray(JSONUtils.getJsonArray(json, "effects"));
			return new RecipeFlowerSachet(builder, effects);
		}

		@Override
		public RecipeFlowerSachet read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, buffer);
			int[] ids = buffer.readVarIntArray();
			return new RecipeFlowerSachet(builder, ids);
		}

		@Override
		public void write(PacketBuffer buffer, RecipeFlowerSachet recipe)
		{
			super.write(buffer, recipe);
			buffer.writeVarIntArray(recipe.IDs);
		}
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModuleCore.recipeSachet;
	}

}
