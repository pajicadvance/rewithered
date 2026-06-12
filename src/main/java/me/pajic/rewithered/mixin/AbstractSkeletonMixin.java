package me.pajic.rewithered.mixin;

import me.pajic.rewithered.Rewithered;
import me.pajic.rewithered.util.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
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

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin extends Mob {

    protected AbstractSkeletonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "finalizeSpawn",
            at = @At(
                    value = "INVOKE",
					target = "Lnet/minecraft/world/entity/monster/skeleton/AbstractSkeleton;populateDefaultEquipmentEnchantments(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V"
            )
    )
    private void assignWeapon(
			ServerLevelAccessor level,
			DifficultyInstance difficulty,
			EntitySpawnReason spawnReason,
			SpawnGroupData groupData,
			CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        boolean isInFortress = ModUtil.getStructureAt(
				level, level.getLevel().structureManager(), getOnPos(),
                level.registryAccess().lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.FORTRESS).value()
        ) != StructureStart.INVALID_START;
        if (getType() == ModUtil.WITHER_SKELETON) {
            if (
					Rewithered.CONFIG.witherSkeletonTweaks.useBowsInSoulSandValley.get() &&
                    level.getBiome(getOnPos()).is(Biomes.SOUL_SAND_VALLEY) &&
                    !isInFortress &&
                    level.getRandom().nextFloat() < Rewithered.CONFIG.witherSkeletonTweaks.soulSandValleyBowChance.get() / 100
            ) {
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            } else if (
					Rewithered.CONFIG.witherSkeletonTweaks.useBowsInFortress.get() &&
                    isInFortress &&
                    level.getRandom().nextFloat() < Rewithered.CONFIG.witherSkeletonTweaks.fortressBowChance.get() / 100
            ) {
				Rewithered.debugLog("Assigning bow to wither skeleton spawned in fortress at {} {} {}", getX(), getY(), getZ());
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }
    }
}
