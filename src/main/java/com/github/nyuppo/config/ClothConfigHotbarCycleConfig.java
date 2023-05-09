package com.github.nyuppo.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "hotbarcycle")
public class ClothConfigHotbarCycleConfig extends HotbarCycleConfig implements ConfigData {
    public boolean playSound = true;
    public boolean reverseCycleDirection = false;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean holdAndScroll = false;
    @ConfigEntry.Gui.Tooltip()
    public boolean repeatSlotToCycle = false;

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

    @Override
    public boolean getPlaySound() {
        return playSound;
    }

    @Override
    public boolean getReverseCycleDirection() {
        return reverseCycleDirection;
    }

    @Override
    public boolean getHoldAndScroll() {
        return holdAndScroll;
    }

    @Override
    public boolean getRepeatSlotToCycle() {
        return repeatSlotToCycle;
    }

    @Override
    public boolean getEnableRow1() {
        return enableRow1;
    }

    @Override
    public boolean getEnableRow2() {
        return enableRow2;
    }

    @Override
    public boolean getEnableRow3() {
        return enableRow3;
    }

    @Override
    public boolean getEnableColumn0() {
        return enableColumn0;
    }

    @Override
    public boolean getEnableColumn1() {
        return enableColumn1;
    }

    @Override
    public boolean getEnableColumn2() {
        return enableColumn2;
    }

    @Override
    public boolean getEnableColumn3() {
        return enableColumn3;
    }

    @Override
    public boolean getEnableColumn4() {
        return enableColumn4;
    }

    @Override
    public boolean getEnableColumn5() {
        return enableColumn5;
    }

    @Override
    public boolean getEnableColumn6() {
        return enableColumn6;
    }

    @Override
    public boolean getEnableColumn7() {
        return enableColumn7;
    }

    @Override
    public boolean getEnableColumn8() {
        return enableColumn8;
    }
}
