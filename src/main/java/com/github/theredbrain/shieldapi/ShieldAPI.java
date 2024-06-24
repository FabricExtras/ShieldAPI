package com.github.theredbrain.shieldapi;

import com.github.theredbrain.shieldapi.item.CustomShieldItem;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;


public class ShieldAPI implements ModInitializer {
	public static final String MOD_ID = "shieldapi";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static HashSet<CustomShieldItem> instances = new HashSet<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Shield API initialized!");
	}
}
