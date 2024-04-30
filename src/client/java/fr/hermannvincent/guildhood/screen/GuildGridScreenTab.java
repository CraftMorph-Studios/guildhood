package fr.hermannvincent.guildhood.screen;

import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class GuildGridScreenTab extends GridScreenTab {
    public GridWidget grid = new GridWidget();

    public GuildGridScreenTab(Text title) {
        super(title);
    }
}
