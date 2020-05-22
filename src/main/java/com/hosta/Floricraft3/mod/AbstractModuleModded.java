package com.hosta.Floricraft3.mod;

import com.hosta.Flora.module.IModDependency;
import com.hosta.Floricraft3.module.AbstractModule;

public abstract class AbstractModuleModded extends AbstractModule implements IModDependency {

	private String modName;

	public AbstractModuleModded(String name)
	{
		super(name);
	}

	public void setMod(String modName)
	{
		this.modName = modName;
	}

	public String getModName()
	{
		return modName;
	}
}
