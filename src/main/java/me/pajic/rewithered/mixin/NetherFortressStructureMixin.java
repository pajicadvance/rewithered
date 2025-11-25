package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.rewithered.Rewithered;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherFortressStructure.class)
public class NetherFortressStructureMixin {

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/EntityType;SKELETON:Lnet/minecraft/world/entity/EntityType;"
            )
    )
    private static EntityType<?> replaceSkeletonsWithWitherSkeletons(EntityType<?> original) {
        return Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInFortress.get() ?
                EntityType.WITHER_SKELETON : original;
    }
}
