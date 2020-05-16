package com.hosta.Floricraft3.mod.botania;

import com.hosta.Flora.block.BlockBase;
import com.hosta.Floricraft3.mod.AbstractModuleModded;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModuleBotania extends AbstractModuleModded {

	@Override
	public void registerBlocks()
	{
		register("block_twinkle", new BlockBase(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
	}

	@Override
	public void registerItems()
	{
		registerItems("ingot_twinkle", "nugget_twinkle");
	}
}
