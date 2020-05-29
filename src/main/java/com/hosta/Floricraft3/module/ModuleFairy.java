package com.hosta.Floricraft3.module;

import com.hosta.Floricraft3.item.ItemFairyDust;
import com.hosta.Floricraft3.potion.EffectSizeChanger;

import net.minecraft.potion.EffectType;

public class ModuleFairy extends AbstractModule {

	public ModuleFairy(String name)
	{
		super(name);
	}

	@Override
	public void registerItems()
	{
		registerItems("fairy_wing", "fairy_tale");
		register("fairy_dust", new ItemFairyDust(this.mod));
	}

	@Override
	public void registerEffects()
	{
		register("bigger", new EffectSizeChanger(EffectType.NEUTRAL, 0, true));
		register("smaller", new EffectSizeChanger(EffectType.NEUTRAL, 0, false));
	}
}
