package com.github.nyuppo.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "hotbarcycle")
public class HotbarCycleConfig implements ConfigData {
    public boolean playSound = true;
    public boolean reverseCycleDirection = false;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean holdAndScroll = false;

    @ConfigEntry.Category("rows")
    @ConfigEntry.Gui.Tooltip()
    public boolean enableRow1 = true;
    @ConfigEntry.Category("rows")
    public boolean enableRow2 = true;
    @ConfigEntry.Category("rows")
    @ConfigEntry.Gui.Tooltip()
    public boolean enableRow3 = true;

    @ConfigEntry.Category("columns")
    @ConfigEntry.Gui.Tooltip()
    public boolean enableColumn0 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn1 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn2 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn3 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn4 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn5 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn6 = true;
    @ConfigEntry.Category("columns")
    public boolean enableColumn7 = true;
    @ConfigEntry.Category("columns")
    @ConfigEntry.Gui.Tooltip()
    public boolean enableColumn8 = true;
}
