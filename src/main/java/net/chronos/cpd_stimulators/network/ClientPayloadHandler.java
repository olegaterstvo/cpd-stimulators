package net.chronos.cpd_stimulators.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void handleData(final AirdropPos data, final IPayloadContext context) {
        // Do something with the data, on the network thread
        int[] pos = new int[3];
        pos[0] = data.x();
        pos[1] = data.y();
        pos[2] = data.z();
        context.player().getPersistentData().putIntArray("airdrop_pos", pos);

//        // Do something with the data, on the main thread
//        context.enqueueWork(() -> {
//                    CPDStimulators.LOGGER.info(String.valueOf((data.age())));
//                })
//                .exceptionally(e -> {
//                    // Handle exception
//                    context.disconnect(Component.translatable("my_mod.networking.failed", e.getMessage()));
//                    return null;
//                });
    }
}
