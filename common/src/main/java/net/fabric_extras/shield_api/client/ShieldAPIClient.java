package net.fabric_extras.shield_api.client;

/**
 * Since 1.21.4 item models are data driven: the old client side `blocking` model predicate
 * (registered via `ModelPredicateProviderRegistry`, removed in 1.21.4) is replaced by an
 * item model definition shipped by the mod that adds the shield:
 * <pre>
 * assets/&lt;namespace&gt;/items/&lt;shield_id&gt;.json
 * {
 *   "model": {
 *     "type": "minecraft:condition",
 *     "property": "minecraft:using_item",
 *     "on_false": { "type": "minecraft:model", "model": "&lt;ns&gt;:item/&lt;shield&gt;" },
 *     "on_true":  { "type": "minecraft:model", "model": "&lt;ns&gt;:item/&lt;shield&gt;_blocking" }
 *   }
 * }
 * </pre>
 * so no client side registration is needed any more.
 */
public class ShieldAPIClient {

	public static void init() {
	}
}
