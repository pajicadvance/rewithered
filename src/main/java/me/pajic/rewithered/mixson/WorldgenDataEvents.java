package me.pajic.rewithered.mixson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.pajic.rewithered.Rewithered;
import net.ramixin.mixson.inline.Mixson;

public class WorldgenDataEvents {

	private static boolean initialized = false;

    public static void register() {
		if (initialized) return;
        if (Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInSoulSandValley.get()) Mixson.registerEvent(
                Mixson.DEFAULT_PRIORITY,
                rl -> rl.toString().equals("minecraft:worldgen/biome/soul_sand_valley"),
                "Replace skeletons with wither skeletons in soul sand valleys",
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
                },
                true
        );
        if (Rewithered.CONFIG.witherSkeletonTweaks.replaceSkeletonsInFortress.get()) Mixson.registerEvent(
                Mixson.DEFAULT_PRIORITY,
                rl -> rl.toString().equals("minecraft:worldgen/structure/fortress"),
                "Replace skeletons with wither skeletons in fortresses",
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
                },
                true
        );
		initialized = true;
    }
}
