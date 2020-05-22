package com.hosta.Floricraft3.block;

import java.util.function.Function;
import java.util.function.Supplier;

import com.hosta.Flora.block.BlockEntityBaseHorizontal;
import com.hosta.Flora.block.IRenderTileEntity;
import com.hosta.Floricraft3.client.render.tileentity.TileEntityPotPourriRenderer;
import com.hosta.Floricraft3.module.ModuleCore;
import com.hosta.Floricraft3.tileentity.TileEntityPotPourri;

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

public class BlockPotPourri extends BlockEntityBaseHorizontal implements IRenderTileEntity {

	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);

	public BlockPotPourri(Properties property, Supplier<TileEntity> supplier)
	{
		super(property, supplier);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityPotPourri)
			{
				((TileEntityPotPourri) tileentity).onActivated(player, handIn);
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
		return RenderType.getTranslucent();
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public TileEntityType<TileEntityPotPourri> getTileEntityType()
	{
		return ModuleCore.typePotPourri;
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public Function<TileEntityRendererDispatcher, TileEntityPotPourriRenderer> getRenderer()
	{
		return TileEntityPotPourriRenderer::new;
	}
}
