package fr.hermannvincent.guildhood;

import fr.hermannvincent.guildhood.client.GuildHoodPacketsClient;
import fr.hermannvincent.guildhood.screen.ContractScreen;
import fr.hermannvincent.guildhood.screen.GuildRegistryScreen;
import fr.hermannvincent.guildhood.screen.Screens;
import fr.hermannvincent.guildhood.server.GuildHoodPackets;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class GuildHoodClient implements ClientModInitializer {

	public static final GuildHoodManagerClient guildHoodManagerClient = new GuildHoodManagerClient();

	@Override
	public void onInitializeClient() {
		HandledScreens.register(Screens.GHCONTRACT_SCREEN_HANDLER, ContractScreen::new);
		HandledScreens.register(Screens.GHREGISTRY_SCREEN_HANDLER, GuildRegistryScreen::new);
		GuildHoodPacketsClient.register();
	}
}