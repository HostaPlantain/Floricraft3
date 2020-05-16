package com.hosta.Floricraft3.client.render.tileentity;

import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;
import com.hosta.Floricraft3.Reference;
import com.hosta.Floricraft3.tileentity.TileEntityPotPourri;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityPotPourriRenderer extends TileEntityBaseRenderer<TileEntityPotPourri> {

	private static final ModelPetalLayer	CUBE				= new TileEntityPotPourriRenderer.ModelPetalLayer(6, 1);
	private static final ResourceLocation[]	RESOURCELOCATION	= new ResourceLocation[DyeColor.values().length];
	static
	{
		for (int i = 0; i < RESOURCELOCATION.length; ++i)
		{
			String path = String.format("textures/block/potpourri_petals_%s.png", DyeColor.byId(i).getTranslationKey());
			RESOURCELOCATION[i] = Reference.getResourceLocation(path);
		}
	}

	public TileEntityPotPourriRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileEntityPotPourri tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		for (int i = 0; i < tileEntityIn.getSizeInventory(); ++i)
		{
			ItemStack stack = tileEntityIn.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				DyeItem dye = (DyeItem) stack.getItem();
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 0.0625D * (i + 1), 0.0D);
				IVertexBuilder builder = bufferIn.getBuffer(CUBE.getRenderType(RESOURCELOCATION[dye.getDyeColor().getId()]));
				CUBE.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}
	}

	public static class ModelPetalLayer extends Model {

		private final ModelRenderer RENDERER;

		public ModelPetalLayer(int width, int height)
		{
			super(RenderType::getEntityCutoutNoCull);
			this.textureWidth = 16;
			this.textureHeight = 16;
			this.RENDERER = new ModelRenderer(this);
			RENDERER.addBox((16 - width) / 2, 0, (16 - width) / 2, width, height, width);
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
		{
			RENDERER.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		}
	}
}
