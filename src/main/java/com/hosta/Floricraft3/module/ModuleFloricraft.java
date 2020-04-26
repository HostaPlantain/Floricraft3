package com.hosta.Floricraft3.module;

import com.hosta.Flora.block.BlockBase;
import com.hosta.Flora.block.BlockBaseCrops;
import com.hosta.Flora.block.BlockBaseFalling;
import com.hosta.Flora.block.BlockBaseOre;
import com.hosta.Flora.item.ItemBaseColor;
import com.hosta.Flora.module.AbstractMod;
import com.hosta.Flora.module.AbstractModule;
import com.hosta.Flora.potion.EffectBase;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemSachet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.potion.EffectType;

public class ModuleFloricraft extends AbstractModule {

	public ModuleFloricraft(AbstractMod mod)
	{
		super(mod);
	}

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
			register("petal_raw_" + color.getTranslationKey(), new ItemBaseColor(color, this.MOD));
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
		register("sachet_flower", new ItemSachet(7200, this.MOD));

		// Twinkle Metal
		register("ingot_twinkle");
		register("nugget_twinkle");

		// Salt
		register("dust_salt");
	}

	@Override
	public void registerEffects()
	{
		register("floric", new EffectBase(EffectType.BENEFICIAL, 0xFFDAFF));
	}
}
