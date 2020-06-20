package com.hosta.Floricraft3.module;

import com.hosta.Flora.module.Module;
import com.hosta.Floricraft3.item.ItemDebug;

public class ModuleDebug extends Module {

	@Override
	public void registerItems()
	{
		// Debuger
		register("debug", new ItemDebug(32, this.mod));
	}
}
