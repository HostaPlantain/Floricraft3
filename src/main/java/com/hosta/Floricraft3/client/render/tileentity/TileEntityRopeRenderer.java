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
			float front = (float) tileEntityIn.getBlockState().get(BlockBaseHorizontal.FACING).getHorizontalIndex() / 2;
			double radians = Math.PI - front * Math.PI;
			double[] pos = new double[3];
			pos[0] = Math.sin(radians) * 0.7D / 1.6D;
			pos[1] = 0.0D;
			pos[2] = Math.cos(radians) * 0.7D / 1.6D;
			float[] rotate = new float[3];
			rotate[0] = 0.0F;
			rotate[1] = 180.0F - front * 180.0F;
			rotate[2] = 135.0F;
			renderItem(matrixStackIn, pos, rotate, 0.7F, itemstack, combinedLightIn, bufferIn);
		}
	}
}
