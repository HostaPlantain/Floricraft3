package com.hosta.Floricraft3.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Floricraft3.Reference;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ConfigCommon extends AbstractConfig {

	private static final String[]	ANTI_ZOMBIE_POTION		= { "anti_zombie", "minecraft:rotten_flesh" };
	private static final String[]	ANTI_SKELETON_POTION	= { "anti_skeleton", "minecraft:bone" };
	private static final String[]	ANTI_CREEPER_POTION		= { "anti_creeper", "minecraft:gunpowder" };
	private static final String[]	ANTI_SPIDER_POTION		= { "anti_spider", "minecraft:spider_eye" };
	private static final String[]	ANTI_ENDER_POTION		= { "anti_ender", "minecraft:ender_pearl" };
	private static final String[][]	ANTI_POTION_DEFAULT		= new String[][] { ANTI_ZOMBIE_POTION, ANTI_SKELETON_POTION, ANTI_CREEPER_POTION, ANTI_SPIDER_POTION, ANTI_ENDER_POTION };

	public ConfigValue<List<? extends List<String>>>	addedAntiPotions;
	public BooleanValue									enableSaltOreGen;

	public BooleanValue enableFlowerLand;

	public final Map<String, BooleanValue> ENABLE_MODULES = new HashMap<String, ForgeConfigSpec.BooleanValue>();

	public ConfigCommon()
	{
		super(Type.COMMON);
	}

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder.comment("Common settings for Floricraft3.").push("common");
		buildCommon(builder);
		builder.pop();

		isEnable(builder, Reference.MODULE_ORNAMENTAL);
		buildOrnamental(builder);
		builder.pop();

		isEnable(builder, Reference.MODULE_FAIRY);
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

	private void buildCommon(Builder builder)
	{
		builder.comment("Settings for effects and potions.").push("effects");
		String antiPotions = "anti_potions";
		builder.comment("Additional Potion Effect to Avoid Creatures").translation(getTransKey(antiPotions));
		Predicate<Object> elementValidator = (e) -> {
			return e instanceof List && ((List<?>) e).size() == 2;
		};
		addedAntiPotions = builder.defineList(antiPotions, toList(ANTI_POTION_DEFAULT), elementValidator);
		builder.pop();

		builder.comment("Settings for salt.").push("salt");
		String genSalt = "gen_salt_ore_enabled";
		builder.comment("Enable salt ore gen").translation(getTransKey(genSalt));
		enableSaltOreGen = builder.define(genSalt, true);
		builder.pop();
	}

	private void buildOrnamental(Builder builder)
	{
		builder.comment("Settings for biomes.").push("biomes");
		String flowerLand = "flower_land_enabled";
		builder.comment("Enable flower land biomes").translation(getTransKey(flowerLand));
		enableFlowerLand = builder.define(flowerLand, true);
		builder.pop();
	}

	private void isEnable(Builder builder, String name)
	{
		builder.comment(String.format("Settings for %s module.", name)).push(name);
		String isEnable = "enable_" + name;
		builder.comment(String.format("Enable %s module.", name)).translation(getTransKey(isEnable));
		BooleanValue value = builder.define(isEnable, true);
		ENABLE_MODULES.put(name, value);
	}

	private static List<List<String>> toList(String[][] strss)
	{
		List<List<String>> list = new ArrayList<List<String>>();
		for (String[] strs : strss)
		{
			list.add(Arrays.asList(strs));
		}
		return list;
	}

	private String getTransKey(String key)
	{
		return Reference.MOD_ID + ".configgui." + key;
	}
}
