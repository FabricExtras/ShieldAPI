package net.fabric_extras.forge.client;

import net.fabric_extras.shield_api.ShieldAPI;
import net.fabric_extras.shield_api.client.ShieldAPIClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ShieldAPI.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientMod {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ShieldAPIClient.init();
	}
}
