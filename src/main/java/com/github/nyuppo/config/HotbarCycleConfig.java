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
    public boolean enableRow1 = true;
    @ConfigEntry.Category("rows")
    public boolean enableRow2 = true;
    @ConfigEntry.Category("rows")
    public boolean enableRow3 = true;
}
