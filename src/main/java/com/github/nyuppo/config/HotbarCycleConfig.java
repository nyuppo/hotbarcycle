package com.github.nyuppo.config;

public abstract class HotbarCycleConfig {
    public abstract boolean getPlaySound();
    public abstract boolean getReverseCycleDirection();
    public abstract boolean getHoldAndScroll();
    public abstract boolean getRepeatSlotToCycle();
    public abstract boolean getCycleWhenPickingBlock();
    public abstract boolean getPickCyclesWholeHotbar();

    public abstract boolean getEnableRow1();
    public abstract boolean getEnableRow2();
    public abstract boolean getEnableRow3();

    public abstract boolean getEnableColumn0();
    public abstract boolean getEnableColumn1();
    public abstract boolean getEnableColumn2();
    public abstract boolean getEnableColumn3();
    public abstract boolean getEnableColumn4();
    public abstract boolean getEnableColumn5();
    public abstract boolean getEnableColumn6();
    public abstract boolean getEnableColumn7();
    public abstract boolean getEnableColumn8();

    public boolean isRowEnabled(int y){
        switch (y){
            default: return false;
            case 1: return getEnableRow1();
            case 2: return getEnableRow2();
            case 3: return getEnableRow3();
        }
    }

    public boolean isColumnEnabled(int x){
        switch (x){
            default: return false;
            case 1: return getEnableColumn1();
            case 2: return getEnableColumn2();
            case 3: return getEnableColumn3();
            case 4: return getEnableColumn4();
            case 5: return getEnableColumn5();
            case 6: return getEnableColumn6();
            case 7: return getEnableColumn7();
            case 8: return getEnableColumn8();
        }
    }
}
