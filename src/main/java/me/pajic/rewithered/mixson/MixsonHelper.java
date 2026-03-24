package me.pajic.rewithered.mixson;

import com.google.gson.JsonElement;
import me.pajic.rewithered.Rewithered;
import net.ramixin.mixson.Mixson;
import net.ramixin.mixson.MixsonCodecs;
import net.ramixin.mixson.enums.ErrorPolicy;
import net.ramixin.mixson.enums.Lifetime;
import net.ramixin.mixson.util.Index;
import net.ramixin.mixson.util.functions.Event;

import java.util.UUID;

public class MixsonHelper {

	private static final ErrorPolicy ERROR_POLICY = Rewithered.xplat().isDebug() ? ErrorPolicy.THROW : ErrorPolicy.LOG;

	public static UUID registerSingleJsonPersistent(String eventName, Index target, Event<JsonElement> event) {
		return Mixson.registerEvent(
				MixsonCodecs.JSON_ELEMENT,
				Mixson.DEFAULT_PRIORITY,
				Lifetime.PERSISTENT,
				ERROR_POLICY,
				eventName,
				index -> index.idEquals(target),
				event
		);
	}
}
