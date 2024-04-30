package fr.hermannvincent.guildhood.server;

import fr.hermannvincent.guildhood.GuildHoodManager;
import net.fabricmc.api.DedicatedServerModInitializer;

public class GuildHoodDedicated implements DedicatedServerModInitializer {

    public static Database database = null;

    public static GuildHoodManager guildHoodManager = null;

    @Override
    public void onInitializeServer() {
        if (database == null) {
            database = Database.init();
        }

        if (guildHoodManager == null) {
            guildHoodManager = GuildHoodManager.init();
        }
    }


}
