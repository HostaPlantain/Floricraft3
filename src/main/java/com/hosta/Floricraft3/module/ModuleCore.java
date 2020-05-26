package com.hosta.Floricraft3.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.block.BlockBaseCrops;
import com.hosta.Flora.block.BlockBaseFalling;
import com.hosta.Flora.block.BlockBaseOre;
import com.hosta.Flora.block.ITileEntitySupplier;
import com.hosta.Flora.item.ItemBaseColor;
import com.hosta.Flora.item.ItemBasePotionTooltip;
import com.hosta.Flora.module.Module;
import com.hosta.Flora.recipe.RecipeBaseSingleItem;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.block.BlockPotPourri;
import com.hosta.Floricraft3.block.BlockRope;
import com.hosta.Floricraft3.item.ItemSachet;
import com.hosta.Floricraft3.item.ItemVial;
import com.hosta.Floricraft3.item.ItemVialFlower;
import com.hosta.Floricraft3.potion.EffectActive;
import com.hosta.Floricraft3.potion.EffectAntis;
import com.hosta.Floricraft3.recipe.RecipeBrewingVial;
import com.hosta.Floricraft3.recipe.RecipeDrying;
import com.hosta.Floricraft3.recipe.RecipeFlowerSachet;
import com.hosta.Floricraft3.tileentity.TileEntityPotPourri;
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
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleCore extends Module {

	@ObjectHolder(Reference.MOD_ID + ":rope")
	public static Block								rope;
	@ObjectHolder(Reference.MOD_ID + ":rope")
	public static TileEntityType<TileEntityRope>	typeRope;

	@ObjectHolder(Reference.MOD_ID + ":potpourri")
	public static Block									potPourri;
	@ObjectHolder(Reference.MOD_ID + ":potpourri")
	public static TileEntityType<TileEntityPotPourri>	typePotPourri;

	@ObjectHolder(Reference.MOD_ID + ":ore_salt")
	public static Block oreSalt;

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

	@ObjectHolder(Reference.MOD_ID + ":crafting_sachet")
	public static IRecipeSerializer<?>	recipeSachet;
	@ObjectHolder(Reference.MOD_ID + ":drying")
	public static IRecipeSerializer<?>	recipeDrying;

	public static final Tag<Item>	PETALS_RAW	= new ItemTags.Wrapper(Reference.getResourceLocation("petalss/raw_all"));
	public static final Tag<Item>	PETALS_SALT	= new ItemTags.Wrapper(Reference.getResourceLocation("petalss/salt_all"));

	@Override
	public void registerBlocks()
	{
		// TileEntity
		register("rope", new BlockRope(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.1F).sound(SoundType.CLOTH), TileEntityRope::new));
		register("potpourri", new BlockPotPourri(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.1F).sound(SoundType.GLASS), TileEntityPotPourri::new));
		// Crop & Seed
		register("crop_flax", new BlockBaseCrops("seed_flax", Material.PLANTS));
		// Material
		register("ore_salt", new BlockBaseOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
		register("block_salt", new BlockBaseFalling(0xFFFFFF, Block.Properties.create(Material.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND)));
	}

	@Override
	public void registerTileEntities()
	{
		register("rope", ITileEntitySupplier.getType(rope));
		register("potpourri", ITileEntitySupplier.getType(potPourri));
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
			register("petals_raw_" + color.getTranslationKey(), new ItemBaseColor(color, this.mod));
			register("petals_salt_" + color.getTranslationKey(), new ItemBaseColor(color, this.mod));
		}
		registerItems("petal_dry", "petals_dry");
		// Flax Item
		registerItems("flax_yarn", "flax_twine", "flax_spool", "flax_cloth", "sachet_sac");
		// Vial
		register("vial_empty", new ItemVial(this.mod));
		register("vial_water", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_moon", this.mod.getDefaultProp().maxStackSize(1));
		register("vial_flower", new ItemVialFlower(this.mod));
		register("vial_mix", new ItemBasePotionTooltip(this.mod));
		// Sachet
		register("sachet_flower", new ItemSachet(7200, this.mod));
		// Material
		registerItems("dust_salt");
	}

	@Override
	public void registerEffects()
	{
		register("floric", new EffectActive(EffectType.BENEFICIAL, 0xFFDAFF));
		for (List<String> potions : Floricraft3.CONFIG_COMMON.addedAntiPotions.get())
		{
			String name = potions.get(0);
			Tag<EntityType<?>> tag = new EntityTypeTags.Wrapper(Reference.getResourceLocation(name));
			ResourceLocation recipe = new ResourceLocation(potions.get(1));
			register(name, new EffectAntis(EffectType.NEUTRAL, 0xFFDAFF, tag, recipe));
		}
	}

	@Override
	public void registerPotions(List<Effect> list)
	{
		ItemSachet.setPotionList(list);
	}

	@Override
	public void registerRecipes()
	{
		register("crafting_sachet", new RecipeFlowerSachet.Serializer());
		register("drying", new RecipeBaseSingleItem.Serializer<>(RecipeDrying::new));
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
					register(new RecipeBrewingVial(Ingredient.fromTag(PETALS_RAW), potionFloric, true));
					vialFlower.add(potion);
				}
				else if (effect instanceof EffectAntis)
				{
					register(new RecipeBrewingVial(((EffectAntis) effect).getRecipe(), potion, false));
					vialFlower.add(potion);
				}
			}
		}
		ItemVialFlower.setPotionList(vialFlower);
	}

	@Override
	public void setup(FMLCommonSetupEvent event)
	{
		for (Biome biome : GameRegistry.findRegistry(Biome.class).getValues())
		{
			if (biome.getSurfaceBuilderConfig() == SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
			{
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, oreSalt.getDefaultState(), 20)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
			}
		}
	}
}
