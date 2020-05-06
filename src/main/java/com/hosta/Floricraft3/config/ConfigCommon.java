package com.hosta.Floricraft3.config;

import java.util.Arrays;
import java.util.List;

import com.hosta.Flora.config.AbstractConfig;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ConfigCommon extends AbstractConfig {

	public ConfigCommon()
	{
		super(Type.COMMON);
	}

	public ConfigValue<List<? extends String>> addedAntiPotions;
	private static final String[]	ANTIS_DEFAULT	= new String[]
			{
					"zombie;zombie;zombie_villager;husk;drowned",
					"skeleton;skeleton;wither_skeleton;stray",
					"creeper;creeper",
					"spider;spider;cave_spider",
					"ender;enderman;endermite"
			};

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder
			.comment("Common settings for Floricraft3.")
			.push("common");

		addedAntiPotions = builder
				.comment("Additional Potion Effect to Avoid Creatures")
				.translation("flora.configgui.addedAntiPotions")
				.defineList("addedAntiPotions", Arrays.asList(ANTIS_DEFAULT), (s) -> {return true;});

		builder.pop();

		return this;
	}
}
