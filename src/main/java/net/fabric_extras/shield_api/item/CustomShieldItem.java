package net.fabric_extras.shield_api.item;

import net.fabric_extras.shield_api.ShieldAPI;
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

import java.util.List;
import java.util.function.Supplier;

public class CustomShieldItem extends ShieldItem {

	private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	@Nullable
	private final SoundEvent equipSound;

	private final Supplier<Ingredient> repairIngredientSupplier;

	public CustomShieldItem(@Nullable SoundEvent equipSound, Supplier<Ingredient> repairIngredientSupplier, List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList, Settings settings) {
		super(settings);
		this.attributeModifiers = buildModifiers(attributeModifierList);
		this.equipSound = equipSound;
		this.repairIngredientSupplier = repairIngredientSupplier;
		ShieldAPI.instances.add(this);
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return this.repairIngredientSupplier.get().test(ingredient) || super.canRepair(stack, ingredient);
	}

	public void setAttributeModifiers(List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList) {
		this.attributeModifiers = buildModifiers(attributeModifierList);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.OFFHAND) {
			return this.attributeModifiers;
		}
		return super.getAttributeModifiers(slot);
	}

	protected Multimap<EntityAttribute, EntityAttributeModifier> buildModifiers(List<Pair<EntityAttribute, EntityAttributeModifier>> attributeModifierList) {
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		for (Pair<EntityAttribute, EntityAttributeModifier> pair : attributeModifierList) {
			builder.put(pair.getLeft(), pair.getRight());
		}
		return builder.build();
	}

	@Override
	public @Nullable SoundEvent getEquipSound() {
		return this.equipSound != null ? this.equipSound : super.getEquipSound();
	}
}
