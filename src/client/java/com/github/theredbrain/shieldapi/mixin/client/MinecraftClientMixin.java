package com.github.theredbrain.shieldapi.mixin.client;

import com.github.theredbrain.shieldapi.ShieldAPI;
import com.github.theredbrain.shieldapi.ShieldAPIClient;
import com.github.theredbrain.shieldapi.item.CustomShieldItem;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "run", at = @At("HEAD"))
	private void shieldapi$run(CallbackInfo ci) {
		for (CustomShieldItem customShieldItem : ShieldAPI.instances) {
			ShieldAPIClient.registerModelPredicateProviders(customShieldItem);
		}
	}
}
