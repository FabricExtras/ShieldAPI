package net.fabric_extras.shield_api.client;

import net.fabric_extras.shield_api.mixin.client.ModelPredicateProviderRegistryInvoker;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ShieldAPIClient {

	public static void init() {
	}

	public static void registerModelPredicateProviders(Item item) {
		ModelPredicateProviderRegistryInvoker.invokeRegister(item, Identifier.of("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
	}
}
