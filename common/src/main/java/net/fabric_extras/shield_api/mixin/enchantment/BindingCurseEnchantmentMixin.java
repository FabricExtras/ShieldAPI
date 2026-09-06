package net.fabric_extras.shield_api.mixin.enchantment;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Curse of Binding must not apply to custom shields.
 *
 * <p>On 1.20.1 {@code EnchantmentTarget.WEARABLE} accepts anything implementing {@code Equipment},
 * which includes every {@code ShieldItem}; vanilla excludes shields with a hardcoded
 * {@code !stack.isOf(Items.SHIELD)} in {@code BindingCurseEnchantment#isAcceptableItem}. Forge 47 does
 * not patch that method. 1.21.1 has no such class at all (binding curse is tag-driven there), so this
 * mixin is 1.20.1-only and restores vanilla parity rather than adding behaviour.
 */
@Mixin(BindingCurseEnchantment.class)
public class BindingCurseEnchantmentMixin {

	@Redirect(
			method = "isAcceptableItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
			)
	)
	private boolean shield_api$isAcceptableItem(ItemStack instance, Item item) {
		return instance.isOf(item) || instance.getItem() instanceof CustomShieldItem;
	}
}
