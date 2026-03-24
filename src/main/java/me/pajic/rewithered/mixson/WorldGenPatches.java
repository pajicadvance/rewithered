package me.pajic.rewithered.mixson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.pajic.rewithered.Rewithered;
import net.ramixin.mixson.util.Index;

public class WorldGenPatches {

	public static void init() {
		if (Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInSoulSandValley.get()) MixsonHelper.registerSingleJsonPersistent(
				"Replace skeletons with wither skeletons in soul sand valleys",
				new Index("minecraft:worldgen/biome/soul_sand_valley"),
				context -> {
					JsonObject spawnCosts = context.getFile().getAsJsonObject().getAsJsonObject("spawn_costs");
					if (spawnCosts.has("minecraft:skeleton")) {
						double charge = spawnCosts.getAsJsonObject("minecraft:skeleton").get("charge").getAsDouble();
						double energy_budget = spawnCosts.getAsJsonObject("minecraft:skeleton").get("energy_budget").getAsDouble();
						spawnCosts.remove("minecraft:skeleton");
						JsonObject witherSkeleton = new JsonObject();
						witherSkeleton.addProperty("charge", charge);
						witherSkeleton.addProperty("energy_budget", energy_budget);
						spawnCosts.add("minecraft:wither_skeleton", witherSkeleton);
					}
					JsonArray monsters = context.getFile().getAsJsonObject().getAsJsonObject("spawners").getAsJsonArray("monster");
					int idToRemove = -1;
					JsonObject witherSkeleton = new JsonObject();
					for (int i = 0; i < monsters.size(); i++) {
						JsonObject monster = monsters.get(i).getAsJsonObject();
						if (monster.get("type").getAsString().equals("minecraft:skeleton")) {
							idToRemove = i;
							witherSkeleton.addProperty("type", "minecraft:wither_skeleton");
							witherSkeleton.addProperty("maxCount", monster.get("maxCount").getAsInt());
							witherSkeleton.addProperty("minCount", monster.get("minCount").getAsInt());
							witherSkeleton.addProperty("weight", monster.get("weight").getAsInt());
							break;
						}
					}
					if (idToRemove != -1) monsters.remove(idToRemove);
					if (!witherSkeleton.isEmpty()) monsters.add(witherSkeleton);
				}
		);
		if (Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInFortress.get()) MixsonHelper.registerSingleJsonPersistent(
				"Replace skeletons with wither skeletons in fortresses",
				new Index("minecraft:worldgen/structure/fortress"),
				context -> {
					JsonArray spawns = context.getFile().getAsJsonObject()
							.getAsJsonObject("spawn_overrides")
							.getAsJsonObject("monster")
							.getAsJsonArray("spawns");
					int idToRemove = -1;
					int idToUpdate = -1;
					int addWeight = 0;
					for (int i = 0; i < spawns.size(); i++) {
						JsonObject spawn = spawns.get(i).getAsJsonObject();
						if (spawn.get("type").getAsString().equals("minecraft:wither_skeleton")) idToUpdate = i;
						if (spawn.get("type").getAsString().equals("minecraft:skeleton")) {
							idToRemove = i;
							addWeight = spawn.get("weight").getAsInt();
						}
					}
					if (idToRemove != -1) spawns.remove(idToRemove);
					if (idToUpdate != -1) {
						JsonObject update = spawns.get(idToUpdate).getAsJsonObject();
						update.addProperty("weight", update.get("weight").getAsInt() + addWeight);
					}
				}
		);
	}
}
