package net.fabric_extras.fabric.client;

import net.fabric_extras.shield_api.client.ShieldAPIClient;
import net.fabricmc.api.ClientModInitializer;

public final class FabricClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ShieldAPIClient.init();
	}
}
