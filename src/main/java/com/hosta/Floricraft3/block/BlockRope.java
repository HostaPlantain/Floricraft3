package com.hosta.Floricraft3.block;

import java.util.function.Function;
import java.util.function.Supplier;

import com.hosta.Flora.block.BlockEntityHorizontal;
import com.hosta.Flora.block.IRenderTileEntity;
import com.hosta.Flora.block.IRenderType;
import com.hosta.Floricraft3.client.render.tileentity.TileEntityRopeRenderer;
import com.hosta.Floricraft3.module.ModuleFloricraft;
import com.hosta.Floricraft3.tileentity.TileEntityRope;

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

public class BlockRope extends BlockEntityHorizontal implements IRenderType, IRenderTileEntity {

	private static final VoxelShape[] SHAPE_ONLY_SIDE = new VoxelShape[] { Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D), Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D) };

	public BlockRope(Block.Properties property, Supplier<TileEntity> supplier)
	{
		super(property, supplier);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityRope)
			{
				((TileEntityRope) tileentity).putHoldItemIn(player, handIn, 0);
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE_ONLY_SIDE[state.get(FACING).getHorizontalIndex()];
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public RenderType getRenderType()
	{
		return RenderType.getCutout();
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public TileEntityType<TileEntityRope> getTileEntityType()
	{
		return ModuleFloricraft.typeRope;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<TileEntityRendererDispatcher, TileEntityRopeRenderer> getRenderer()
	{
		return (TileEntityRopeRenderer::new);
	}
}
