package com.hosta.Floricraft3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.IMod;
import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Flora.module.Module;
import com.hosta.Floricraft3.config.ConfigCommon;
import com.hosta.Floricraft3.mod.botania.ModuleBotania;
import com.hosta.Floricraft3.mod.curios.ModuleCurios;
import com.hosta.Floricraft3.mod.jei.ModuleJEI;
import com.hosta.Floricraft3.mod.tetra.ModuleTetra;
import com.hosta.Floricraft3.mod.top.ModuleTOP;
import com.hosta.Floricraft3.module.ModuleFloricraft;
import com.hosta.Floricraft3.proxy.ProxyClient;
import com.hosta.Floricraft3.proxy.ProxyCommon;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(Reference.MOD_ID)
public class Floricraft3 implements IMod {

	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	public static final ProxyCommon		PROXY			= DistExecutor.runForDist(() -> () -> new ProxyCommon(), () -> () -> new ProxyClient());
	public static final ConfigCommon	CONFIG_COMMON	= new ConfigCommon();

	public static final List<Pair<String, Supplier<Module>>> MODULES = new ArrayList<>();

	public Floricraft3()
	{
		AbstractConfig.registerConfigs(CONFIG_COMMON);
		registerMod();
	}

	@Override
	public void registerModules()
	{
		registerModule(null, ModuleFloricraft::new);
		registerModule(Reference.MOD_ID_BOTANIA, ModuleBotania::new);
		registerModule(Reference.MOD_ID_CURIOS, ModuleCurios::new);
		registerModule(Reference.MOD_ID_JEI, ModuleJEI::new);
		registerModule(Reference.MOD_ID_TETRA, ModuleTetra::new);
		registerModule(Reference.MOD_ID_TOP, ModuleTOP::new);
	}

	@Override
	public List<Pair<String, Supplier<Module>>> getModuleList()
	{
		return MODULES;
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
