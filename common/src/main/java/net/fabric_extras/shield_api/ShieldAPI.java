package net.fabric_extras.shield_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShieldAPI {
	public static final String MOD_ID = "shield_api";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Shield API initialized!");
	}
}
