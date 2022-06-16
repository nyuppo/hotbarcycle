package com.github.nyuppo;

import com.github.nyuppo.compat.Clicker;
import com.github.nyuppo.compat.IPNClicker;
import com.github.nyuppo.compat.VanillaClicker;
import com.github.nyuppo.config.ClothConfigHotbarCycleConfig;
import com.github.nyuppo.config.DefaultHotbarCycleConfig;
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

    private static final HotbarCycleConfig CONFIG;

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
                if (client.player != null && !CONFIG.getHoldAndScroll()) {
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
                if (client.player != null && client.player.getInventory() != null && !CONFIG.getHoldAndScroll()) {
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
        if (CONFIG.getReverseCycleDirection() ? CONFIG.getEnableRow1() : CONFIG.getEnableRow3()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!CONFIG.getReverseCycleDirection() ? 9 : 27) + i, i);
                }
            }
        }

        if (CONFIG.getEnableRow2()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, 18 + i, i);
                }
            }
        }

        if (CONFIG.getReverseCycleDirection() ? CONFIG.getEnableRow3() : CONFIG.getEnableRow1()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!CONFIG.getReverseCycleDirection() ? 27 : 9) + i, i);
                }
            }
        }

        if (CONFIG.getPlaySound()) {
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
        if (reverseBypass ? CONFIG.getEnableRow1() : CONFIG.getEnableRow3()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!reverseBypass ? 9 : 27) + i, i);
                }
            }
        }

        if (CONFIG.getEnableRow2()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, 18 + i, i);
                }
            }
        }

        if (reverseBypass ? CONFIG.getEnableRow3() : CONFIG.getEnableRow1()) {
            for (i = 0; i < 9; i++) {
                if (isColumnEnabled(i)) {
                    clicker.swap(client, (!reverseBypass ? 27 : 9) + i, i);
                }
            }
        }

        if (CONFIG.getPlaySound()) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.5f);
        }
    }

    public static void shiftSingle(MinecraftClient client, int hotbarSlot) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        if (CONFIG.getReverseCycleDirection() ? CONFIG.getEnableRow1() : CONFIG.getEnableRow3()) {
            clicker.swap(client, (!CONFIG.getReverseCycleDirection() ? 9 : 27) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.getEnableRow2()) {
            clicker.swap(client, 18 + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.getReverseCycleDirection() ? CONFIG.getEnableRow3() : CONFIG.getEnableRow1()) {
            clicker.swap(client, (!CONFIG.getReverseCycleDirection() ? 27 : 9) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.getPlaySound()) {
            client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 0.5f, 1.8f);
        }
    }

    public static void shiftSingle(MinecraftClient client, int hotbarSlot, boolean reverseBypass) {
        @SuppressWarnings("resource")
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager == null || client.player == null) {
            return;
        }

        if (reverseBypass ? CONFIG.getEnableRow1() : CONFIG.getEnableRow3()) {
            clicker.swap(client, (!reverseBypass ? 9 : 27) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.getEnableRow2()) {
            clicker.swap(client, 18 + hotbarSlot, hotbarSlot);
        }

        if (reverseBypass ? CONFIG.getEnableRow3() : CONFIG.getEnableRow1()) {
            clicker.swap(client, (!reverseBypass ? 27 : 9) + hotbarSlot, hotbarSlot);
        }

        if (CONFIG.getPlaySound()) {
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
            case 0 -> CONFIG.getEnableColumn0();
            case 1 -> CONFIG.getEnableColumn1();
            case 2 -> CONFIG.getEnableColumn2();
            case 3 -> CONFIG.getEnableColumn3();
            case 4 -> CONFIG.getEnableColumn4();
            case 5 -> CONFIG.getEnableColumn5();
            case 6 -> CONFIG.getEnableColumn6();
            case 7 -> CONFIG.getEnableColumn7();
            case 8 -> CONFIG.getEnableColumn8();
            default -> false;
        };
    }

    static {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            CONFIG = AutoConfig.register(ClothConfigHotbarCycleConfig.class, GsonConfigSerializer::new).getConfig();
        } else {
            CONFIG = new DefaultHotbarCycleConfig();
        }

    }
}
