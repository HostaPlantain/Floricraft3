package com.hosta.Floricraft3.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.block.BlockBase;
import com.hosta.Flora.block.BlockBaseCrops;
import com.hosta.Flora.block.BlockBaseFalling;
import com.hosta.Flora.block.BlockBaseOre;
import com.hosta.Flora.item.ItemBaseColor;
import com.hosta.Flora.module.AbstractModule;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemSachet;
import com.hosta.Floricraft3.potion.EffectActive;
import com.hosta.Floricraft3.potion.EffectAntis;
import com.hosta.Floricraft3.recipe.RecipeNaming;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.EffectType;

public class ModuleFloricraft extends AbstractModule {

	@Override
	public void registerBlocks()
	{
		// Crop & Seed
		register("crop_flax", new BlockBaseCrops("seed_flax", Material.PLANTS));

		// Twinkle Metal
		register("block_twinkle", new BlockBase(Material.IRON));

		// Salt
		register("ore_salt", new BlockBaseOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 3.0F)));
		register("block_salt", new BlockBaseFalling(0xFFFFFF, Block.Properties.create(Material.SAND)));
	}

	@Override
	public void registerItems()
	{
		// Cut Flower
		for (String flower : Reference.FLOWERS)
		{
			register("cut_" + flower);
		}
		register("stack_flower");
		register("stack_dry_flower");

		// Flower Petal
		for (DyeColor color : DyeColor.values())
		{
			register("petal_raw_" + color.getTranslationKey(), new ItemBaseColor(color, this.mod));
			register("petals_salt_" + color.getTranslationKey());
		}
		register("petal_dry");
		register("petals_dry");

		// Flax Items
		register("flax_yarn");
		register("flax_twine");
		register("flax_spool");
		register("flax_cloth");

		// Sachet
		register("sachet_sac");
		register("sachet_flower", new ItemSachet(7200, this.mod));

		// Twinkle Metal
		register("ingot_twinkle");
		register("nugget_twinkle");

		// Salt
		register("dust_salt");
	}

	@Override
	public void registerEffects()
	{
		register("floric", new EffectActive(EffectType.BENEFICIAL, 0xFFDAFF));
		for (String a : Floricraft3.CONFIG_COMMON.antis.get())
		{
			String[] anti = a.split(";");
			List<EntityType<?>> types = new ArrayList<EntityType<?>>();
			for (int i = 1; i < anti.length; ++i)
			{
				types.add(EntityType.byKey(anti[i]).get());
			}
			register("anti_" + anti[0], new EffectAntis(EffectType.BENEFICIAL, 0xFFDAFF, types.toArray(new EntityType[types.size()])));
		}
	}

	@Override
	public void registerRecipes()
	{
		register("crafting_special_naming", new SpecialRecipeSerializer<>(idIn -> new RecipeNaming(idIn)));
	}
}
