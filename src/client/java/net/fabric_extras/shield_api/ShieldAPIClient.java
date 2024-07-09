package net.fabric_extras.shield_api;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ShieldAPIClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
	}

	public static void registerModelPredicateProviders(Item item) {
		ModelPredicateProviderRegistry.register(item, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
	}
}
