package com.github.nyuppo.compat;

import net.minecraft.client.Minecraft;
import org.anti_ad.mc.ipn.api.access.IPN;

public class IPNClicker implements Clicker {
    @Override
    public void swap(Minecraft client, int from, int to) {
        IPN.getInstance().getContainerClicker().swap(from, to);
    }
}
