package com.hosta.Floricraft3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String	MOD_ID			= "floricraft3";
	public static final String	MOD_ID_SHORT	= "fc3";

	public static final String[]	FLOWERS_VANILLA	= new String[] { "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "cornflower", "lily", "wither_rose", "sunflower", "lilac", "rose", "peony" };
	public static final String[]	FLOWERS_FC		= new String[] { "sakura" };
	public static final String[]	FLOWERS;
	static
	{
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(FLOWERS_VANILLA));
		list.addAll(Arrays.asList(FLOWERS_FC));

		FLOWERS = list.toArray(new String[list.size()]);
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}
}
