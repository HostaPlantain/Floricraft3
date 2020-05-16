package com.hosta.Floricraft3.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Floricraft3.Reference;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ConfigCommon extends AbstractConfig {

	private static final String		ANTI_ZOMBIE		= "{\"name\": \"zombie\", \"recipe\": {\"item\": \"minecraft:rotten_flesh\"}, \"types\": [\"zombie\", \"zombie_villager\", \"husk\", \"drowned\"]}";
	private static final String		ANTI_SKELETON	= "{\"name\": \"skeleton\", \"recipe\": {\"item\": \"minecraft:bone\"}, \"types\": [\"skeleton\", \"wither_skeleton\", \"stray\"]}";
	private static final String		ANTI_CREEPER	= "{\"name\": \"creeper\", \"recipe\": {\"item\": \"minecraft:gunpowder\"}, \"types\": [\"creeper\"]}";
	private static final String		ANTI_SPIDER		= "{\"name\": \"spider\", \"recipe\": {\"item\": \"minecraft:spider_eye\"}, \"types\": [\"spider\", \"cave_spider\"]}";
	private static final String		ANTI_ENDER		= "{\"name\": \"ender\", \"recipe\": {\"item\": \"minecraft:ender_pearl\"}, \"types\": [\"enderman\", \"endermite\"]}";
	private static final String[]	ANTIS_DEFAULT	= new String[] { ANTI_ZOMBIE, ANTI_SKELETON, ANTI_CREEPER, ANTI_SPIDER, ANTI_ENDER };

	public ConfigValue<List<? extends String>>	addedAntiPotions;
	public final Map<String, BooleanValue>		IS_ENABLE_MODDED_MODULE	= new HashMap<String, ForgeConfigSpec.BooleanValue>();

	public ConfigCommon()
	{
		super(Type.COMMON);
	}

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder.comment("Common settings for Floricraft3.").push("common");

		String antiPotions = "anti_potions";
		builder.comment("Additional Potion Effect to Avoid Creatures").translation(getTransKey(antiPotions));
		addedAntiPotions = builder.defineList(antiPotions, Arrays.asList(ANTIS_DEFAULT), (s) -> {
			return true;
		});
		builder.pop();

		isEnable(builder, Reference.MOD_ID_BOTANIA);
		builder.pop();

		isEnable(builder, Reference.MOD_ID_CURIOS);
		builder.pop();

		isEnable(builder, Reference.MOD_ID_JEI);
		builder.pop();

		isEnable(builder, Reference.MOD_ID_TETRA);
		builder.pop();

		isEnable(builder, Reference.MOD_ID_TOP);
		builder.pop();

		return this;
	}

	private void isEnable(Builder builder, String name)
	{
		builder.comment(String.format("Common settings for %s addon.", name)).push(name);

		String isEnable = "enable_" + name;
		builder.comment(String.format("Enable %s addon.", name)).translation(getTransKey(isEnable));
		BooleanValue value = builder.define(isEnable, true);

		IS_ENABLE_MODDED_MODULE.put(name, value);
	}

	private String getTransKey(String key)
	{
		return Reference.MOD_ID + ".configgui." + key;
	}
}
