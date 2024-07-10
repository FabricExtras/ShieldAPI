package net.fabric_extras.shield_api;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShieldAPI implements ModInitializer {
	public static final String MOD_ID = "shield_api";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Shield API initialized!");
	}
}
