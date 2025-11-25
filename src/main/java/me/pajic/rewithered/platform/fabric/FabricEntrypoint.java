package me.pajic.rewithered.platform.fabric;

//? fabric {

import me.pajic.rewithered.Rewithered;
import me.pajic.rewithered.mixson.LootTableEvents;
import me.pajic.rewithered.mixson.WorldgenDataEvents;
import me.pajic.rewithered.potion.ModPotions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		LootTableEvents.register();
		WorldgenDataEvents.register();
		Rewithered.onInitialize();
		ModPotions.init();
		initRegistry();
		initBrewing();
	}

	private void initRegistry() {
		Registry.register(
				BuiltInRegistries.POTION,
				Rewithered.id("decay"),
				ModPotions.DECAY
		);
		Registry.register(
				BuiltInRegistries.POTION,
				Rewithered.id("long_decay"),
				ModPotions.LONG_DECAY
		);
		Registry.register(
				BuiltInRegistries.POTION,
				Rewithered.id("strong_decay"),
				ModPotions.STRONG_DECAY
		);
	}

	private void initBrewing() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
					Potions.AWKWARD,
					Ingredient.of(Items.WITHER_ROSE),
					BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY)
			);
			builder.registerPotionRecipe(
					BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY),
					Ingredient.of(Items.REDSTONE),
					BuiltInRegistries.POTION.wrapAsHolder(ModPotions.LONG_DECAY)
			);
			builder.registerPotionRecipe(
					BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY),
					Ingredient.of(Items.GLOWSTONE_DUST),
					BuiltInRegistries.POTION.wrapAsHolder(ModPotions.STRONG_DECAY)
			);
		});
	}
}
//?}
