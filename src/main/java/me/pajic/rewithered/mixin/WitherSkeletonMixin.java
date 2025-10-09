package me.pajic.rewithered.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.rewithered.Main;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    @SuppressWarnings("resource")
    @Inject(
            method = "populateDefaultEquipmentSlots",
            at = @At("HEAD")
    )
    private void spawnWitherSkeletonsWithArmor(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (Main.CONFIG.witherSkeletonTweaks.spawnWithArmor.get() && random.nextFloat() < 0.15F * difficulty.getSpecialMultiplier()) {
            int i = random.nextInt(2);
            float f = level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (random.nextFloat() < 0.095F) {
                i++;
            }
            if (random.nextFloat() < 0.095F) {
                i++;
            }
            if (random.nextFloat() < 0.095F) {
                i++;
            }
            boolean bl = true;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    ItemStack itemStack = getItemBySlot(equipmentSlot);
                    if (!bl && random.nextFloat() < f) {
                        break;
                    }
                    bl = false;
                    if (itemStack.isEmpty()) {
                        Item item = getEquipmentForSlot(equipmentSlot, i);
                        if (item != null) {
                            setItemSlot(equipmentSlot, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }

    @Inject(
            method = "populateDefaultEquipmentEnchantments",
            at = @At("HEAD")
    )
    private void enchantEquipmentOnWitherSkeletons(ServerLevelAccessor level, RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (Main.CONFIG.witherSkeletonTweaks.spawnWithEnchantedEquipment.get()) {
            enchantSpawnedWeapon(level, random, difficulty);
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    enchantSpawnedArmor(level, random, equipmentSlot, difficulty);
                }
            }
        }
    }

    @WrapWithCondition(
            method = "getArrow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;igniteForSeconds(F)V"
            )
    )
    private boolean replaceIgniteWithWitherEffect(AbstractArrow instance, float v) {
        if (Main.CONFIG.witherSkeletonTweaks.shootWitherArrows.get() && instance instanceof Arrow arrow) {
            arrow.addEffect(new MobEffectInstance(MobEffects.WITHER, 100));
            return false;
        }
        return true;
    }
}
