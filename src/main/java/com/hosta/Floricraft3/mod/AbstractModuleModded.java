package com.hosta.Floricraft3.mod;

import com.hosta.Flora.module.ModuleModded;
import com.hosta.Floricraft3.Floricraft3;

public abstract class AbstractModuleModded extends ModuleModded {

	@Override
	public boolean isEnable()
	{
		return isEnableModule(modName);
	}

	private static boolean isEnableModule(String name)
	{
		return Floricraft3.CONFIG_COMMON.IS_ENABLE_MODDED_MODULE.get(name).get();
	}
}
