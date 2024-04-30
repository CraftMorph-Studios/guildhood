package fr.hermannvincent.guildhood;

import fr.hermannvincent.guildhood.block.Blocks;
import fr.hermannvincent.guildhood.item.Items;
import fr.hermannvincent.guildhood.screen.Screens;
import fr.hermannvincent.guildhood.server.GuildHoodPackets;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildHood implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("guildhood");

	public static final String ID = "guildhood";


	public static Identifier id(String path)
	{
		return new Identifier(ID, path);
	}

	@Override
	public void onInitialize() {
		Items.register();
		Blocks.register();
		Screens.register();

		GuildHoodPackets.register();
		GuildHoodPackets.registerServerListener();
	}
}