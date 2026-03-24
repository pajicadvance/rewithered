package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffects.class)
public class MobEffectsMixin {

	@ModifyExpressionValue(
			method = "<clinit>",
			at = @At(
					value = "CONSTANT",
					args = "intValue=7561558"
			)
	)
	private static int changeWitherColor(int original) {
		return 0x5E574F;
	}
}
