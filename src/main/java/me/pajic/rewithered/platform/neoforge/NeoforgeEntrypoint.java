package me.pajic.rewithered.platform.neoforge;

//? neoforge {

/*import me.pajic.rewithered.Rewithered;
import me.pajic.rewithered.mixson.LootTableEvents;
import me.pajic.rewithered.mixson.WorldgenDataEvents;
import me.pajic.rewithered.potion.ModPotions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Rewithered.MOD_ID)
@EventBusSubscriber(modid = Rewithered.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		Rewithered.onInitialize();
	}

	@SubscribeEvent
	private static void initRegistry(RegisterEvent event) {
		LootTableEvents.register();
		WorldgenDataEvents.register();
		ModPotions.init();
		event.register(
				Registries.POTION,
				registry -> {
					registry.register(Rewithered.id("decay"), ModPotions.DECAY);
					registry.register(Rewithered.id("long_decay"), ModPotions.LONG_DECAY);
					registry.register(Rewithered.id("strong_decay"), ModPotions.STRONG_DECAY);
				}
		);
	}

	@SubscribeEvent
	private static void initBrewing(RegisterBrewingRecipesEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addMix(
				Potions.AWKWARD,
				Items.WITHER_ROSE,
				BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY)
		);
		builder.addMix(
				BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY),
				Items.REDSTONE,
				BuiltInRegistries.POTION.wrapAsHolder(ModPotions.LONG_DECAY)
		);
		builder.addMix(
				BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY),
				Items.GLOWSTONE_DUST,
				BuiltInRegistries.POTION.wrapAsHolder(ModPotions.STRONG_DECAY)
		);
	}
}
*///?}
