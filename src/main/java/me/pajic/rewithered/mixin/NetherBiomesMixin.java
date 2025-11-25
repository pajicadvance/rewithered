package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.rewithered.Rewithered;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherBiomes.class)
public class NetherBiomesMixin {

    @ModifyExpressionValue(
            method = "soulSandValley",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/EntityType;SKELETON:Lnet/minecraft/world/entity/EntityType;"
            )
    )
    private static EntityType<?> replaceSkeletonsWithWitherSkeletons(EntityType<?> original) {
        return Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInSoulSandValley.get() ?
                EntityType.WITHER_SKELETON : original;
    }
}
