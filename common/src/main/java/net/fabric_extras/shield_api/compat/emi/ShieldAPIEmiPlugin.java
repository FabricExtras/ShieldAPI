package net.fabric_extras.shield_api.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.recipe.EmiAnvilRecipe;
import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

@EmiEntrypoint
public class ShieldAPIEmiPlugin implements EmiPlugin {
	@Override
	public void register(EmiRegistry registry) {
		// Register anvil repair recipes for CustomShieldItem instances
		for (CustomShieldItem shield : CustomShieldItem.instances) {
			registerAnvilRecipe(registry, shield);
		}
	}

	private void registerAnvilRecipe(EmiRegistry registry, Item item) {
		// Get the repair ingredient
		Ingredient repairIngredient;
		if (item instanceof CustomShieldItem shield) {
			repairIngredient = shield.getRepairIngredientSupplier().get();
		} else {
			return;
		}

		var itemEntry = item.getRegistryEntry();
		if (itemEntry == null || itemEntry.getKey().isEmpty()) {
			return; // Item is not registered, cannot create recipe
		}
		var itemId = itemEntry.getKey().get().getValue();

		// Create the anvil recipe
		Identifier id = Identifier.of(itemId.getNamespace(), "anvil_repair_shield_api/" +
				itemId.getPath());

		EmiStack input = EmiStack.of(item);
		EmiIngredient repairMaterial = EmiIngredient.of(repairIngredient);

		var recipe = new EmiAnvilRecipe(input, repairMaterial, id);

		registry.addRecipe(recipe);
	}
}