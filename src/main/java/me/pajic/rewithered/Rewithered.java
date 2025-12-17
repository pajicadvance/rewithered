package me.pajic.rewithered;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.rewithered.config.ModConfig;
import me.pajic.rewithered.platform.Platform;
import net.minecraft.resources.Identifier;
import net.ramixin.mixson.debug.DebugMode;
import net.ramixin.mixson.inline.Mixson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.pajic.rewithered.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.rewithered.platform.neoforge.NeoforgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class Rewithered {

	public static final String MOD_ID = /*$ mod_id*/ "rewithered";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.0.6";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Rewithered";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier CONFIG_RL = id("config");
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		if (PLATFORM.isDebug()) Mixson.setDebugMode(DebugMode.EXPORT);
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		*///?}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
