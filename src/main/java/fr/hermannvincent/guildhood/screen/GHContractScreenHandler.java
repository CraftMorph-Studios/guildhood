package fr.hermannvincent.guildhood.screen;

import fr.hermannvincent.guildhood.item.GuildHoodContractItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class GHContractScreenHandler extends ScreenHandler {
    public GHContractScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Screens.GHCONTRACT_SCREEN_HANDLER, syncId);
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
