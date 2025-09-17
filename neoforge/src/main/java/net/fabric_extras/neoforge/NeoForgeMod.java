package net.fabric_extras.neoforge;

import net.fabric_extras.shield_api.ShieldAPI;
import net.neoforged.fml.common.Mod;

@Mod(ShieldAPI.MOD_ID)
public final class NeoForgeMod {
	public NeoForgeMod() {
		// Run our common setup.
		ShieldAPI.init();
	}
}
