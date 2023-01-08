package com.github.nyuppo.mixin;

import com.github.nyuppo.HotbarCycleClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class ScrollCycleMixin {
    @Inject(method = "scrollInHotbar(D)V", at = @At("HEAD"), cancellable = true)
    private void hotbarcycleScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        final HotbarCycleClient.Direction direction = Math.signum(scrollAmount) < 0
                ? HotbarCycleClient.Direction.UP
                : HotbarCycleClient.Direction.DOWN;
        if (HotbarCycleClient.getConfig().getHoldAndScroll() && HotbarCycleClient.getCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftRows(MinecraftClient.getInstance(), direction);
            ci.cancel();
        } else if (HotbarCycleClient.getConfig().getHoldAndScroll() && HotbarCycleClient.getSingleCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftSingle(MinecraftClient.getInstance(), ((PlayerInventory)(Object)this).selectedSlot, direction);
            ci.cancel();
        }
    }
}
