package fr.hermannvincent.guildhood.server;

import fr.hermannvincent.guildhood.GuildHood;
import fr.hermannvincent.guildhood.GuildHoodManager;
import fr.hermannvincent.guildhood.guild.Guild;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class GuildHoodPackets {
    public static final Identifier CSP_SIGN_CONTRACT_PACKET_ID = GuildHood.id("sign_contract_packet");

    public static record SignContractPayload(String guild_id) implements CustomPayload {
        public static final Id<SignContractPayload> ID = CustomPayload.id(CSP_SIGN_CONTRACT_PACKET_ID.toString());
        public static final PacketCodec<PacketByteBuf, SignContractPayload> CODEC = PacketCodec.tuple(PacketCodecs.STRING, SignContractPayload::guild_id, SignContractPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public static final Identifier CSP_REQUEST_CREATE_GUILD_PACKET_ID = GuildHood.id("request_create_guild_packet");

    public static record RequestCreateGuildPayload(NbtCompound payload) implements CustomPayload {
        public static final Id<RequestCreateGuildPayload> ID = CustomPayload.id(CSP_REQUEST_CREATE_GUILD_PACKET_ID.toString());
        public static final PacketCodec<PacketByteBuf, RequestCreateGuildPayload> CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND, RequestCreateGuildPayload::payload, RequestCreateGuildPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public static void registerServerListener() {
        ServerPlayNetworking.registerGlobalReceiver(SignContractPayload.ID, (payload, context) -> {
            UUID playerUuid = context.player().getUuid();
            GuildHoodManager guildHoodManager = new GuildHoodManager();
            //guildHoodManager.playerJoinGuild(playerUuid, payload.guild_id);
        });

        ServerPlayNetworking.registerGlobalReceiver(RequestCreateGuildPayload.ID, (payload, context) -> {
            UUID playerUuid = context.player().getUuid();
            GuildHoodManager guildHoodManager = new GuildHoodManager();
            Guild guild = guildHoodManager.createGuildForUser(playerUuid, payload.payload().getString("name"));
            if (guild != null) {
                NbtCompound response = new NbtCompound();
                response.putString("type", "response");
                response.put("guild", guild.toNbt());
                ServerPlayNetworking.send(context.player(), new RequestCreateGuildPayload(response));
            }
        });
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(GuildHoodPackets.SignContractPayload.ID, GuildHoodPackets.SignContractPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(GuildHoodPackets.RequestCreateGuildPayload.ID, GuildHoodPackets.RequestCreateGuildPayload.CODEC);

    }
}
