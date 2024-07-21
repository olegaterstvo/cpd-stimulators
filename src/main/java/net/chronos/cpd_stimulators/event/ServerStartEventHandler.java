package net.chronos.cpd_stimulators.event;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ServerStartEventHandler {
	private static final boolean ANNOUNCEMENT = false;
	private static final int ANNOUNCEMENT_X_MINUTES_BEFORE = 1;
    private static final long EVENT_EVERY_X_MINUTES = 1;
    private static final int WITHIN_RADIUS_OF_POLYGON = 200;
    private static final int WITHIN_RADIUS_OF_POINT = 50;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        scheduleTask(event);
    }

	// 
	// КАЖДУЮ МИНУТУ СПАВНИТ СУНДУК 
	// 
	// 
	// 
	// 
	// 
    private static void scheduleTask(ServerStartedEvent event) {
        long currentTime = System.currentTimeMillis();
        long nextExecutionTime = ((currentTime / (EVENT_EVERY_X_MINUTES * 60 * 1000)) + 1) * (EVENT_EVERY_X_MINUTES * 60 * 1000);
        long delay = nextExecutionTime - currentTime;

        scheduler.schedule(() -> {
            executeTask(event);
            scheduleTask(event);
        }, delay, TimeUnit.MILLISECONDS);

		if (ANNOUNCEMENT) {
			long announcementDelay = delay - ANNOUNCEMENT_X_MINUTES_BEFORE * 60 * 1000;
			if (announcementDelay > 0) {
				scheduler.schedule(() -> {
					PlayerChatMessage chatMessage = PlayerChatMessage.system("скоро сундук");
					event.getServer().getPlayerList().broadcastChatMessage(chatMessage, event.getServer().createCommandSourceStack(), ChatType.bind(ChatType.CHAT, event.getServer().createCommandSourceStack()));
				}, announcementDelay, TimeUnit.MILLISECONDS);
			}
		}
    }

	private static void executeTask(ServerStartedEvent event) {
		ServerLevel world = event.getServer().overworld();
		
		List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
		if (players.isEmpty()) {
			return;
		}

		int sumX = 0;
		int sumZ = 0;

		for (ServerPlayer player : players) {
			BlockPos pos = player.blockPosition();
			sumX += pos.getX();
			sumZ += pos.getZ();
		}

		int centerX = sumX / players.size() + Mth.nextInt(RandomSource.create(), 1, WITHIN_RADIUS_OF_POLYGON);
		int centerZ = sumZ / players.size() + Mth.nextInt(RandomSource.create(), 1, WITHIN_RADIUS_OF_POLYGON);

		int px = centerX + Mth.nextInt(RandomSource.create(), 1, WITHIN_RADIUS_OF_POINT);
		int pz = centerZ + Mth.nextInt(RandomSource.create(), 1, WITHIN_RADIUS_OF_POINT);
		int py = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, px, pz);

		BlockPos pos = BlockPos.containing(Double.valueOf(px), Double.valueOf(py), Double.valueOf(pz));
		world.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);

		// 
		// Надо как-то пофиксить! не работает! не видит сундук в координатах.
		// 
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ChestBlockEntity) {
			ChestBlockEntity chest = (ChestBlockEntity) blockEntity;
			chest.setItem(0, new ItemStack(Items.DIAMOND, 5));
		}
		// 
		// 

		if (!world.isClientSide() && world.getServer() != null) {
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(px + " " + py + " " + pz), false);
			Component clickableCoords = Component.translatable("misc.cpd_stimulators.airdrop")
				.append(Component.literal(" [" + centerX + " " + centerZ + "]")
				.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ())).withColor(ChatFormatting.GREEN)))
				.append(Component.literal(" (?)")
				.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("misc.cpd_stimulators.airdrop_radius", WITHIN_RADIUS_OF_POINT))).withColor(ChatFormatting.BOLD)));

			event.getServer().getPlayerList().broadcastSystemMessage(clickableCoords, false);
		}
	}
}