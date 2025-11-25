package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.rewithered.Rewithered;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin extends Mob {
    protected WitherSkeletonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "populateDefaultEquipmentSlots",
            at = @At("HEAD")
    )
    private void spawnWitherSkeletonsWithArmor(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (Rewithered.CONFIG.witherSkeletonTweaks.spawnWithArmor.get()) super.populateDefaultEquipmentSlots(random, difficulty);
    }

    @Inject(
            method = "populateDefaultEquipmentEnchantments",
            at = @At("HEAD")
    )
    private void enchantEquipmentOnWitherSkeletons(ServerLevelAccessor level, RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (Rewithered.CONFIG.witherSkeletonTweaks.spawnWithEnchantedEquipment.get()) super.populateDefaultEquipmentEnchantments(level, random, difficulty);
    }

    @WrapWithCondition(
            method = "getArrow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;igniteForSeconds(F)V"
            )
    )
    private boolean replaceIgniteWithWitherEffect(AbstractArrow instance, float v) {
        if (Rewithered.CONFIG.witherSkeletonTweaks.shootWitherArrows.get() && instance instanceof Arrow arrow) {
            arrow.addEffect(new MobEffectInstance(MobEffects.WITHER, 100));
            return false;
        }
        return true;
    }
}
