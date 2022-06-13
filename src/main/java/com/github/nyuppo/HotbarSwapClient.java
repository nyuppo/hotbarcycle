package com.github.nyuppo;

import com.github.nyuppo.interoperability.IPNClicker;
import com.github.nyuppo.interoperability.VanillaClicker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class HotbarSwapClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private static KeyBinding singleKeyBinding;

    private Clicker clicker;


    @Override
    public void onInitializeClient() {
        clicker = getClicker();
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarswap.swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.hotbarswap.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                this.shiftRows(client);
            }
        });

        singleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarswap.single_swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.hotbarswap.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (singleKeyBinding.wasPressed()) {
                if (client.player != null && client.player.getInventory() != null) {
                    this.shiftSingle(client, client.player.getInventory().selectedSlot);
                }
            }
        });
    }

    public void shiftRows(MinecraftClient client) {
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        int i;
        for (i = 0; i < 9; i++) {
            clicker.swap(client, 9 + i, i);
        }

        for (i = 0; i < 9; i++) {
            clicker.swap(client,18 + i, i);
        }

        for (i = 0; i < 9; i++) {
            clicker.swap(client, 27 + i, i);
        }
        client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);
    }

    public void shiftSingle(MinecraftClient client, int hotbarSlot) {
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }
        clicker.swap(client, 9+hotbarSlot, hotbarSlot);
        clicker.swap(client, 18+hotbarSlot, hotbarSlot);
        clicker.swap(client, 27+hotbarSlot, hotbarSlot);

        client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.8f);
    }


    private Clicker getClicker() {
        try {
            Class.forName("org.anti_ad.mc.ipn.api.access.IPN", false, this.getClass().getClassLoader());
            return new IPNClicker();
        } catch (Throwable tw) {
          tw.printStackTrace();
        }
        return new VanillaClicker();
    }

}
