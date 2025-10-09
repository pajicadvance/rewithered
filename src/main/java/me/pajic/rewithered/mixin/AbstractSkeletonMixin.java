package me.pajic.rewithered.mixin;

import me.pajic.rewithered.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if < 1.21.8 {
/*import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;
*///?}

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin extends Mob {
    protected AbstractSkeletonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    //? if < 1.21.8
    /*@Shadow public abstract void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack);*/

    @Inject(
            method = "finalizeSpawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/AbstractSkeleton;populateDefaultEquipmentEnchantments(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V"
            )
    )
    private void assignWeapon(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            /*? if < 1.21.8 {*//*MobSpawnType spawnType*//*?} else {*/EntitySpawnReason spawnType/*?}*/,
            SpawnGroupData spawnGroupData,
            CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        boolean isInFortress = level.getLevel().structureManager().getStructureAt(
                getOnPos(),
                level.registryAccess().lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.FORTRESS).value()
        ) != StructureStart.INVALID_START;
        if (getType() == EntityType.WITHER_SKELETON) {
            if (
                    Main.CONFIG.witherSkeletonTweaks.useBowsInSoulSandValley.get() &&
                    level.getBiome(getOnPos()).is(Biomes.SOUL_SAND_VALLEY) &&
                    !isInFortress &&
                    level.getRandom().nextFloat() < Main.CONFIG.witherSkeletonTweaks.soulSandValleyBowChance.get() / 100
            ) {
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            } else if (
                    Main.CONFIG.witherSkeletonTweaks.useBowsInFortress.get() &&
                    isInFortress &&
                    level.getRandom().nextFloat() < Main.CONFIG.witherSkeletonTweaks.fortressBowChance.get() / 100
            ) {
                Main.debugLog("Assigning bow to wither skeleton spawned in fortress at {} {} {}", getX(), getY(), getZ());
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }
    }
}
