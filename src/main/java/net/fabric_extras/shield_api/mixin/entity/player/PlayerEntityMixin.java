package net.fabric_extras.shield_api.mixin.entity.player;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow
	public abstract void incrementStat(Stat<?> stat);

	@Shadow
	public abstract ItemCooldownManager getItemCooldownManager();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "damageShield", at = @At("HEAD"))
	protected void shield_api$damageShield(float amount, CallbackInfo ci) {
		if (this.activeItemStack.getItem() instanceof CustomShieldItem customShieldItem) {
			if (!this.getWorld().isClient) {
				this.incrementStat(Stats.USED.getOrCreateStat(customShieldItem));
			}

			if (amount >= 3.0F) {
				int i = 1 + MathHelper.floor(amount);
				Hand hand = this.getActiveHand();
				this.activeItemStack.damage(i, this, player -> player.sendToolBreakStatus(hand));
				if (this.activeItemStack.isEmpty()) {
					if (hand == Hand.MAIN_HAND) {
						this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
					}

					this.activeItemStack = ItemStack.EMPTY;
					this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.getWorld().random.nextFloat() * 0.4F);
				}
			}
		}
	}

	@Inject(method = "disableShield", at = @At("TAIL"))
	public void shield_api$disableShield(boolean sprinting, CallbackInfo ci) {
		float f = 0.25F + (float) EnchantmentHelper.getEfficiency(this) * 0.05F;
		if (sprinting) {
			f += 0.75F;
		}

		if (this.random.nextFloat() < f) {
			for (CustomShieldItem customShieldItem : CustomShieldItem.instances) {
				this.getItemCooldownManager().set(customShieldItem, 100);
			}
			this.clearActiveItem();
			this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
		}
	}

}
