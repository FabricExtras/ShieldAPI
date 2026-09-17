package net.fabric_extras.shield_api.mixin.entity.mob;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mob-with-an-axe disabling a blocking player's shield.
 *
 * <p>{@code MobEntity#disablePlayerShield} exists only on 1.20.1 (1.21.1 routes mob attacks through
 * {@code PlayerEntity#disableShield}) and hardcodes {@code Items.SHIELD} twice -- for the eligibility
 * check and for the cooldown. Forge 47 does not patch this method, so the same two redirects serve
 * both loaders. Vanilla's own probability roll is left untouched.
 */
@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

	@Redirect(
			method = "disablePlayerShield",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
			)
	)
	private boolean shield_api$isShield(ItemStack instance, Item item) {
		return instance.isOf(item) || instance.getItem() instanceof CustomShieldItem;
	}

	@Redirect(
			method = "disablePlayerShield",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"
			)
	)
	private void shield_api$disablePlayerShield(ItemCooldownManager manager, Item item, int duration) {
		manager.set(item, duration);
		for (CustomShieldItem customShieldItem : CustomShieldItem.instances) {
			manager.set(customShieldItem, duration);
		}
	}
}
