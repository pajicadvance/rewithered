package me.pajic.rewithered.potion;

import me.pajic.rewithered.Rewithered;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotions {

    public static final Potion DECAY = new DecayPotion(
			Rewithered.CONFIG.decayPotion.duration.get() * 20,
			Rewithered.CONFIG.decayPotion.strength.get() - 1
	);
    public static final Potion LONG_DECAY = new DecayPotion(
			Rewithered.CONFIG.decayPotion.longDuration.get() * 20,
			Rewithered.CONFIG.decayPotion.longStrength.get() - 1
	);
    public static final Potion STRONG_DECAY = new DecayPotion(
			Rewithered.CONFIG.decayPotion.strongDuration.get() * 20,
			Rewithered.CONFIG.decayPotion.strongStrength.get() - 1
	);
}
