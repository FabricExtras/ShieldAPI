package net.fabric_extras.shield_api.mixin.item;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * `Item.canRepair` was removed in 1.21.2 (repairing is driven by the `minecraft:repairable` component).
 * Custom shields keep accepting the repair ingredient supplied to their constructor on top of that,
 * so that the ingredient can stay lazily resolved (cross-mod items, config driven).
 */
@Mixin(ItemStack.class)
public class ItemStackRepairMixin {
    @Inject(method = "canRepairWith", at = @At("HEAD"), cancellable = true)
    private void customRepairIngredient_ShieldAPI(ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if (((ItemStack) (Object) this).getItem() instanceof CustomShieldItem shield) {
            if (shield.getRepairIngredientSupplier().get().test(ingredient)) {
                cir.setReturnValue(true);
            }
        }
    }
}
