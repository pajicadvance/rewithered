package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.rewithered.Rewithered;
import me.pajic.rewithered.util.ModUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherFortressStructure.class)
public class NetherFortressStructureMixin {

    @ModifyExpressionValue(
            method = "<clinit>",
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
        return Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInFortress.get() ?
				ModUtil.WITHER_SKELETON : original;
    }
}
