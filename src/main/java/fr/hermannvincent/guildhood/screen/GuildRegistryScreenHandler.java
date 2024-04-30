package fr.hermannvincent.guildhood.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;

public class GuildRegistryScreenHandler extends ScreenHandler {
    public GuildRegistryScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Screens.GHREGISTRY_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}