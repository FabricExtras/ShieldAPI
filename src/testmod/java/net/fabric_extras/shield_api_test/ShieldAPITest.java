package net.fabric_extras.shield_api_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShieldAPITest implements ModInitializer {
	public static final String MOD_ID = "shield_api_test";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryKey<Item> TEST_BUCKLER_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "test_buckler"));
	public static final Item TEST_BUCKLER = new ShieldItem(
			new Item.Settings()
					.maxDamage(150)
					.repairable(Items.IRON_INGOT)
					.attributeModifiers(
							AttributeModifiersComponent.builder()
									.add(
											EntityAttributes.ARMOR,
											new EntityAttributeModifier(
													Identifier.of(MOD_ID, "shield"),
													4.0,
													EntityAttributeModifier.Operation.ADD_VALUE
											),
											AttributeModifierSlot.HAND)
									.build()
					)
					.registryKey(TEST_BUCKLER_KEY)
	);

	public static final RegistryKey<Item> TEST_SHIELD_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "test_shield"));
	public static final Item TEST_SHIELD = new ShieldItem(
			new Item.Settings()
					.maxDamage(150)
					.repairable(Items.IRON_INGOT)
					.equippableUnswappable(EquipmentSlot.OFFHAND)
					.attributeModifiers(
							AttributeModifiersComponent.builder()
									.add(
											EntityAttributes.ARMOR,
											new EntityAttributeModifier(
													Identifier.of(MOD_ID, "shield"),
													4.0,
													EntityAttributeModifier.Operation.ADD_VALUE
											),
											AttributeModifierSlot.OFFHAND)
									.build()
					)
					.registryKey(TEST_SHIELD_KEY)
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Shield API Test initialized!");

		Registry.register(Registries.ITEM, TEST_BUCKLER_KEY, TEST_BUCKLER);
		Registry.register(Registries.ITEM, TEST_SHIELD_KEY, TEST_SHIELD);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(TEST_BUCKLER);
			content.add(TEST_SHIELD);
		});
	}
}
