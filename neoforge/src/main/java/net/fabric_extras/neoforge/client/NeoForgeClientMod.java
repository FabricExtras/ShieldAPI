package net.fabric_extras.neoforge.client;

import net.fabric_extras.shield_api.ShieldAPI;
import net.fabric_extras.shield_api.client.ShieldAPIClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ShieldAPI.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientMod {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ShieldAPIClient.init();
	}
}