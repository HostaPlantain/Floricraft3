package com.hosta.Floricraft3.client.particle;

import java.util.stream.Stream;

import com.hosta.Flora.client.particle.ParticleBaseSpriteTextured;
import com.hosta.Flora.util.WindHelper;
import com.hosta.Flora.util.WindHelper.Wind;
import com.hosta.Floricraft3.particle.ParticleDataPetal;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticlePetal extends ParticleBaseSpriteTextured {

	protected int trueAge = 0;

	public ParticlePetal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, TextureAtlasSprite sprite)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.setSprite(sprite);
		this.setMaxAge(1024);
		this.particleGravity = 0.07f;
		this.particleScale /= 2.0F;
	}

	@Override
	public void tick()
	{
		super.tick();
		Wind wind = WindHelper.getWind(this.world);
		BlockState blcokIn = this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
		if (blcokIn.getMaterial().isLiquid())
		{
			moveOnLiquid(wind, blcokIn);
		}
		else if (this.onGround)
		{
			moveOnGround(wind);
		}
		else
		{
			moveOnAir(wind);
		}
	}

	protected void moveOnAir(Wind wind)
	{
		++this.trueAge;
		if (this.trueAge < 10)
		{
			this.motionX *= 0.9D;
			this.motionY *= 0.9D;
			this.motionZ *= 0.9D;
		}
		else if (this.trueAge % 20 == 10)
		{
			float value = 2 * (float) Math.PI * rand.nextFloat();
			this.motionX = MathHelper.cos(value) * 0.01D;
			this.motionY = -(double) this.particleGravity;
			this.motionZ = MathHelper.sin(value) * 0.01D;
		}
		else
		{
			this.motionX *= 1.1D;
			this.motionY += 0.05D * (double) this.particleGravity;
			this.motionZ *= 1.1D;
		}

		this.move(this.motionX + wind.getWindNorth(), this.motionY + wind.getWindUp(), this.motionZ + wind.getWindWest());
	}

	protected void moveOnGround(Wind wind)
	{
		this.motionX *= 0.5D;
		this.motionY = -(double) this.particleGravity;
		this.motionZ *= 0.5D;
		--this.maxAge;

		if (wind.getWindUp() <= 0.0D)
		{
			this.move(this.motionX, this.motionY, this.motionZ);
		}
		else
		{
			this.move(this.motionX + wind.getWindNorth(), this.motionY + wind.getWindUp(), this.motionZ + wind.getWindWest());
		}
	}

	protected void moveOnLiquid(Wind wind, BlockState blcokIn)
	{
		double waterLevel = (double) MathHelper.floor(this.posY) + blcokIn.getFluidState().getHeight();
		boolean isAbove = waterLevel + 0.1D < this.posY;
		boolean isBelow = waterLevel > this.posY || this.world.getBlockState(new BlockPos(this.posX, this.posY + 1, this.posZ)).getMaterial().isLiquid();

		double x = wind.getWindNorth() * (isBelow ? 0.0D : 0.1D);
		double z = wind.getWindWest() * (isBelow ? 0.0D : 0.1D);

		this.motionX = 0.0D;
		this.motionY = isAbove || isBelow ? (motionY < 0.0D ? motionY : -(double) this.particleGravity * 0.05D) : 0.0D;
		this.motionZ = 0.0D;

		this.move(this.motionX + x, this.motionY, this.motionZ + z);
	}

	@Override
	public void move(double x, double y, double z)
	{
		if (this.canCollide && (x != 0.0D || y != 0.0D || z != 0.0D))
		{
			Vec3d vec3d = Entity.collideBoundingBoxHeuristically((Entity) null, new Vec3d(x, y, z), this.getBoundingBox(), this.world, ISelectionContext.dummy(), new ReuseableStream<>(Stream.empty()));
			x = vec3d.x;
			y = vec3d.y;
			z = vec3d.z;
		}

		if (x != 0.0D || y != 0.0D || z != 0.0D)
		{
			this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
			this.resetPositionToBB();
		}
	}

	@Override
	public IParticleRenderType getRenderType()
	{
		return IParticleRenderType.TERRAIN_SHEET;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<ParticleDataPetal> {

		private static final ItemModelMesher MESHER = Minecraft.getInstance().getItemRenderer().getItemModelMesher();

		@Override
		public Particle makeParticle(ParticleDataPetal typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
		{
			TextureAtlasSprite sprite = MESHER.getParticleIcon(typeIn.getItem());
			return new ParticlePetal(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
		}
	}
}
