package com.github.nyuppo.mixin;

import com.github.nyuppo.HotbarCycleClient;
import com.github.nyuppo.HotbarCycleClient.Direction;
import com.github.nyuppo.config.HotbarCycleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class RepeatClickCycleMixin {
    @Final
    @Mutable
    @Shadow
    public GameOptions options;

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void repeatClickCycleMixin(CallbackInfo ci) {
        if (HotbarCycleClient.getConfig().getRepeatSlotToCycle() && this.options.hotbarKeys[this.player.getInventory().selectedSlot].wasPressed()) {
            HotbarCycleClient.shiftSingle(((MinecraftClient)(Object)this), this.player.getInventory().selectedSlot, HotbarCycleClient.Direction.DOWN);
        }
    }

    @Redirect(method="doItemPick", at=@At(value="INVOKE", target="net/minecraft/entity/player/PlayerInventory.getSlotWithStack (Lnet/minecraft/item/ItemStack;)I"))
    private int cyclePickedItem(PlayerInventory inventory, ItemStack pickedItem) {
        final HotbarCycleConfig config = HotbarCycleClient.getConfig();
        int slot = inventory.getSlotWithStack(pickedItem);
        int x, y;

        if (0<slot && config.getCycleWhenPickingBlock() && config.isColumnEnabled(x=slot%9) && config.isRowEnabled(y=slot/9))
        {
            final MinecraftClient client = (MinecraftClient)(Object)this;
            final Direction direction = Direction.UP.reverse(config.getReverseCycleDirection());
            Runnable shiftOp;
            if (config.getPickCyclesWholeHotbar())
                shiftOp = ()->HotbarCycleClient.shiftRows(client, direction);
            else
                shiftOp = ()->HotbarCycleClient.shiftSingle(client, x, direction);

            for (int i=y; 0<i; --i)
                shiftOp.run();

            slot = x;
        }

        return slot;
    }
}
