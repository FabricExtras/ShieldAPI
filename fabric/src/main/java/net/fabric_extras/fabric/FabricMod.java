package net.fabric_extras.fabric;

import net.fabric_extras.shield_api.ShieldAPI;
import net.fabricmc.api.ModInitializer;

public final class FabricMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// Run our common setup.
		ShieldAPI.init();
	}
}
