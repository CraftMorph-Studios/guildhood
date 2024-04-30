package fr.hermannvincent.guildhood.screen;

import fr.hermannvincent.guildhood.GuildHood;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Screens {

    public static final ScreenHandlerType<GHContractScreenHandler> GHCONTRACT_SCREEN_HANDLER = new ScreenHandlerType<>(GHContractScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    public static final ScreenHandlerType<GuildRegistryScreenHandler> GHREGISTRY_SCREEN_HANDLER = new ScreenHandlerType<>(GuildRegistryScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, GuildHood.id("guild_contract_screen"), GHCONTRACT_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, GuildHood.id("guild_registry_screen"), GHREGISTRY_SCREEN_HANDLER);
    }
}
