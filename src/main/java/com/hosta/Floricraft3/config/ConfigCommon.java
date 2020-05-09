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

	private static final String	ANTI_ZOMBIE		= "{\"name\": \"zombie\", \"recipe\": {\"item\": \"minecraft:rotten_flesh\"}, \"types\": [\"zombie\", \"zombie_villager\", \"husk\", \"drowned\"]}";
	private static final String	ANTI_SKELETON	= "{\"name\": \"skeleton\", \"recipe\": {\"item\": \"minecraft:bone\"}, \"types\": [\"skeleton\", \"wither_skeleton\", \"stray\"]}";
	private static final String	ANTI_CREEPER	= "{\"name\": \"creeper\", \"recipe\": {\"item\": \"minecraft:gunpowder\"}, \"types\": [\"creeper\"]}";
	private static final String	ANTI_SPIDER		= "{\"name\": \"spider\", \"recipe\": {\"item\": \"minecraft:spider_eye\"}, \"types\": [\"spider\", \"cave_spider\"]}";
	private static final String	ANTI_ENDER		= "{\"name\": \"ender\", \"recipe\": {\"item\": \"minecraft:ender_pearl\"}, \"types\": [\"enderman\", \"endermite\"]}";

	private static final String[] ANTIS_DEFAULT = new String[] { ANTI_ZOMBIE, ANTI_SKELETON, ANTI_CREEPER, ANTI_SPIDER, ANTI_ENDER };

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder.comment("Common settings for Floricraft3.").push("common");

		addedAntiPotions = builder.comment("Additional Potion Effect to Avoid Creatures").translation("flora.configgui.addedAntiPotions").defineList("addedAntiPotions", Arrays.asList(ANTIS_DEFAULT), (s) -> {
			return true;
		});

		builder.pop();
		return this;
	}
}
