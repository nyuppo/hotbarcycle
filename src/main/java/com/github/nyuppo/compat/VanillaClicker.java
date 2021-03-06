package com.github.nyuppo.compat;

import com.github.nyuppo.HotbarCycleClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Map;

public class VanillaClicker implements Clicker {
    @Override
    public void swap(MinecraftClient client, int from, int to) {
        ClientPlayerInteractionManager interactionManager = client.interactionManager;

        if (interactionManager != null && client.player != null && client.player.getInventory() != null) {
            interactionManager.clickSlot(client.player.playerScreenHandler.syncId, from, to, SlotActionType.SWAP, client.player);
        }
    }
}
