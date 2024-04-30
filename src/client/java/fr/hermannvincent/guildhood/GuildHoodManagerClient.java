package fr.hermannvincent.guildhood;

import fr.hermannvincent.guildhood.guild.Guild;
import fr.hermannvincent.guildhood.server.GuildHoodPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;

public class GuildHoodManagerClient {

    public Guild currentPlayerGuild = null;

    public void joinGuildHood(String guild_id) {
        //ClientPlayNetworking.send();
    }

    public void requestCreateGuild(String guild_name) {
        NbtCompound payload = new NbtCompound();
        payload.putString("type", "request");
        payload.putString("name", guild_name);
        ClientPlayNetworking.send(new GuildHoodPackets.RequestCreateGuildPayload(payload));
    }
}
