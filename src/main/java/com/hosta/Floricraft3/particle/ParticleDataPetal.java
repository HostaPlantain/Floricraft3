package com.hosta.Floricraft3.particle;

import com.hosta.Flora.util.ItemHelper;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleDataPetal extends ParticleDataBase<ParticleDataPetal> {

	private final Item ITEM;

	public ParticleDataPetal(ParticleType<ParticleDataPetal> type, ResourceLocation rl)
	{
		this(type, ItemHelper.getItem(rl));
	}

	public ParticleDataPetal(ParticleType<ParticleDataPetal> type, Item item)
	{
		super(type);
		this.ITEM = item;
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeString(this.ITEM.getRegistryName().toString());
	}

	@Override
	public String getParameters()
	{
		return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " " + this.ITEM.getRegistryName().toString();
	}

	public Item getItem()
	{
		return ITEM;
	}

	public static final IParticleData.IDeserializer<ParticleDataPetal> DESERIALIZER = new IParticleData.IDeserializer<ParticleDataPetal>()
	{
		@Override
		public ParticleDataPetal deserialize(ParticleType<ParticleDataPetal> particleTypeIn, StringReader reader) throws CommandSyntaxException
		{
			reader.expect(' ');
			return new ParticleDataPetal(particleTypeIn, new ResourceLocation(reader.getString()));
		}

		@Override
		public ParticleDataPetal read(ParticleType<ParticleDataPetal> particleTypeIn, PacketBuffer buffer)
		{
			return new ParticleDataPetal(particleTypeIn, new ResourceLocation(buffer.readString()));
		}
	};
}
