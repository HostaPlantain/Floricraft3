package com.hosta.Floricraft3.block;

import java.util.function.Function;
import java.util.function.Supplier;

import com.hosta.Flora.block.BlockEntityBaseFourWay;
import com.hosta.Flora.block.IRenderTileEntity;
import com.hosta.Floricraft3.client.render.tileentity.TileEntityFlowerBedRenderer;
import com.hosta.Floricraft3.module.ModuleOrnamental;
import com.hosta.Floricraft3.tileentity.TileEntityFlowerBed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEntityFlowerBed extends BlockEntityBaseFourWay implements IRenderTileEntity {

	private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public BlockEntityFlowerBed(Properties property, Supplier<TileEntity> supplier)
	{
		super(property, supplier);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityFlowerBed)
			{
				((TileEntityFlowerBed) tileentity).onActivated(player, handIn);
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	protected boolean canConnect(BlockState state, Direction direct)
	{
		return state.getBlock() == this;
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
	public TileEntityType<TileEntityFlowerBed> getTileEntityType()
	{
		return ModuleOrnamental.typeFlowerBed;
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public Function<TileEntityRendererDispatcher, TileEntityFlowerBedRenderer> getRenderer()
	{
		return TileEntityFlowerBedRenderer::new;
	}
}
