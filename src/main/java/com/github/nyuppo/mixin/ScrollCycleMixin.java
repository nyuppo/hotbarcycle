package com.github.nyuppo.mixin;

import com.github.nyuppo.HotbarCycleClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public class ScrollCycleMixin {
    @WrapOperation(method="onMouseScroll", at=@At(value="INVOKE", target="net/minecraft/client/input/Scroller.scrollCycling(DII)I" ))
    private int hotbarcycleScrollInHotbar(double scrollAmount, int selectedSlot, int hotbarSize, Operation<Integer> original) {
        final HotbarCycleClient.Direction direction = Math.signum(scrollAmount) < 0
                ? HotbarCycleClient.Direction.UP
                : HotbarCycleClient.Direction.DOWN;
        if (HotbarCycleClient.getConfig().getHoldAndScroll() && HotbarCycleClient.getCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftRows(MinecraftClient.getInstance(), direction);
            return selectedSlot;
        } else if (HotbarCycleClient.getConfig().getHoldAndScroll() && HotbarCycleClient.getSingleCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftSingle(MinecraftClient.getInstance(), selectedSlot, direction);
            return selectedSlot;
        }
        else
            return original.call(scrollAmount, selectedSlot, hotbarSize);
    }
}
