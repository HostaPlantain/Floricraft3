package com.hosta.Floricraft3.module;

import com.hosta.Flora.block.ITileEntitySupplier;
import com.hosta.Flora.client.registry.ClientRegistries;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.block.BlockEntityFlowerBed;
import com.hosta.Floricraft3.block.BlockEntityFlowerPot;
import com.hosta.Floricraft3.block.BlockEntityFlowerPotWater;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerBed;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPot;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPotWater;
import com.hosta.Floricraft3.world.biome.BiomeFlowerLand;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleOrnamental extends AbstractModule {

	@ObjectHolder(Reference.MOD_ID + ":flower_pot")
	public static Block									flowerPot;
	@ObjectHolder(Reference.MOD_ID + ":flower_pot")
	public static TileEntityType<TileEntityFlowerPot>	typeFlowerPot;

	@ObjectHolder(Reference.MOD_ID + ":flower_pot_water")
	public static Block										flowerPotWater;
	@ObjectHolder(Reference.MOD_ID + ":flower_pot_water")
	public static TileEntityType<TileEntityFlowerPotWater>	typeFlowerPotWater;

	@ObjectHolder(Reference.MOD_ID + ":flower_bed")
	public static Block									flowerBed;
	@ObjectHolder(Reference.MOD_ID + ":flower_bed")
	public static TileEntityType<TileEntityFlowerBed>	typeFlowerBed;

	public static final Tag<Item>	PLANTABLE		= new ItemTags.Wrapper(Reference.getResourceLocation("plantables"));
	public static final Tag<Item>	PLANTABLE_WATER	= new ItemTags.Wrapper(Reference.getResourceLocation("plantables_in_water"));

	public ModuleOrnamental(String name)
	{
		super(name);
	}

	@Override
	public void registerBlocks()
	{
		register("flower_pot", new BlockEntityFlowerPot(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid(), TileEntityFlowerPot::new));
		register("flower_pot_water", new BlockEntityFlowerPotWater(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid(), TileEntityFlowerPotWater::new));
		register("flower_bed", new BlockEntityFlowerBed(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.0F).notSolid(), TileEntityFlowerBed::new));
	}

	@Override
	public void registerTileEntities()
	{
		register("flower_pot", ITileEntitySupplier.getType(flowerPot));
		register("flower_pot_water", ITileEntitySupplier.getType(flowerPotWater));
		register("flower_bed", ITileEntitySupplier.getType(flowerBed));
	}

	@Override
	public void registerBiomes()
	{
		Biome.Builder builder = new Biome.Builder();
		builder.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
		builder.precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS);
		builder.depth(0.3F).scale(0.0F).temperature(0.8F).downfall(0.4F).waterColor(4159204).waterFogColor(329011).parent((String) null);
		Biome roseLand = register("rose_land", new BiomeFlowerLand(builder, Blocks.ROSE_BUSH));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(roseLand, 5));
		Biome tulipLand = register("tulip_land", new BiomeFlowerLand(builder, Blocks.ORANGE_TULIP, Blocks.PINK_TULIP, Blocks.RED_TULIP, Blocks.WHITE_TULIP));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(tulipLand, 5));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerModels()
	{
		ClientRegistries.BLOCK_COLORS.register(ClientRegistries.GRASS_COLOR, flowerBed);
		ClientRegistries.ITEM_COLORS.register(ClientRegistries.COLOR_FROM_BLOCK, flowerBed.asItem());
	}
}
