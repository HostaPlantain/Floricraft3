package com.hosta.Floricraft3.client.render.tileentity;

import com.hosta.Flora.block.BlockBaseHorizontal;
import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;
import com.hosta.Floricraft3.tileentity.TileEntityRope;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TileEntityRopeRenderer extends TileEntityBaseRenderer<TileEntityRope> {

	public TileEntityRopeRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileEntityRope tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		ItemStack itemstack = tileEntityIn.getStackInSlot(0);
		if (!itemstack.isEmpty())
		{
			float front = 1.0F - (float) tileEntityIn.getBlockState().get(BlockBaseHorizontal.FACING).getHorizontalIndex() / 2;
			double radians = front * Math.PI;
			double[] pos = new double[] { Math.sin(radians) * 0.7D / 1.6D + 0.5D, 0.5D, Math.cos(radians) * 0.7D / 1.6D + 0.5D };
			float[] rotate = new float[] { 0.0F, front * 180.0F, 135.0F };
			renderItem(matrixStackIn, pos, rotate, 0.7F, itemstack, combinedLightIn, bufferIn);
		}
	}
}
