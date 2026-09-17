package net.fabric_extras.shield_api.client;

import net.fabric_extras.shield_api.mixin.client.ModelPredicateProviderRegistryInvoker;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ShieldAPIClient {

	public static void init() {
	}

	public static void registerModelPredicateProviders(Item item) {
		// `ModelPredicateProviderRegistry.register(Item, Identifier, ClampedModelPredicateProvider)` is
		// public in vanilla 1.20.1 but PRIVATE in the Forge-patched class, so the invoker mixin is what
		// keeps a single common code path across both loaders.
		ModelPredicateProviderRegistryInvoker.invokeRegister(item, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
	}
}
