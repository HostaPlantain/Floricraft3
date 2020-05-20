package com.hosta.Floricraft3.module;

import com.hosta.Flora.block.ITileEntitySupplier;
import com.hosta.Flora.module.Module;
import com.hosta.Floricraft3.Floricraft3;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.block.BlockEntityFlowerPot;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPot;
import com.hosta.Floricraft3.world.biome.BiomeFlowerLand;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.ObjectHolder;

public class ModuleOrnamental extends Module {

	@ObjectHolder(Reference.MOD_ID + ":flower_pot")
	public static Block									flowerPot;
	@ObjectHolder(Reference.MOD_ID + ":flower_pot")
	public static TileEntityType<TileEntityFlowerPot>	typeFlowerPot;

	public static final Tag<Item> PLANTABLE = new ItemTags.Wrapper(Reference.getResourceLocation("plantables"));

	@Override
	public void registerBlocks()
	{
		// TileEntity
		register("flower_pot", new BlockEntityFlowerPot(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.1F).sound(SoundType.CLOTH), TileEntityFlowerPot::new));
	}

	@Override
	public void registerTileEntities()
	{
		register("flower_pot", ITileEntitySupplier.getType(flowerPot));
	}

	@Override
	public boolean isEnable()
	{
		return Floricraft3.CONFIG_COMMON.IS_ENABLE_MODDED_MODULE.get(Reference.MODULE_ORNAMENTAL).get();
	}

	@Override
	public void registerBiomes()
	{
		Biome.Builder builder = new Biome.Builder();
		builder.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
		builder.precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS);
		builder.depth(0.3F).scale(0.0F).temperature(0.8F).downfall(0.4F).waterColor(4159204).waterFogColor(329011).parent((String) null);
		Biome flowerLand = register("flower_land", new BiomeFlowerLand(builder));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(flowerLand, 5));
	}
}
