package fr.hermannvincent.guildhood.block;

import fr.hermannvincent.guildhood.GuildHood;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Blocks {
    public static final Block GUILD_REGISTRY = new GuildRegistryBlock(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.LECTERN));
    public static final Item GUILD_REGISTRY_ITEM = new BlockItem(GUILD_REGISTRY, new Item.Settings());
    public static final BlockEntityType<GuildRegistryBlockEntity> GUILD_REGISTRY_ENTITY = FabricBlockEntityTypeBuilder.create(GuildRegistryBlockEntity::new, GUILD_REGISTRY).build();

    public static void register() {
        Registry.register(Registries.BLOCK, GuildHood.id("guild_registry"), GUILD_REGISTRY);
        Registry.register(Registries.ITEM, GuildHood.id("guild_registry"), GUILD_REGISTRY_ITEM);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, GuildHood.id("guild_registry"), GUILD_REGISTRY_ENTITY);
    }
}
