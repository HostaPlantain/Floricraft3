package com.hosta.Floricraft3.block;

import java.util.function.Function;
import java.util.function.Supplier;

import com.hosta.Flora.block.BlockEntityBase;
import com.hosta.Flora.block.IRenderTileEntity;
import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;
import com.hosta.Flora.tileentity.TileEntityBaseInventory;
import com.hosta.Floricraft3.client.render.tileentity.TileEntityFlowerPotRenderer;
import com.hosta.Floricraft3.module.ModuleOrnamental;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerPot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEntityFlowerPot extends BlockEntityBase implements IRenderTileEntity {

	private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

	public BlockEntityFlowerPot(Properties property, Supplier<TileEntity> supplier)
	{
		super(property, supplier);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityFlowerPot)
			{
				((TileEntityBaseInventory) tileentity).putHoldItemIn(player, handIn, 0);
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public RenderType getRenderType()
	{
		return RenderType.getSolid();
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public <T extends TileEntity> TileEntityType<T> getTileEntityType()
	{
		return (TileEntityType<T>) ModuleOrnamental.typeFlowerPot;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <T extends TileEntity> Function<TileEntityRendererDispatcher, ? extends TileEntityBaseRenderer<T>> getRenderer()
	{
		return TileEntityFlowerPotRenderer::new;
	}
}
