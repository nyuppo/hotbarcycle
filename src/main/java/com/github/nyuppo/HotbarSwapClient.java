package com.github.nyuppo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class HotbarSwapClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarswap.swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.hotbarswap.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                this.shiftRows(client.player);
            }
        });
    }

    public void shiftRows(PlayerEntity player) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager == null) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, 1 * 9 + i, i, SlotActionType.SWAP, player);
        }

        for (int i = 0; i < 9; i++) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, 2 * 9 + i, i, SlotActionType.SWAP, player);
        }

        for (int i = 0; i < 9; i++) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, 3 * 9 + i, i, SlotActionType.SWAP, player);
        }

        player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);

    }

}
