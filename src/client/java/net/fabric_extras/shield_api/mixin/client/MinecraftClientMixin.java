package net.fabric_extras.shield_api.mixin.client;

import net.fabric_extras.shield_api.ShieldAPI;
import net.fabric_extras.shield_api.ShieldAPIClient;
import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "run", at = @At("HEAD"))
	private void shield_api$run(CallbackInfo ci) {
		for (CustomShieldItem customShieldItem : ShieldAPI.instances) {
			ShieldAPIClient.registerModelPredicateProviders(customShieldItem);
		}
	}
}
