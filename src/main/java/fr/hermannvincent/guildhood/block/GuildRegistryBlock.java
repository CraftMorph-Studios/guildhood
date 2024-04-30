package fr.hermannvincent.guildhood.block;

import com.mojang.serialization.MapCodec;
import fr.hermannvincent.guildhood.GuildHood;
import fr.hermannvincent.guildhood.screen.GHContractScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuildRegistryBlock extends Block implements BlockEntityProvider {
    public GuildRegistryBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {

            GuildRegistryBlockEntity blockEntity = (GuildRegistryBlockEntity)world.getBlockEntity(pos);
            if (blockEntity != null) {
                NamedScreenHandlerFactory namedScreenHandlerFactory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerHandler) -> blockEntity.createMenu(syncId, playerInventory, playerHandler), this.getName());

                if (namedScreenHandlerFactory != null) {
                    player.openHandledScreen(namedScreenHandlerFactory);
                }
            }

        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GuildRegistryBlockEntity(blockPos, blockState);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}