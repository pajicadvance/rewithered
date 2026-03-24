package me.pajic.rewithered.platform;

public interface Platform {

	boolean isDevelopmentEnvironment();

	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}
}
