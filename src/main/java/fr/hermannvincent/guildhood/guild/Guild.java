package fr.hermannvincent.guildhood.guild;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.UUID;

public class Guild {
    public final UUID id;
    public final UUID ownerId;
    public final String name;

    public Guild(UUID id, UUID ownerId, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public NbtCompound toNbt() {
        NbtCompound guildCompound = new NbtCompound();
        guildCompound.putUuid("id", id);
        guildCompound.putUuid("ownerId", ownerId);
        guildCompound.putString("name", name);
        return guildCompound;
    }

    public static Guild fromNbt(NbtCompound guildCompound) {
        return new Guild(guildCompound.getUuid("id"), guildCompound.getUuid("ownerId"), guildCompound.getString("name"));
    }

    @Environment(EnvType.CLIENT)
    public static enum Mode {
        PVE("pve"),
        PVP("pvp"),
        HYBRID("hybrid");

        public final Text name;
        private final Text info;

        private Mode(final String name) {
            this.name = Text.translatable("createGuild.guildMode." + name);
            this.info = Text.translatable("createGuild.guildMode." + name + ".info");
        }

        public Text getInfo() {
            return this.info;
        }
    }
}
