package fr.hermannvincent.guildhood.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.hermannvincent.guildhood.GuildHoodClient;
import fr.hermannvincent.guildhood.guild.Guild;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GuildRegistryScreen  extends HandledScreen<ScreenHandler> {

    private Layout CREATE_LAYOUT_NO_GUILD = new Layout();
    private Layout CREATE_LAYOUT_HAVE_GUILD = new Layout();

    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    public TabNavigationWidget tabNavigation = null;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, (child) -> {
        this.remove(child);
    });
    public TextFieldWidget guildNameTextField = null;

    public final ButtonWidget joinGuildButton = ButtonWidget.builder(Text.of("Sign"), new ButtonWidget.PressAction() {
        @Override
        public void onPress(ButtonWidget button) {
            GuildHoodClient.guildHoodManagerClient.requestCreateGuild(guildNameTextField.getText());
        }
    }).build();

    public GuildRegistryScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext, mouseX, mouseY, delta);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
        if (GuildHoodClient.guildHoodManagerClient.currentPlayerGuild != null) {

        }
    }

    @Override
    protected void init() {
        super.init();
        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width).tabs(new Tab[]{new GuildRegistryScreen.CreateTab(), new GuildRegistryScreen.JoinTab()}).build();
        this.addDrawableChild(this.tabNavigation);
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int i = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - this.layout.getFooterHeight() - i);
            this.tabManager.setTabArea(screenRect);
            this.layout.setHeaderHeight(i);
            this.layout.refreshPositions();
        }
        this.tabNavigation.selectTab(0, false);
//        this.addDrawableChild(this.guildNameTextField);
//        this.addDrawableChild(this.joinGuildButton);
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean mouseClickedElement = hasClickedElement(mouseX, mouseY, button);
        if (!mouseClickedElement) {
            return super.mouseClicked(mouseX, mouseY, button);
        } else {
            return true;
        }
    }

    private boolean hasClickedElement(double mouseX, double mouseY, int button) {
        Iterator<? extends Element> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (element.mouseClicked(mouseX, mouseY, button)) {
                this.setFocused(element);
                if (button == 0) {
                    this.setDragging(true);
                }
                return true;
            } else {
                element.setFocused(false);
            }
        }

        return false;
    }


    private boolean hasElementFocused() {
        Iterator<? extends Element> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (element.isFocused()) {
                return true;
            }
        }

        return false;
    }

    private void unfocusElements() {
        Iterator<? extends Element> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (element.isFocused()) {
                element.setFocused(false);
            }
        }
        this.setFocused(null);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode) && hasElementFocused()) {
            return true;
        } else if(keyCode == 256 && hasElementFocused()) {
            unfocusElements();
            return true;
        }
        else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Environment(EnvType.CLIENT)
    private class CreateTab extends GridScreenTab {
        private static final Text CREATE_TAB_TITLE_TEXT = Text.of("Create");
        private static final Text ALLOW_COMMANDS_TEXT = Text.translatable("selectWorld.allowCommands.new");
        private final TextFieldWidget guildNameField;
        private Guild.Mode guildMode = null;

        CreateTab() {
            super(CREATE_TAB_TITLE_TEXT);
            GridWidget.Adder adder = this.grid.setRowSpacing(8).createAdder(1);
            Positioner positioner = adder.copyPositioner();
            this.guildNameField = new TextFieldWidget(GuildRegistryScreen.this.textRenderer, 208, 20, Text.translatable("createGuild.enterName"));
//            this.worldNameField.setText(CreateWorldScreen.this.worldCreator.getWorldName());
//            TextFieldWidget var10000 = this.worldNameField;
//            WorldCreator var10001 = CreateWorldScreen.this.worldCreator;
//            Objects.requireNonNull(var10001);
//            var10000.setChangedListener(var10001::setWorldName);
//            CreateWorldScreen.this.worldCreator.addListener((creator) -> {
//                this.worldNameField.setTooltip(Tooltip.of(Text.translatable("selectWorld.targetFolder", new Object[]{Text.literal(creator.getWorldDirectoryName()).formatted(Formatting.ITALIC)})));
//            });
            GuildRegistryScreen.this.setInitialFocus(this.guildNameField);
            adder.add(LayoutWidgets.createLabeledWidget(GuildRegistryScreen.this.textRenderer, this.guildNameField, Text.of("Enter guild name")), adder.copyPositioner().alignHorizontalCenter());
            CyclingButtonWidget<Guild.Mode> testButtonWidget = (CyclingButtonWidget<Guild.Mode>)adder.add(CyclingButtonWidget.builder((Guild.Mode value) -> {
                return value.name;
            }).values(new Guild.Mode[]{Guild.Mode.PVE, Guild.Mode.PVP, Guild.Mode.HYBRID}).build(0, 0, 210, 20, Text.of("Guild Mode"), (button, value) -> {
                //CreateWorldScreen.this.worldCreator.setGameMode(value);
                this.guildMode = value;
            }), positioner);
            ButtonWidget createButtonWidget = ButtonWidget.builder(Text.of("Create"), new ButtonWidget.PressAction() {
                @Override
                public void onPress(ButtonWidget button) {
                    GuildHoodClient.guildHoodManagerClient.requestCreateGuild(guildNameField.getText());
                }
            }).build();
            createButtonWidget.setWidth(210);
            adder.add(createButtonWidget);
//            CreateWorldScreen.this.worldCreator.addListener((creator) -> {
//                cyclingButtonWidget.setValue(creator.getGameMode());
//                cyclingButtonWidget.active = !creator.isDebug();
//                cyclingButtonWidget.setTooltip(Tooltip.of(creator.getGameMode().getInfo()));
//            });
//            CyclingButtonWidget<Difficulty> cyclingButtonWidget2 = (CyclingButtonWidget)adder.add(CyclingButtonWidget.builder(Difficulty::getTranslatableName).values(Difficulty.values()).build(0, 0, 210, 20, Text.translatable("options.difficulty"), (button, value) -> {
//                CreateWorldScreen.this.worldCreator.setDifficulty(value);
//            }), positioner);
//            CreateWorldScreen.this.worldCreator.addListener((creator) -> {
//                cyclingButtonWidget2.setValue(CreateWorldScreen.this.worldCreator.getDifficulty());
//                cyclingButtonWidget2.active = !CreateWorldScreen.this.worldCreator.isHardcore();
//                cyclingButtonWidget2.setTooltip(Tooltip.of(CreateWorldScreen.this.worldCreator.getDifficulty().getInfo()));
//            });
//            CyclingButtonWidget<Boolean> cyclingButtonWidget3 = (CyclingButtonWidget)adder.add(CyclingButtonWidget.onOffBuilder().tooltip((value) -> {
//                return Tooltip.of(CreateWorldScreen.ALLOW_COMMANDS_INFO_TEXT);
//            }).build(0, 0, 210, 20, ALLOW_COMMANDS_TEXT, (button, value) -> {
//                CreateWorldScreen.this.worldCreator.setCheatsEnabled(value);
//            }));
//            CreateWorldScreen.this.worldCreator.addListener((creator) -> {
//                cyclingButtonWidget3.setValue(CreateWorldScreen.this.worldCreator.areCheatsEnabled());
//                cyclingButtonWidget3.active = !CreateWorldScreen.this.worldCreator.isDebug() && !CreateWorldScreen.this.worldCreator.isHardcore();
//            });
//            if (!SharedConstants.getGameVersion().isStable()) {
//                adder.add(ButtonWidget.builder(CreateWorldScreen.EXPERIMENTS_TEXT, (button) -> {
//                    CreateWorldScreen.this.openExperimentsScreen(CreateWorldScreen.this.worldCreator.getGeneratorOptionsHolder().dataConfiguration());
//                }).width(210).build());
//            }

        }
    }

    @Environment(EnvType.CLIENT)
    private class JoinTab extends GridScreenTab {
        private static final Text JOIN_TAB_TITLE_TEXT = Text.of("Join");
        private static final Text ALLOW_COMMANDS_TEXT = Text.translatable("selectWorld.allowCommands.new");
//        private final TextFieldWidget worldNameField;

        JoinTab() {
            super(JOIN_TAB_TITLE_TEXT);
            GridWidget.Adder adder = this.grid.createAdder(1);
            Positioner positioner = adder.copyPositioner();

        }
    }
}
