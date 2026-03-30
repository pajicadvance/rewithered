package me.pajic.rewithered.mixson;

import me.pajic.rewithered.Rewithered;
import net.ramixin.mixson.Mixson;
import net.ramixin.mixson.enums.DebugOption;

public class DataPatches {

	public static void init() {
		if (Rewithered.xplat().isDebug()) {
			Mixson.enableDebugOption(DebugOption.BASIC_LOGGING);
			Mixson.enableDebugOption(DebugOption.EXTRA_LOGGING);
			Mixson.enableDebugOption(DebugOption.EXPORT_PATCHED_FILE);
		}

		LootTablePatches.init();
		WorldGenPatches.init();
	}
}
