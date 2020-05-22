package com.hosta.Floricraft3.client.render.tileentity;

import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPot;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TileEntityFlowerPotRenderer<T extends TileEntityFlowerPot> extends TileEntityBaseRenderer<T> {

	public TileEntityFlowerPotRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		BlockState flower = tileEntityIn.getFlower();
		if (flower.getBlock() != Blocks.AIR)
		{
			double[] pos = new double[] { 0.2D, 0.3D, 0.2D };
			renderPlant(matrixStackIn, pos, ROTATE0, 0.6F, tileEntityIn, flower, combinedLightIn, bufferIn);
		}
	}
}
