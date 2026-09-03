package me.pajic.rewithered;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.rewithered.config.ModConfig;
import me.pajic.rewithered.mixson.DataPatches;
import me.pajic.rewithered.mixson.MixsonHelper;
import me.pajic.rewithered.platform.MultiLoaderUtil;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rewithered {

    public static final String MOD_ID = /*$ mod_id*/ "rewithered";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

    public static void onInitialize() {
        MixsonHelper.setDebugFlags();
        DataPatches.init();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (MultiLoaderUtil.INSTANCE.isDevEnv()) LOGGER.info(message, args);
    }
}
