package com.hosta.Floricraft3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.module.AbstractMod;
import com.hosta.Flora.registry.Registries;
import com.hosta.Floricraft3.module.ModuleFloricraft;
import com.hosta.Floricraft3.proxy.ClientProxy;
import com.hosta.Floricraft3.proxy.CommonProxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Reference.MOD_ID)
public class Floricraft3 extends AbstractMod{

	public static Floricraft3 	instance;
	public static CommonProxy	proxy;

	public static final Logger		LOGGER	= LogManager.getLogger(Reference.MOD_ID);
	private static final Registries REGISTRY = new Registries();

	public Floricraft3()
	{
		Floricraft3.instance = this;
		Floricraft3.proxy = DistExecutor.runForDist(() -> () -> new CommonProxy(), () -> () -> new ClientProxy());
		this.getRegistry().register(new ModuleFloricraft(Floricraft3.instance));
	}

	@Override
	public Registries getRegistry()
	{
		return REGISTRY;
	}
	
	@ObjectHolder(Reference.MOD_ID + ":stack_flower")
	public static Item stackFlower;
	public static final ItemGroup	TAB		= new ItemGroup(Reference.MOD_ID)
	{
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(stackFlower);
		}
	};
	private static final Item.Properties	PROP_ITEM	= new Item.Properties().group(Floricraft3.TAB);

	@Override
	public Item.Properties getDefaultProp()
	{
		return PROP_ITEM;
	}

	@Override
	public String getID()
	{
		return Reference.MOD_ID;
	}
}
