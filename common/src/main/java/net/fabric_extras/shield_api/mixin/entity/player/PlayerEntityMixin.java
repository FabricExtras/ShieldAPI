package net.fabric_extras.shield_api.mixin.entity.player;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Axe-disables-shield cooldown for custom shields.
 *
 * <p>1.20.1's {@code PlayerEntity#disableShield(boolean sprinting)} is <b>probabilistic</b> -- it rolls
 * {@code 0.25 + efficiency*0.05 (+0.75 when sprinting)} and only then applies the cooldown. Vanilla applies
 * that cooldown to the hardcoded {@code Items.SHIELD}; the Forge-patched copy applies it to
 * {@code getActiveItem().getItem()}.
 *
 * <p>Redirecting the cooldown call (rather than injecting at HEAD/TAIL, as the legacy 1.0.x branch did)
 * keeps vanilla's single dice roll intact -- the legacy TAIL injection re-rolled {@code random.nextFloat()}
 * a second time, so custom shields were disabled independently of the vanilla shield. The effect matches
 * 2.2.0 on 1.21.1: every registered {@link CustomShieldItem} goes on cooldown alongside the vanilla shield.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Redirect(
			method = "disableShield(Z)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"
			)
	)
	private void shield_api$disableShield(ItemCooldownManager manager, Item item, int duration) {
		manager.set(item, duration);
		for (CustomShieldItem customShieldItem : CustomShieldItem.instances) {
			manager.set(customShieldItem, duration);
		}
	}
}
