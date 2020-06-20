package com.hosta.Floricraft3.particle;

import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public abstract class ParticleDataBase<T extends ParticleDataBase<?>> implements IParticleData {

	private final ParticleType<T> TYPE;

	public ParticleDataBase(ParticleType<T> type)
	{
		this.TYPE = type;
	}

	@Override
	public ParticleType<T> getType()
	{
		return TYPE;
	}
}
