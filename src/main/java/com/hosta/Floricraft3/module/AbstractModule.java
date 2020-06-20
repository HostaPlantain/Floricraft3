package com.hosta.Floricraft3.module;

import com.hosta.Flora.module.Module;
import com.hosta.Floricraft3.Floricraft3;

public class AbstractModule extends Module {

	public AbstractModule(String name)
	{
		super(name);
	}

	@Override
	public boolean isEnable()
	{
		return isEnableModule(NAME);
	}

	public static boolean isEnableModule(String name)
	{
		return Floricraft3.CONFIG_COMMON.ENABLE_MODULES.get(name).get();
	}
}
