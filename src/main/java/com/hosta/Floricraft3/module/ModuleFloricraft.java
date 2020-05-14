package com.hosta.Floricraft3.module;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hosta.Flora.block.BlockBase;
import com.hosta.Flora.block.BlockBaseCrops;
import com.hosta.Flora.block.BlockBaseFalling;
import com.hosta.Flora.block.BlockBaseOre;
import com.hosta.Flora.block.ITileEntitySupplier;
import com.hosta.Flora.item.ItemBaseColor;
import com.hosta.Flora.item.ItemBasePotionTooltip;
import com.hosta.Flora.module.AbstractModule;
import com.hosta.Flora.potion.EffectInstanceBuilder;
import com.hosta.Flora.potion.PotionBase;
import com.hosta.Flora.recipe.SingleItemRecipeBase;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.block.BlockRope;
import com.hosta.Floricraft3.event.EventHandler;
import com.hosta.Floricraft3.item.ItemSachet;
import com.hosta.Floricraft3.item.ItemVial;
import com.hosta.Floricraft3.item.ItemVialFlower;
import com.hosta.Floricraft3.potion.EffectActive;
import com.hosta.Floricraft3.potion.EffectAntis;
import com.hosta.Floricraft3.recipe.RecipeBrewingVial;
import com.hosta.Floricraft3.recipe.RecipeDrying;
import com.hosta.Floricraft3.tileentity.TileEntityRope;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleFloricraft extends AbstractModule {

	@ObjectHolder(Reference.MOD_ID + ":rope")
	public static Block								rope;
	@ObjectHolder(Reference.MOD_ID + ":rope")
	public static TileEntityType<TileEntityRope>	typeRope;

	@ObjectHolder(Reference.MOD_ID + ":seed_flax")
	public static Item	seedFlax;
	@ObjectHolder(Reference.MOD_ID + ":stack_flower")
	public static Item	stackFlower;
	@ObjectHolder(Reference.MOD_ID + ":stack_dry_flower")
	public static Item	stackDryFlower;
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

	@ObjectHolder(Reference.MOD_ID + ":drying")
	public static IRecipeSerializer<?> recipeDrying;

	public static final Tag<Item> PETAL_RAW = new ItemTags.Wrapper(Reference.getResourceLocation("petals/raw_all"));

	@Override
	public void preInit()
	{
		registerEventHandler(new EventHandler());
	}

	@Override
	public void registerBlocks()
	{
		// TileEntity
		register("rope", new BlockRope(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.1F).sound(SoundType.CLOTH), TileEntityRope::new));
		// Crop & Seed
		register("crop_flax", new BlockBaseCrops("seed_flax", Material.PLANTS));
		// Material
		register("block_twinkle", new BlockBase(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
		register("ore_salt", new BlockBaseOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
		register("block_salt", new BlockBaseFalling(0xFFFFFF, Block.Properties.create(Material.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND)));
	}

	@Override
	public void registerTileEntities()
	{
		register("rope", ITileEntitySupplier.getType(rope));
	}

	@Override
	public void registerItems()
	{
		// Cut Flower
		for (String flower : Reference.FLOWERS)
		{
			registerItems("cut_" + flower);
		}
		registerItems("stack_flower", "stack_dry_flower");
		// Flower Petal
		for (DyeColor color : DyeColor.values())
		{
			register("petal_raw_" + color.getTranslationKey(), new ItemBaseColor(color, this.mod));
			registerItems("petals_salt_" + color.getTranslationKey());
		}
		registerItems("petal_dry", "petals_dry");
		// Flax Item
		registerItems("flax_yarn", "flax_twine", "flax_spool", "flax_cloth");
		// Vial
		register("vial_empty", new ItemVial(this.mod));
		register("vial_water", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_moon", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_flower", new ItemVialFlower(this.mod));
		register("vial_mix", new ItemBasePotionTooltip(this.mod));
		// Sachet
		registerItems("sachet_sac");
		register("sachet_flower", new ItemSachet(7200, this.mod));
		// Material
		registerItems("ingot_twinkle", "nugget_twinkle", "dust_salt");
	}

	@Override
	public void registerEffects()
	{
		register("floric", new EffectActive(EffectType.BENEFICIAL, 0xFFDAFF));
		for (String str : Floricraft3.CONFIG_COMMON.addedAntiPotions.get())
		{
			JsonObject json = (JsonObject) new JsonParser().parse(str);
			String name = "anti_" + json.get("name").getAsString();
			List<EntityType<?>> types = new ArrayList<EntityType<?>>();
			for (JsonElement anti : json.get("types").getAsJsonArray())
			{
				types.add(EntityType.byKey(anti.getAsString()).get());
			}
			register(name, new EffectAntis(types.toArray(new EntityType[types.size()]), json.get("recipe")));
		}
	}

	@Override
	public void registerPotions(List<Effect> list)
	{
		EffectInstance floric = EffectInstanceBuilder.passiveOf(effectFloric);
		List<Potion> sachetFlower = new ArrayList<Potion>();
		for (Effect effect : list)
		{
			if (effect == effectFloric)
			{
				Potion potion = register(effectFloric.getRegistryName().getPath() + "_passive", new PotionBase(floric));
				sachetFlower.add(potion);
			}
			else if (effect instanceof EffectAntis)
			{
				EffectInstance anti = EffectInstanceBuilder.passiveOf(effect);
				Potion potion = register(effectFloric.getRegistryName().getPath() + "_" + effect.getRegistryName().getPath() + "_passive", new PotionBase(floric, anti));
				sachetFlower.add(potion);
			}
		}
		ItemSachet.setPotionList(sachetFlower);
		super.registerPotions(list);
	}

	@Override
	public void registerRecipes()
	{
		register("drying", new SingleItemRecipeBase.Serializer<>(RecipeDrying::new));
	}

	@Override
	public void registerPotionRecipes(List<Potion> list)
	{
		List<Potion> vialFlower = new ArrayList<Potion>();
		for (Potion potion : list)
		{
			if (potion.getEffects().size() == 1)
			{
				Effect effect = potion.getEffects().get(0).getPotion();
				if (potion == potionFloric)
				{
					register(new RecipeBrewingVial(Ingredient.fromTag(PETAL_RAW), potionFloric, true));
					vialFlower.add(potion);
					break;
				}
				else if (effect instanceof EffectAntis)
				{
					register(new RecipeBrewingVial(((EffectAntis) effect).getRecipe(), potion, false));
					vialFlower.add(potion);
					break;
				}
			}
		}
		ItemVialFlower.setPotionList(vialFlower);
	}
}
