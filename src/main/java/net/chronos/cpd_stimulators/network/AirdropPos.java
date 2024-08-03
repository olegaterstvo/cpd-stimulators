package net.chronos.cpd_stimulators.network;

import io.netty.buffer.ByteBuf;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AirdropPos(int x, int y, int z) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AirdropPos> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "airdrop_pos"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, AirdropPos> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            AirdropPos::x,
            ByteBufCodecs.INT,
            AirdropPos::y,
            ByteBufCodecs.INT,
            AirdropPos::z,
            AirdropPos::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
