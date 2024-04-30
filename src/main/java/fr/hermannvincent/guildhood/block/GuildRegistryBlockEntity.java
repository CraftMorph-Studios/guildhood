package fr.hermannvincent.guildhood.block;

import fr.hermannvincent.guildhood.screen.GuildRegistryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GuildRegistryBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    public GuildRegistryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Blocks.GUILD_REGISTRY_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GuildRegistryScreenHandler(syncId, playerInventory);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("test");
    }

}