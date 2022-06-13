package com.github.nyuppo;

import net.minecraft.client.MinecraftClient;

import java.util.Map;

public interface Clicker {
    void swap(MinecraftClient client, int from, int to);

    void swapMulti(MinecraftClient client, Map<Integer, Integer> clicks);
}
