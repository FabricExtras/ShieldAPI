package net.fabric_extras.shield_api.item;

import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

public class CustomShieldItem extends ShieldItem {

	private Supplier<AttributeModifiersComponent> attributeModifiers;
	public final static HashSet<CustomShieldItem> instances = new HashSet<>();

	@Nullable
	private final RegistryEntry<SoundEvent> equipSound;

	private final Supplier<Ingredient> repairIngredientSupplier;

	public CustomShieldItem(@Nullable RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList, Settings settings) {
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

	public void setAttributeModifiers(List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList) {
		this.attributeModifiers = buildModifiers(attributeModifierList);
	}

	@Override
	public AttributeModifiersComponent getAttributeModifiers() {
		return (AttributeModifiersComponent) this.attributeModifiers.get();
	}

	protected Supplier<AttributeModifiersComponent> buildModifiers(List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifierList) {
		return Suppliers.memoize(
				() -> {
					AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
					for (Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier> pair : attributeModifierList) {
						builder.add(pair.getLeft(), pair.getRight(), AttributeModifierSlot.HAND);
					}
					return builder.build();
				}
		);
	}

	@Override
	public @Nullable RegistryEntry<SoundEvent> getEquipSound() {
		return this.equipSound != null ? this.equipSound : super.getEquipSound();
	}
}
