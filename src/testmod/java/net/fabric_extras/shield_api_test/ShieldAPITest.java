package net.fabric_extras.shield_api_test;

import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShieldAPITest implements ModInitializer {
	public static final String MOD_ID = "shieldapitest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item TEST_BUCKLER = new CustomShieldItem(
			null,
			() -> Ingredient.ofItems(Items.IRON_INGOT),
			List.of(
					new Pair<>(
							EntityAttributes.GENERIC_ARMOR,
							new EntityAttributeModifier(
									"test_buckler_armor",
									4.0,
									EntityAttributeModifier.Operation.ADDITION
							)
					)
			),
			new FabricItemSettings().maxDamage(150));

	public static final Item TEST_SHIELD = new CustomShieldItem(
			null,
			() -> Ingredient.ofItems(Items.IRON_INGOT),
			List.of(
					new Pair<>(
							EntityAttributes.GENERIC_ARMOR,
							new EntityAttributeModifier(
									"test_shield_armor",
									4.0,
									EntityAttributeModifier.Operation.ADDITION
							)
					)
			),
			new FabricItemSettings().maxDamage(150));

	@Override
	public void onInitialize() {
		LOGGER.info("Shield API Test initialized!");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(TEST_BUCKLER);
			content.add(TEST_SHIELD);
		});
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "test_buckler"), TEST_BUCKLER);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "test_shield"), TEST_SHIELD);

		((CustomShieldItem) TEST_BUCKLER).setAttributeModifiers(
				List.of(
						new Pair<>(
								EntityAttributes.GENERIC_ARMOR,
								new EntityAttributeModifier(
										"test_buckler_armor",
										2.0,
										EntityAttributeModifier.Operation.ADDITION
								)
						)
				)
		);

		((CustomShieldItem) TEST_SHIELD).setAttributeModifiers(
				List.of(
						new Pair<>(
								EntityAttributes.GENERIC_ARMOR,
								new EntityAttributeModifier(
										"test_shield_armor",
										2.0,
										EntityAttributeModifier.Operation.ADDITION
								)
						)
				)
		);
	}
}
