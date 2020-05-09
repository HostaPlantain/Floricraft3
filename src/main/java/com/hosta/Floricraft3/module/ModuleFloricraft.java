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
import com.hosta.Floricraft3.item.ItemVialFlower;
import com.hosta.Floricraft3.potion.EffectActive;
import com.hosta.Floricraft3.potion.EffectAntis;
import com.hosta.Floricraft3.recipe.RecipeBrewingVial;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleFloricraft extends AbstractModule {

	@ObjectHolder(Reference.MOD_ID + ":stack_flower")
	public static Item	stackFlower;
	@ObjectHolder(Reference.MOD_ID + ":vial_water")
	public static Item	vialWater;
	@ObjectHolder(Reference.MOD_ID + ":vial_moon")
	public static Item	vialMoon;
	@ObjectHolder(Reference.MOD_ID + ":vial_flower")
	public static Item	vialFlower;

	@ObjectHolder(Reference.MOD_ID + ":floric")
	public static Effect	effectFloric;
	@ObjectHolder(Reference.MOD_ID + ":floric")
	public static Potion	potionFloric;

	public static final Tag<Item>	VIALS		= new ItemTags.Wrapper(Reference.getResourceLocation("vials"));
	public static final Tag<Item>	PETAL_RAW	= new ItemTags.Wrapper(Reference.getResourceLocation("petals/raw_all"));

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
		register("vial_flower", new ItemVialFlower(this.mod));
		register("vial_mix", new ItemBasePotionTooltip(this.mod));

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
		for (String str : Floricraft3.CONFIG_COMMON.addedAntiPotions.get())
		{
			String[] anti = str.split(";");
			List<EntityType<?>> types = new ArrayList<EntityType<?>>();
			for (int i = 1; i < anti.length; ++i)
			{
				types.add(EntityType.byKey(anti[i]).get());
			}
			register("anti_" + anti[0], new EffectAntis(EffectType.BENEFICIAL, 0xFFDAFF, types.toArray(new EntityType[types.size()])));
		}
	}

	@Override
	public void registerPotionRecipes(List<Potion> list)
	{
		ItemVialFlower.setPotionList(list);
		for (Potion potion : list)
		{
			if (potion == potionFloric)
			{
				register(new RecipeBrewingVial(Ingredient.fromTag(PETAL_RAW), potionFloric, true));
			}
		}
	}
}
