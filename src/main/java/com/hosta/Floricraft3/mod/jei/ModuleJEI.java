package com.hosta.Floricraft3.mod.jei;

import java.util.Arrays;

import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.item.ItemVial;
import com.hosta.Floricraft3.module.ModuleCore;
import com.hosta.Floricraft3.tileentity.TileEntityRope;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class ModuleJEI implements IModPlugin {

	public static final String				PLUGIN_ID			= "jei_plugin";
	public static final ResourceLocation	RECIPE_DRYING		= Reference.getResourceLocation("drying");
	public static final ResourceLocation	RECIPE_VIAL_MOON	= Reference.getResourceLocation("vial_moon");

	@Override
	public ResourceLocation getPluginUid()
	{
		return Reference.getResourceLocation(PLUGIN_ID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new RecipeCategoryDryingy(guiHelper, RECIPE_DRYING));
		registration.addRecipeCategories(new RecipeCategoryVial(guiHelper, RECIPE_VIAL_MOON));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		registration.addRecipeCatalyst(new ItemStack(ModuleCore.rope), RECIPE_DRYING);
	}

	@SuppressWarnings("resource")
	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
		registration.addRecipes(TileEntityRope.getRecipes(manager), RECIPE_DRYING);
		registration.addRecipes(Arrays.asList((ItemVial) ModuleCore.vialEmpty), RECIPE_VIAL_MOON);
	}
}
