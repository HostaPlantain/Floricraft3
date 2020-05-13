package com.hosta.Floricraft3.tileentity;

import com.hosta.Flora.tileentity.TileEntityInventoryWithRender;
import com.hosta.Floricraft3.module.ModuleFloricraft;

public class TileEntityRope extends TileEntityInventoryWithRender {

	public TileEntityRope()
	{
		super(ModuleFloricraft.typeRope, 1);
		setInventoryStackLimit(1);
	}
}
