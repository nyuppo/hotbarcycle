package com.github.nyuppo;

import com.github.nyuppo.compat.Clicker;
import com.github.nyuppo.compat.IPNClicker;
import com.github.nyuppo.compat.VanillaClicker;
import com.github.nyuppo.config.HotbarCycleConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotbarCycleClient implements ClientModInitializer {
    private static KeyBinding cycleKeyBinding;
    private static KeyBinding singleCycleKeyBinding;

    private static final HotbarCycleConfig CONFIG = AutoConfig.register(HotbarCycleConfig.class, GsonConfigSerializer::new).getConfig();

    private static Clicker clicker;

    public static final Logger LOGGER = LoggerFactory.getLogger("hotbarcycle");

    public static HotbarCycleConfig getConfig() {
        return CONFIG;
    }

    public static KeyBinding getCycleKeyBinding() {
        return cycleKeyBinding;
    }

    public static KeyBinding getSingleCycleKeyBinding() {
        return singleCycleKeyBinding;
    }

    @Override
    public void onInitializeClient() {
        clicker = getClicker();

        cycleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarcycle.cycle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.hotbarcycle.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (cycleKeyBinding.wasPressed()) {
                if (client.player != null && !CONFIG.holdAndScroll) {
                    shiftRows(client);
                }
            }
        });

        singleCycleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarcycle.single_cycle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.hotbarcycle.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (singleCycleKeyBinding.wasPressed()) {
                if (client.player != null && client.player.getInventory() != null && !CONFIG.holdAndScroll) {
                    shiftSingle(client, client.player.getInventory().selectedSlot);
                }
            }
        });
    }

    public static void shiftRows(MinecraftClient client) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        int i;
        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!CONFIG.reverseCycleDirection ? 9 : 27) + i, i);
                }
            }
        }

        if (CONFIG.enableRow2) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, 18 + i, i);
                }
            }
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!CONFIG.reverseCycleDirection ? 27 : 9) + i, i);
                }
            }
        }

        if (CONFIG.playSound) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);
        }
    }

    public static void shiftRows(MinecraftClient client, boolean reverseBypass) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        int i;
        if (reverseBypass ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!reverseBypass ? 9 : 27) + i, i);
                }
            }
        }

        if (CONFIG.enableRow2) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, 18 + i, i);
                }
            }
        }

        if (reverseBypass ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!reverseBypass ? 27 : 9) + i, i);
                }
            }
        }

        if (CONFIG.playSound) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);
        }
    }

    public static void shiftSingle(MinecraftClient client, int hotbarSlot) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            clicker.swap(client, (!CONFIG.reverseCycleDirection ? 9 : 27) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.enableRow2) {
            clicker.swap(client, 18 + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.reverseCycleDirection ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            clicker.swap(client, (!CONFIG.reverseCycleDirection ? 27 : 9) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.playSound) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.8f);
        }
    }

    public static void shiftSingle(MinecraftClient client, int hotbarSlot, boolean reverseBypass) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        if (reverseBypass ? CONFIG.enableRow1 : CONFIG.enableRow3) {
            clicker.swap(client, (!reverseBypass ? 9 : 27) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.enableRow2) {
            clicker.swap(client, 18 + hotbarSlot, hotbarSlot);
        }

        if (reverseBypass ? CONFIG.enableRow3 : CONFIG.enableRow1) {
            clicker.swap(client, (!reverseBypass ? 27 : 9) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.playSound) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.8f);
        }
    }

    private static Clicker getClicker() {
        if (FabricLoader.getInstance().isModLoaded("inventoryprofilesnext")) {
            LOGGER.info("Inventory Profiles Next was found, switching to compatible clicker!");
            return new IPNClicker();
        }

        return new VanillaClicker();
    }

    private static boolean isColumnEnabled(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> CONFIG.enableColumn0;
            case 1 -> CONFIG.enableColumn1;
            case 2 -> CONFIG.enableColumn2;
            case 3 -> CONFIG.enableColumn3;
            case 4 -> CONFIG.enableColumn4;
            case 5 -> CONFIG.enableColumn5;
            case 6 -> CONFIG.enableColumn6;
            case 7 -> CONFIG.enableColumn7;
            case 8 -> CONFIG.enableColumn8;
            default -> false;
        };
    }
}
