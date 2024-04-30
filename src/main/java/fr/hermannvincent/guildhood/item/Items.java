package fr.hermannvincent.guildhood.item;

import fr.hermannvincent.guildhood.GuildHood;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final Item GUILD_HOOD_CONTRACT = new GuildHoodContractItem(new Item.Settings().maxCount(1));

    public static void register() {
        Registry.register(Registries.ITEM, GuildHood.id("contract_item"), GUILD_HOOD_CONTRACT);
    }
}
