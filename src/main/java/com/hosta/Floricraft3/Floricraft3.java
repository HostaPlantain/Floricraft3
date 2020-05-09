package com.hosta.Floricraft3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.IMod;
import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Flora.registry.RegistryHandler;
import com.hosta.Floricraft3.config.ConfigCommon;
import com.hosta.Floricraft3.event.EventHandler;
import com.hosta.Floricraft3.module.ModuleFloricraft;
import com.hosta.Floricraft3.proxy.ProxyClient;
import com.hosta.Floricraft3.proxy.ProxyCommon;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(Reference.MOD_ID)
public class Floricraft3 implements IMod {

	public static final Logger			LOGGER			= LogManager.getLogger(Reference.MOD_ID);
	public static final ProxyCommon		PROXY			= DistExecutor.runForDist(() -> () -> new ProxyCommon(), () -> () -> new ProxyClient());
	public static final ConfigCommon	CONFIG_COMMON	= new ConfigCommon();

	public Floricraft3()
	{
		AbstractConfig.registerConfigs(CONFIG_COMMON);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		new RegistryHandler(this, new ModuleFloricraft());
	}

	public static final ItemGroup TAB = new ItemGroup(Reference.MOD_ID)
	{
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ModuleFloricraft.stackFlower);
		}
	};

	@Override
	public ItemGroup getTab()
	{
		return Floricraft3.TAB;
	}
}
