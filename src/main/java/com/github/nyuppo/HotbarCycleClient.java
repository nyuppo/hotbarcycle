package com.github.nyuppo;

import com.github.nyuppo.config.HotbarCycleConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
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

public class HotbarCycleClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private static KeyBinding singleKeyBinding;

    private static final HotbarCycleConfig CONFIG = AutoConfig.register(HotbarCycleConfig.class, GsonConfigSerializer::new).getConfig();

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarcycle.cycle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.hotbarcycle.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                this.shiftRows(client.player);
            }
        });

        singleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarcycle.single_cycle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.hotbarcycle.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (singleKeyBinding.wasPressed()) {
                if (client.player != null && client.player.getInventory() != null) {
                    this.shiftSingle(client.player, client.player.getInventory().selectedSlot);
                }
            }
        });
    }

    public void shiftRows(PlayerEntity player) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager == null) {
            return;
        }

        int i;
        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            for (i = 0; i < 9; i++) {
                interactionManager.clickSlot(player.playerScreenHandler.syncId, (!CONFIG.reverseCycleDirection ? 9 : 27) + i, i, SlotActionType.SWAP, player);
            }
        }

        if (CONFIG.enableRow2) {
            for (i = 0; i < 9; i++) {
                interactionManager.clickSlot(player.playerScreenHandler.syncId, 18 + i, i, SlotActionType.SWAP, player);
            }
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            for (i = 0; i < 9; i++) {
                interactionManager.clickSlot(player.playerScreenHandler.syncId, (!CONFIG.reverseCycleDirection ? 27 : 9) + i, i, SlotActionType.SWAP, player);
            }
        }

        if (CONFIG.playSound) {
            player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);
        }
    }

    public void shiftSingle(PlayerEntity player, int hotbarSlot) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager == null) {
            return;
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, (!CONFIG.reverseCycleDirection ? 9 : 27) + hotbarSlot, hotbarSlot, SlotActionType.SWAP, player);
        }

        if (CONFIG.enableRow2) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, 18 + hotbarSlot, hotbarSlot, SlotActionType.SWAP, player);
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            interactionManager.clickSlot(player.playerScreenHandler.syncId, (!CONFIG.reverseCycleDirection ? 27 : 9) + hotbarSlot, hotbarSlot, SlotActionType.SWAP, player);
        }

        if (CONFIG.playSound) {
            player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.8f);
        }
    }

}
