package com.hosta.Floricraft3.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.block.BlockBase;
import com.hosta.Flora.block.BlockBaseCrops;
import com.hosta.Flora.block.BlockBaseFalling;
import com.hosta.Flora.block.BlockBaseOre;
import com.hosta.Flora.item.ItemBaseColor;
import com.hosta.Flora.item.ItemBasePotionTooltip;
import com.hosta.Flora.module.AbstractModule;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemSachet;
import com.hosta.Floricraft3.item.ItemVial;
import com.hosta.Floricraft3.potion.EffectActive;
import com.hosta.Floricraft3.potion.EffectAntis;
import com.hosta.Floricraft3.recipe.RecipeBrewingFloricraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

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

		// Vial
		register("vial_empty", new ItemVial(this.mod));
		register("vial_water", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_moon", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_flower", new ItemBasePotionTooltip(this.mod.getDefaultProp().maxStackSize(1)));
		register("vial_mix", new ItemBasePotionTooltip(this.mod.getDefaultProp().maxStackSize(1)));

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
		for (String a : Floricraft3.CONFIG_COMMON.addedAntiPotions.get())
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
		BrewingRecipeRegistry.addRecipe(new RecipeBrewingFloricraft());
	}
}
