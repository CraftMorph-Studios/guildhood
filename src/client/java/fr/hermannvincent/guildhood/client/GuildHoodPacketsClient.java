package fr.hermannvincent.guildhood.client;

import fr.hermannvincent.guildhood.GuildHoodClient;
import fr.hermannvincent.guildhood.guild.Guild;
import fr.hermannvincent.guildhood.screen.GuildRegistryScreen;
import fr.hermannvincent.guildhood.server.GuildHoodPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class GuildHoodPacketsClient {
    public static void register() {
        PayloadTypeRegistry.playS2C().register(GuildHoodPackets.RequestCreateGuildPayload.ID, GuildHoodPackets.RequestCreateGuildPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(GuildHoodPackets.RequestCreateGuildPayload.ID, (payload, context) -> {
            GuildHoodClient.guildHoodManagerClient.currentPlayerGuild = Guild.fromNbt(payload.payload().getCompound("guild"));
            Screen currentScreen = MinecraftClient.getInstance().currentScreen;
            if (currentScreen instanceof GuildRegistryScreen) {
                ((GuildRegistryScreen) currentScreen).tabNavigation.init();
                ((GuildRegistryScreen) currentScreen).tabNavigation.selectTab(1, true);
            }
        });
    }
}
