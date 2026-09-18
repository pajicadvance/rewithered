package me.pajic.rewithered.mixson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.pajic.rewithered.Rewithered;
import net.ramixin.mixson.util.Index;

public class LootTablePatches {

	private static final JsonElement decayArrowPool = JsonParser.parseString(
            //? <26.3 {
            /*"""
            {
              "bonus_rolls": 0.0,
              "conditions": [
                {
                  "condition": "minecraft:killed_by_player"
                },
                {
                  "condition": "minecraft:any_of",
                  "terms": [
                    {
                      "condition": "minecraft:entity_properties",
                      "entity": "this",
                      "predicate": {
                        "equipment": {
                          "mainhand": {
                            "items": "minecraft:bow"
                          }
                        }
                      }
                    },
                    {
                      "condition": "minecraft:entity_properties",
                      "entity": "this",
                      "predicate": {
                        "equipment": {
                          "offhand": {
                            "items": "minecraft:bow"
                          }
                        }
                      }
                    }
                  ]
                }
              ],
              "entries": [
                {
                  "type": "minecraft:item",
                  "functions": [
                    {
                      "add": false,
                      "count": {
                        "type": "minecraft:uniform",
                        "max": 2.0,
                        "min": 0.0
                      },
                      "function": "minecraft:set_count"
                    },
                    {
                      "count": {
                        "type": "minecraft:uniform",
                        "max": 1.0,
                        "min": 0.0
                      },
                      "enchantment": "minecraft:looting",
                      "function": "minecraft:enchanted_count_increase",
                      "limit": 1
                    },
                    {
                      "function": "minecraft:set_potion",
                      "id": "rewithered:decay"
                    }
                  ],
                  "name": "minecraft:tipped_arrow"
                }
              ],
              "rolls": 1.0
            }
            """
            *///?} else {
            """
            {
              "rolls": 1,
              "bonus_rolls": 0,
              "entries": [
                {
                  "type": "minecraft:item",
                  "name": "minecraft:tipped_arrow",
                  "modifier": [
                    {
                      "type": "minecraft:set_count",
                      "count": {
                        "type": "minecraft:uniform",
                        "min": 0,
                        "max": 2
                      },
                      "add": false
                    },
                    {
                      "type": "minecraft:enchanted_count_increase",
                      "count": {
                        "type": "minecraft:uniform",
                        "min": 0,
                        "max": 1
                      },
                      "limit": 1,
                      "enchantment": "minecraft:looting"
                    },
                    {
                      "type": "minecraft:set_potion",
                      "id": "rewithered:decay"
                    }
                  ]
                }
              ],
              "condition": {
                "type": "minecraft:all_of",
                "terms": [
                  {
                    "type": "minecraft:killed_by_player"
                  },
                  {
                    "type": "minecraft:any_of",
                    "terms": [
                      {
                        "type": "minecraft:entity_properties",
                        "entity": "this",
                        "predicate": {
                          "minecraft:equipment": {
                            "mainhand": {
                              "items": "minecraft:bow"
                            }
                          }
                        }
                      },
                      {
                        "type": "minecraft:entity_properties",
                        "entity": "this",
                        "predicate": {
                          "minecraft:equipment": {
                            "offhand": {
                              "items": "minecraft:bow"
                            }
                          }
                        }
                      }
                    ]
                  }
                ]
              }
            }
            """
            //?}
    );

	public static void init() {
		if (Rewithered.CONFIG.witherSkeletonTweaks.dropWitherArrows.get()) MixsonHelper.registerSingleJson(
				"Add wither arrow drop to wither skeletons with bows",
				new Index("minecraft:loot_table/entities/wither_skeleton"),
				context ->
						context.getFile().getAsJsonObject()
								.getAsJsonArray("pools").add(decayArrowPool.deepCopy())
		);
	}
}
