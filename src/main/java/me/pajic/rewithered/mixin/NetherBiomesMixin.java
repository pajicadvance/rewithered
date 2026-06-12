package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.rewithered.Rewithered;
import me.pajic.rewithered.util.ModUtil;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.world.entity.EntityType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherBiomes.class)
public class NetherBiomesMixin {

    @ModifyExpressionValue(
            method = "soulSandValley",
            at = {
					@At(
							value = "FIELD",
							target = "Lnet/minecraft/world/entity/EntityType;SKELETON:Lnet/minecraft/world/entity/EntityType;",
							opcode = Opcodes.GETSTATIC
					),
					@At(
							value = "FIELD",
							target = "Lnet/minecraft/world/entity/EntityTypes;SKELETON:Lnet/minecraft/world/entity/EntityType;",
							opcode = Opcodes.GETSTATIC
					)
			},
			require = 1
    )
    private static EntityType<?> replaceSkeletonsWithWitherSkeletons(EntityType<?> original) {
        return Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInSoulSandValley.get() ?
				ModUtil.WITHER_SKELETON : original;
    }
}
