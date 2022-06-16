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
        int i = (int)Math.signum(scrollAmount);

        if (HotbarCycleClient.getConfig().holdAndScroll && HotbarCycleClient.getCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftRows(MinecraftClient.getInstance(), i < 0);
            ci.cancel();
        } else if (HotbarCycleClient.getConfig().holdAndScroll && HotbarCycleClient.getSingleCycleKeyBinding().isPressed()) {
            HotbarCycleClient.shiftSingle(MinecraftClient.getInstance(), ((PlayerInventory)(Object)this).selectedSlot, i < 0);
            ci.cancel();
        }
    }
}
