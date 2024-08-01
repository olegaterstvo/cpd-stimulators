package net.chronos.cpd_stimulators.event;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.chronos.cpd_stimulators.block.ModBlocks;
import net.chronos.cpd_stimulators.config.ModServerConfigs;
import net.chronos.cpd_stimulators.item.ModItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ServerStartEventHandler {
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	public static BlockPos blockPos = null;

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        scheduleTask(event);
    }

    private static void scheduleTask(ServerStartedEvent event) {
        long currentTime = System.currentTimeMillis();
        long nextExecutionTime = ((currentTime / ((long) ModServerConfigs.AIRDROP_EVENT_EVERY_X_MINUTES.get() * 60 * 1000)) + 1) * ((long) ModServerConfigs.AIRDROP_EVENT_EVERY_X_MINUTES.get() * 60 * 1000);
        long delay = nextExecutionTime - currentTime;

        scheduler.schedule(() -> {
            executeTask(event.getServer());
            scheduleTask(event);
        }, delay, TimeUnit.MILLISECONDS);

		if (ModServerConfigs.AIRDROP_ANNOUNCEMENT.get()) {
			long announcementDelay = delay - (long) ModServerConfigs.AIRDROP_ANNOUNCEMENT_X_MINUTES_BEFORE.get() * 60 * 1000;
			if (announcementDelay > 0) {
				scheduler.schedule(() -> {
					PlayerChatMessage chatMessage = PlayerChatMessage.system(Component.translatable("misc.cpd_stimulators.airdrop_soon").getString());
					event.getServer().getPlayerList().broadcastChatMessage(chatMessage, event.getServer().createCommandSourceStack(), ChatType.bind(ChatType.CHAT, event.getServer().createCommandSourceStack()));
				}, announcementDelay, TimeUnit.MILLISECONDS);
			}
		}
    }

	public static void executeTask(MinecraftServer server) {
		ServerLevel world = server.overworld();
		
		List<ServerPlayer> players = server.getPlayerList().getPlayers();
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

		int centerX = sumX / players.size() + Mth.nextInt(RandomSource.create(), 1, ModServerConfigs.AIRDROP_WITHIN_RADIUS_OF_POLYGON.get());
		int centerZ = sumZ / players.size() + Mth.nextInt(RandomSource.create(), 1, ModServerConfigs.AIRDROP_WITHIN_RADIUS_OF_POLYGON.get());

		int px = centerX + Mth.nextInt(RandomSource.create(), 1, ModServerConfigs.AIRDROP_WITHIN_RADIUS_OF_POINT.get());
		int pz = centerZ + Mth.nextInt(RandomSource.create(), 1, ModServerConfigs.AIRDROP_WITHIN_RADIUS_OF_POINT.get());

		// PRELOAD CHUNK
		server.overworld().setChunkForced((int) (px / 16), (int) (pz / 16), true);

		int py = world.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, px, pz);
		BlockPos pos = BlockPos.containing(Double.valueOf(px), Double.valueOf(py), Double.valueOf(pz));
		world.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
		blockPos = pos;

		CPDStimulators.LOGGER.info("AIRDROP at " + pos);

		BlockState blockState = world.getBlockState(pos);
		if (blockState.is(Blocks.CHEST)) {
			ChestBlockEntity chestBlockEntity = new ChestBlockEntity(pos, blockState);
			int c = 0;

			for (int i = 0; i < ModItems.ITEMS.getEntries().stream().toList().size(); i++) {
				DeferredHolder<Item, ?> item = ModItems.ITEMS.getEntries().stream().toList().get(i);
				if (item.get().equals(ModBlocks.CENTRIFUGE.asItem())) continue;

				double random = Math.random();
				double changeFromRarity =
					new ItemStack(item).getRarity().equals(Rarity.COMMON) ? .5d :
					new ItemStack(item).getRarity().equals(Rarity.UNCOMMON) ? .35d :
					new ItemStack(item).getRarity().equals(Rarity.RARE) ? .2d :
					new ItemStack(item).getRarity().equals(Rarity.EPIC) ? .1d : 0d;

				if (random < changeFromRarity) {
					int t2 = new Random().nextInt(1, ModItems.ADRENALINE_INJECTOR.get().getDefaultMaxStackSize());
					chestBlockEntity.setItem(c, new ItemStack(item, t2));
					c++;
				}
			}

//			int index = new Random().nextInt(0, ModItems.ITEMS.getEntries().stream().toList().size());
//			chestBlockEntity.setItem(0, new ItemStack(ModItems.ITEMS.getEntries().stream().toList().get(index), 4));
			world.setBlockEntity(chestBlockEntity);
		}

		if (!world.isClientSide() && world.getServer() != null) {
//			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(px + " " + py + " " + pz), false);
			Component clickableCoords = Component.translatable("misc.cpd_stimulators.airdrop")
				.append(Component.literal(" " + "[" + centerX + ", " + centerZ + "]")
				.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ())).withColor(ChatFormatting.GREEN)))
				.append(Component.literal(" (?)")
				.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("misc.cpd_stimulators.airdrop_radius", ModServerConfigs.AIRDROP_WITHIN_RADIUS_OF_POINT.get()))).withColor(ChatFormatting.BOLD)));

			server.getPlayerList().broadcastSystemMessage(clickableCoords, false);
		}
	}
}