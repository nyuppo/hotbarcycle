package com.github.nyuppo.mixin;

import com.github.nyuppo.HotbarCycleClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class ScrollCycleMixin {
    @Inject(method = "onScroll(JDD)V", at = @At("HEAD"), cancellable = true)
    private void hotbarcycleOnScroll(long window, double scrollX, double scrollY, CallbackInfo ci) {
        if (!HotbarCycleClient.getConfig().getHoldAndScroll()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.screen != null || client.getOverlay() != null) {
            return;
        }
        final HotbarCycleClient.Direction direction = Math.signum(scrollY) < 0
                ? HotbarCycleClient.Direction.UP
                : HotbarCycleClient.Direction.DOWN;
        if (HotbarCycleClient.getCycleKeyBinding().isDown()) {
            HotbarCycleClient.shiftRows(client, direction);
            ci.cancel();
        } else if (HotbarCycleClient.getSingleCycleKeyBinding().isDown()) {
            HotbarCycleClient.shiftSingle(client, client.player.getInventory().getSelectedSlot(), direction);
            ci.cancel();
        }
    }
}
