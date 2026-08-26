package net.fabric_extras.shield_api_test;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShieldAPITest {
	public static final String MOD_ID = "shield_api_test";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier TEST_BUCKLER_ID = Identifier.of(MOD_ID, "test_buckler");
	public static final Identifier TEST_SHIELD_ID = Identifier.of(MOD_ID, "test_shield");

	public static final CustomShieldItem TEST_BUCKLER = create(TEST_BUCKLER_ID, 4.0);
	public static final CustomShieldItem TEST_SHIELD = create(TEST_SHIELD_ID, 4.0);

	private static CustomShieldItem create(Identifier id, double armor) {
		return new CustomShieldItem(
				null,
				attributes(id, armor),
				new Item.Settings()
						.registryKey(RegistryKey.of(RegistryKeys.ITEM, id))
						.maxDamage(150)
						.repairable(ItemTags.IRON_TOOL_MATERIALS)
		);
	}

	private static List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributes(Identifier id, double armor) {
		return List.of(new Pair<>(
				EntityAttributes.ARMOR,
				new EntityAttributeModifier(id.withSuffixedPath("_shield"), armor, EntityAttributeModifier.Operation.ADD_VALUE)
		));
	}

	public static void init() {
		LOGGER.info("Shield API Test initialized!");

		Registry.register(Registries.ITEM, TEST_BUCKLER_ID, TEST_BUCKLER);
		Registry.register(Registries.ITEM, TEST_SHIELD_ID, TEST_SHIELD);

		// Late attribute override, mirroring how config driven mods apply their values after registration
		TEST_BUCKLER.setAttributeModifiers(attributes(TEST_BUCKLER_ID, 2.0));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(TEST_BUCKLER);
			content.add(TEST_SHIELD);
		});
	}
}
