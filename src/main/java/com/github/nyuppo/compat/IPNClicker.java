package com.github.nyuppo.compat;

import net.minecraft.client.MinecraftClient;
import org.anti_ad.mc.ipn.api.access.IPN;

import java.util.Map;

public class IPNClicker implements Clicker {
    @Override
    public void swap(MinecraftClient client, int from, int to) {
        IPN.getInstance().getContainerClicker().swap(from, to);
    }
}
