package net.fabric_extras.shield_api.item;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * A shield item with a custom model, custom repair ingredient, custom equip sound and configurable attributes.
 * <p>
 * Since 1.21.2 all blocking behaviour (damage reduction, item damage, disable cooldown, axe interactions,
 * the `blocking` item model state) is data driven via the {@code minecraft:blocks_attacks} component,
 * so this class mostly assembles vanilla-equivalent components on top of the settings it is given
 * (the components applied here win over anything already set on the passed settings).
 */
public class CustomShieldItem extends ShieldItem {

	/** Vanilla shield behaviour: 0.25s block delay, 90 degree angle, full reduction, 3+ damage breaks durability. */
	public static final BlocksAttacksComponent DEFAULT_BLOCKS_ATTACKS = new BlocksAttacksComponent(
			0.25F,
			1.0F,
			List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
			new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F),
			Optional.of(DamageTypeTags.BYPASSES_SHIELD),
			Optional.of(SoundEvents.ITEM_SHIELD_BLOCK),
			Optional.of(SoundEvents.ITEM_SHIELD_BREAK)
	);

	public final static HashSet<CustomShieldItem> instances = new HashSet<>();

	private ComponentMap components;

	public CustomShieldItem(@Nullable RegistryEntry<SoundEvent> equipSound,
							List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList,
							Settings settings) {
		super(configure(equipSound, attributeModifierList, settings));
		this.components = super.getComponents();
		instances.add(this);
	}

	private static Settings configure(@Nullable RegistryEntry<SoundEvent> equipSound,
									  List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList,
									  Settings settings) {
		var equippable = EquippableComponent.builder(EquipmentSlot.OFFHAND).swappable(false);
		if (equipSound != null) {
			equippable.equipSound(equipSound);
		}
		return settings
				.component(DataComponentTypes.BLOCKS_ATTACKS, DEFAULT_BLOCKS_ATTACKS)
				.component(DataComponentTypes.EQUIPPABLE, equippable.build())
				.component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK)
				.attributeModifiers(buildModifiers(attributeModifierList));
	}

	/**
	 * Replaces the attribute modifiers of this item (used to apply values loaded from config,
	 * after the item has already been constructed).
	 */
	public void setAttributeModifiers(List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList) {
		setAttributeModifiers(buildModifiers(attributeModifierList));
	}

	public void setAttributeModifiers(AttributeModifiersComponent attributeModifiers) {
		this.components = ComponentMap.builder()
				.addAll(super.getComponents())
				.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributeModifiers)
				.build();
	}

	@Override
	public ComponentMap getComponents() {
		return components == null ? super.getComponents() : components;
	}

	public AttributeModifiersComponent getAttributeModifiers() {
		return getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
	}

	protected static AttributeModifiersComponent buildModifiers(List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList) {
		AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
		for (Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier> pair : attributeModifierList) {
			builder.add(pair.getLeft(), pair.getRight(), AttributeModifierSlot.HAND);
		}
		return builder.build();
	}
}
