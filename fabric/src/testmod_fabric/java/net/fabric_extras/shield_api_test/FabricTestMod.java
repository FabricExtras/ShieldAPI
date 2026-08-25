package net.fabric_extras.shield_api_test;

import net.fabricmc.api.ModInitializer;

public final class FabricTestMod implements ModInitializer {
	@Override
	public void onInitialize() {
		ShieldAPITest.init();
	}
}
