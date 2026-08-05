package com.github.nyuppo.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.inventory.ContainerInput;

public class VanillaClicker implements Clicker {
    @Override
    public void swap(Minecraft client, int from, int to) {
        MultiPlayerGameMode interactionManager = client.gameMode;

        if (interactionManager != null && client.player != null && client.player.getInventory() != null) {
            interactionManager.handleContainerInput(client.player.inventoryMenu.containerId, from, to, ContainerInput.SWAP, client.player);
        }
    }
}
