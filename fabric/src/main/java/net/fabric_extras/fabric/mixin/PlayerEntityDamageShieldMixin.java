package net.fabric_extras.fabric.mixin;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Durability damage when a custom shield blocks a hit -- <b>Fabric only</b>.
 *
 * <p>Vanilla 1.20.1 gates the whole body of {@code PlayerEntity#damageShield(float)} behind
 * {@code this.activeItemStack.isOf(Items.SHIELD)}. Redirecting that single check lets vanilla run its
 * own body (use-statistic, {@code damage(int, T, Consumer<T>)} with the tool-break callback, slot clear,
 * break sound) for custom shields too, instead of duplicating it as the legacy branch did.
 *
 * <p>This mixin is <b>not</b> in the common config: Forge 47 replaces that check with
 * {@code activeItemStack.canPerformAction(ToolActions.SHIELD_BLOCK)}, which {@code ShieldItem} already
 * answers {@code true} for -- so {@link CustomShieldItem} inherits correct durability handling there and
 * applying this redirect on Forge would (a) have no matching target and (b) risk double damage.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityDamageShieldMixin {

	@Redirect(
			method = "damageShield(F)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
			)
	)
	private boolean shield_api$damageShield(ItemStack instance, Item item) {
		return instance.isOf(item) || instance.getItem() instanceof CustomShieldItem;
	}
}
