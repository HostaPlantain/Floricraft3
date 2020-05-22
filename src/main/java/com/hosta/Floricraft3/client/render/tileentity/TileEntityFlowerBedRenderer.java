package com.hosta.Floricraft3.client.render.tileentity;

import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerBed;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.MathHelper;

public class TileEntityFlowerBedRenderer extends TileEntityBaseRenderer<TileEntityFlowerBed> {

	private static final double SQ2 = MathHelper.sqrt(2.0D);

	public TileEntityFlowerBedRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileEntityFlowerBed tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		int count = 0;
		for (int i = tileEntityIn.getSizeInventory() - 1; i >= 0; --i)
		{
			BlockState flower = tileEntityIn.getFlower(i);
			if (flower.getBlock() != Blocks.AIR)
			{
				if (count == 0)
				{
					count = i + 1;
				}
				double radians = (1.0F - (float) i / count) * 2 * Math.PI;
				float size = 1.0F / MathHelper.sqrt(count);
				double[] pos = new double[] { fixLocation(Math.sin(radians), size), 0.1D, fixLocation(Math.cos(radians), size) };
				renderPlant(matrixStackIn, pos, ROTATE0, size, tileEntityIn, flower, combinedLightIn, bufferIn);
			}
		}
	}

	private static double fixLocation(double doub, float size)
	{
		doub *= SQ2;
		doub = Math.min(doub, 1.0D);
		doub = Math.max(doub, -1.0D);
		return (doub + 1.0F) * (1.0F - size) / 2;
	}
}
