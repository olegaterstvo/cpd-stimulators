package net.chronos.cpd_stimulators.client;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.config.ModServerConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.text.DecimalFormat;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PlayerWeightHudLayer {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "player_weight");
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");

    @SubscribeEvent
    public static void register(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, (LayeredDraw.Layer) PlayerWeightHudLayer::render);
    }

    private static void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;
        LocalPlayer player = mc.player;
        if (player == null) return;

        float w = player.getPersistentData().getFloat("current_weight");
        float max = ModServerConfigs.OVERLOAD_MAX_WEIGHT.get().floatValue();
        float sprintStop = ModServerConfigs.OVERLOAD_STOP_SPRINTING_THRESHOLD.get().floatValue();

        int color = colorForWeight(w, max, sprintStop);

        String label = Component.translatable("misc.cpd_stimulators.weight").getString();
        String cu = Component.translatable("misc.cpd_stimulators.cu").getString();
        String text = "⭐ " + label + ": " + FORMAT.format(w) + " / " + FORMAT.format(max) + " " + cu;

        Font font = mc.font;
        int x = graphics.guiWidth() - font.width(text) - 4;
        int y = 4;
        graphics.drawString(font, text, x, y, color, true);
    }

    private static int colorForWeight(float w, float max, float sprintStop) {
        int gray = 0xAAAAAA;
        int green = 0x55FF55;
        int yellow = 0xFFFF55;
        int orange = 0xFFAA00;
        int red = 0xFF5555;
        int darkRed = 0xAA0000;

        float overloadStart = 2000f;
        if (w < overloadStart) {
            float t = w / overloadStart;
            return lerp(gray, green, t);
        }
        if (w < sprintStop) {
            float t = (w - overloadStart) / Math.max(1f, sprintStop - overloadStart);
            return lerp(green, yellow, t);
        }
        if (w < max) {
            float t = (w - sprintStop) / Math.max(1f, max - sprintStop);
            return t < 0.5f ? lerp(yellow, orange, t * 2f) : lerp(orange, red, (t - 0.5f) * 2f);
        }
        return darkRed;
    }

    private static int lerp(int a, int b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int ar = (a >> 16) & 0xFF, ag = (a >> 8) & 0xFF, ab = a & 0xFF;
        int br = (b >> 16) & 0xFF, bg = (b >> 8) & 0xFF, bb = b & 0xFF;
        int r = (int) (ar + (br - ar) * t);
        int g = (int) (ag + (bg - ag) * t);
        int bl = (int) (ab + (bb - ab) * t);
        return (r << 16) | (g << 8) | bl;
    }
}
