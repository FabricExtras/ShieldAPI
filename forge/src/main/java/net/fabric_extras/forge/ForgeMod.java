package net.fabric_extras.forge;

import net.fabric_extras.shield_api.ShieldAPI;
import net.minecraftforge.fml.common.Mod;

@Mod(ShieldAPI.MOD_ID)
public final class ForgeMod {
	public ForgeMod() {
		// Run our common setup.
		ShieldAPI.init();
	}
}
