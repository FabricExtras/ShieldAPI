package net.fabric_extras.shield_api.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

/**
 * Base class for shields with custom models, sounds, repair ingredients and attributes.
 *
 * <p>1.20.1 deltas versus the 2.2.0 (1.21.1) API:
 * <ul>
 *   <li>attribute modifiers are a Guava {@code Multimap<EntityAttribute, EntityAttributeModifier>}
 *       returned from {@code getAttributeModifiers(EquipmentSlot)} -- there is no
 *       {@code AttributeModifiersComponent}. Each {@code EntityAttributeModifier} is UUID-keyed;
 *       callers that hold modern {@code Identifier}-keyed definitions should derive the UUID with
 *       {@code UUID.nameUUIDFromBytes(id.toString().getBytes(StandardCharsets.UTF_8))}.</li>
 *   <li>{@code equipSound} is a bare {@link SoundEvent}, not a {@code RegistryEntry<SoundEvent>}:
 *       1.20.1's {@code Equipment#getEquipSound()} returns the raw sound event.</li>
 * </ul>
 * The modifiers stay behind a memoizing {@link Supplier}, exactly as in 2.2.0, so a shield built
 * during class-init does not force its attributes to resolve before the registries are ready.
 */
public class CustomShieldItem extends ShieldItem {

	private Supplier<Multimap<EntityAttribute, EntityAttributeModifier>> attributeModifiers;
	public final static HashSet<CustomShieldItem> instances = new HashSet<>();

	@Nullable
	private final SoundEvent equipSound;

	private final Supplier<Ingredient> repairIngredientSupplier;

	public CustomShieldItem(@Nullable SoundEvent equipSound, Supplier<Ingredient> repairIngredientSupplier, List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList, Settings settings) {
		super(settings);
		this.attributeModifiers = buildModifiers(attributeModifierList);
		this.equipSound = equipSound;
		this.repairIngredientSupplier = repairIngredientSupplier;
		instances.add(this);
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return this.repairIngredientSupplier.get().test(ingredient) || super.canRepair(stack, ingredient);
	}

	public void setAttributeModifiers(List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList) {
		this.attributeModifiers = buildModifiers(attributeModifierList);
	}

	/**
	 * 2.2.0 registers the modifiers for {@code AttributeModifierSlot.HAND}, which covers both hands,
	 * so both hand slots are answered here (the legacy 1.0.x line answered {@code OFFHAND} only).
	 */
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
			return this.attributeModifiers.get();
		}
		return super.getAttributeModifiers(slot);
	}

	protected Supplier<Multimap<EntityAttribute, EntityAttributeModifier>> buildModifiers(List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList) {
		return Suppliers.memoize(
				() -> {
					ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
					for (Pair<EntityAttribute, EntityAttributeModifier> pair : attributeModifierList) {
						builder.put(pair.getLeft(), pair.getRight());
					}
					return builder.build();
				}
		);
	}

	@Override
	public @Nullable SoundEvent getEquipSound() {
		return this.equipSound != null ? this.equipSound : super.getEquipSound();
	}

	public Supplier<Ingredient> getRepairIngredientSupplier() {
		return repairIngredientSupplier;
	}
}
