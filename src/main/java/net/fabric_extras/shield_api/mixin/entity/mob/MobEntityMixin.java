package net.fabric_extras.shield_api.mixin.entity.mob;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "disablePlayerShield", at = @At("TAIL"))
	private void shield_api$disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack, CallbackInfo ci) {
		if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem && playerStack.getItem() instanceof CustomShieldItem playerStackItem) {
			float f = 0.25F + (float) EnchantmentHelper.getEfficiency(this) * 0.05F;
			if (this.random.nextFloat() < f) {
				player.getItemCooldownManager().set(playerStackItem, 100);
				this.getWorld().sendEntityStatus(player, EntityStatuses.BREAK_SHIELD);
			}
		}
	}

}
