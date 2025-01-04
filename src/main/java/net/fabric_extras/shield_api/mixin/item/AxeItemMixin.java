package net.fabric_extras.shield_api.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AxeItem.class)
public class AxeItemMixin {

	@WrapOperation(
			method = "shouldCancelStripAttempt(Lnet/minecraft/item/ItemUsageContext;)Z",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
	)
	private static boolean shield_api$shouldCancelStripAttempt(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof ShieldItem;
	}
}
