package me.pajic.rewithered.potion;

import me.pajic.rewithered.Rewithered;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.alchemy.Potion;

public class DecayPotion extends Potion {

    public DecayPotion(int duration, int amplifier) {
        super("decay", new MobEffectInstance(MobEffects.WITHER, duration, amplifier));
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return super.isEnabled(enabledFeatures) && Rewithered.CONFIG.decayPotion.enabled.get();
    }
}
