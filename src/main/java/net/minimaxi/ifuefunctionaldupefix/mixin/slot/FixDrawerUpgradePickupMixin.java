package net.minimaxi.ifuefunctionaldupefix.mixin.slot;

import com.hrznstudio.titanium.container.addon.UpdatableSlotItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UpdatableSlotItemHandler.class, priority = 2000)
public abstract class FixDrawerUpgradePickupMixin extends SlotItemHandler {

    public FixDrawerUpgradePickupMixin(IItemHandler h, int i, int x, int y) {
        super(h, i, x, y);
    }

    // both names, so it matches in dev (mayPickup) and in production (m_8010_)
    @Inject(method = {"mayPickup", "m_8010_"}, at = @At("HEAD"), cancellable = true, remap = false)
    private void fix$mayPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        IItemHandler handler = this.getItemHandler();
        if (handler.getClass().getName().startsWith("com.buuz135.functionalstorage")) {
            cir.setReturnValue(!handler.extractItem(this.getSlotIndex(), 1, true).isEmpty());
        }
    }
}