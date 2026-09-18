package me.pajic.rewithered.datagen;

//? >26.2 && fabric {

import me.pajic.rewithered.potion.ModPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import java.util.Set;import java.util.concurrent.CompletableFuture;

public class BrewingRecipeProvider extends FabricRecipeProvider {

    private static final Set<Item> CONTAINERS = Set.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);

    public BrewingRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override @NotNull
    protected RecipeProvider createRecipeProvider(
            @NotNull HolderLookup.Provider registries,
            @NotNull BootstrapContext<Recipe<?>> recipes,
            @NotNull BootstrapContext<Advancement> advancements
    ) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                buildStartMix(Items.WITHER_ROSE, ModPotions.DECAY, output);
                buildMix(ModPotions.DECAY, Items.REDSTONE, ModPotions.LONG_DECAY, output);
                buildMix(ModPotions.DECAY, Items.GLOWSTONE_DUST, ModPotions.STRONG_DECAY, output);
                BrewingRecipeBuilder.brewingContainerTransform(
                        Items.POTION, BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY), Items.GUNPOWDER, Items.SPLASH_POTION
                ).save(output);
                BrewingRecipeBuilder.brewingContainerTransform(
                        Items.POTION, BuiltInRegistries.POTION.wrapAsHolder(ModPotions.DECAY), Items.DRAGON_BREATH, Items.LINGERING_POTION
                ).save(output);
            }
        };
    }

    private static void buildMix(final Potion input, final Item reagent, final Potion output, RecipeOutput recipeOutput) {
        CONTAINERS.forEach(item -> BrewingRecipeBuilder.brewingMix(
                item, BuiltInRegistries.POTION.wrapAsHolder(input), reagent, BuiltInRegistries.POTION.wrapAsHolder(output)
        ).save(recipeOutput));
    }

    private static void buildStartMix(final Item reagent, final Potion output, RecipeOutput recipeOutput) {
        buildMix(Potions.WATER.value(), reagent, Potions.MUNDANE.value(), recipeOutput);
        buildMix(Potions.AWKWARD.value(), reagent, output, recipeOutput);
    }

    @Override @NotNull
    public String getName() {
        return "Rewithered Brewing Recipe Provider";
    }
}
//?}
