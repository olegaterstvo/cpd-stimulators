package net.chronos.cpd_stimulators.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Scanner;

public class VersionEvents {

    private static void compareVersions(ModContainer modContainer, LocalPlayer player, String url) throws IOException {
//        String mcVersion = ModList.get().getModContainerById("minecraft").get().getModInfo().getVersion().toString();

        URLConnection connection;
        connection = URI.create(url).toURL().openConnection();

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            String response = scanner.useDelimiter("\\A").next();
            JsonObject jo = (JsonObject) JsonParser.parseString(response);

            if(jo.get("error").isJsonNull()){
                if(!Objects.equals(jo.get("latest_release").getAsString(), (CPDStimulators.MOD_ID + "-" + modContainer.getModInfo().getVersion()))){
                    Component dlLink = Component.translatable("misc.cpd_stimulators.update_available")
                            .append(Component.literal("[GitHub]")
                                    .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/olegaterstvo/cpd-stimulators/releases")).withColor(ChatFormatting.BLUE)))
                            .append(Component.literal(" !"));

                    player.sendSystemMessage(dlLink);
                }
            }
        }
    }
    public static void init(ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingIn.class, event -> {
            String url = "https://musicchmonya1.onrender.com/releases?mod_id=" + CPDStimulators.MOD_ID;
            String url2 = "https://musicchmonya.onrender.com/releases?mod_id=" + CPDStimulators.MOD_ID;
            try {
                compareVersions(modContainer, event.getPlayer(), url);
            } catch (IOException e) {
                try{
                    compareVersions(modContainer, event.getPlayer(), url2);
                }catch (IOException e2){
                    CPDStimulators.LOGGER.error(e2+"");
                }
            }
        });
    }
}
