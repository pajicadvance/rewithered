package me.pajic.rewithered.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.pajic.rewithered.Rewithered;

@Version(version = 1)
public class ModConfig extends Config {

	public ModConfig() {
		super(Rewithered.id("config"));
	}

	public DecayPotion decayPotion = new DecayPotion();
	public WitherSkeletonTweaks witherSkeletonTweaks = new WitherSkeletonTweaks();

	@RequiresAction(action = Action.RESTART)
	public static class DecayPotion extends ConfigSection {
		public ValidatedBoolean enabled = new ValidatedBoolean(true);
		public ValidatedInt duration = new ValidatedInt(40, Integer.MAX_VALUE, 20);
		public ValidatedInt strength = new ValidatedInt(1, 5, 1);
		public ValidatedInt longDuration = new ValidatedInt(60, Integer.MAX_VALUE, 20);
		public ValidatedInt longStrength = new ValidatedInt(1, 5, 1);
		public ValidatedInt strongDuration = new ValidatedInt(20, Integer.MAX_VALUE, 20);
		public ValidatedInt strongStrength = new ValidatedInt(2, 5, 1);
	}

	public static class WitherSkeletonTweaks extends ConfigSection {
		@RequiresAction(action = Action.RESTART)
		public ValidatedBoolean replaceSkeletonsInSoulSandValley = new ValidatedBoolean(true);
		@RequiresAction(action = Action.RESTART)
		public ValidatedBoolean replaceSkeletonsInFortress = new ValidatedBoolean(true);
		public ValidatedBoolean useBowsInSoulSandValley = new ValidatedBoolean(true);
		public ValidatedBoolean useBowsInFortress = new ValidatedBoolean(true);
		public ValidatedFloat soulSandValleyBowChance = new ValidatedFloat(100, 100, 0);
		public ValidatedFloat fortressBowChance = new ValidatedFloat(25, 100, 0);
		public ValidatedBoolean shootWitherArrows = new ValidatedBoolean(true);
		@RequiresAction(action = Action.RESTART)
		public ValidatedBoolean dropWitherArrows = new ValidatedBoolean(true);
		public ValidatedBoolean spawnWithArmor = new ValidatedBoolean(true);
		public ValidatedBoolean spawnWithEnchantedEquipment = new ValidatedBoolean(true);
	}
}
