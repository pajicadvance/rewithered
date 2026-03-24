package me.pajic.rewithered.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.List;
import java.util.function.Consumer;

public class ModUtil {

	/**
	 * Code from <a href="https://github.com/TelepathicGrunt/Bumblezone/blob/5d606204d0a68d236f2d31923fdd3286c051060a/common/src/main/java/com/telepathicgrunt/the_bumblezone/utils/GeneralUtils.java#L876-L917">...</a>
	 * Related issue: <a href="https://github.com/pajicadvance/rewithered/issues/6">...</a>
	 */

	public static StructureStart getStructureAt(LevelReader level, StructureManager structureManager, BlockPos blockPos, Structure structure) {
		for(StructureStart structureStart : startsForStructure(level, structureManager, SectionPos.of(blockPos), structure)) {
			if (structureStart.getBoundingBox().isInside(blockPos)) {
				return structureStart;
			}
		}

		return StructureStart.INVALID_START;
	}

	@SuppressWarnings("deprecation")
	private static List<StructureStart> startsForStructure(LevelReader level, StructureManager structureManager, SectionPos sectionPos, Structure structure) {
		if (level.hasChunk(sectionPos.x(), sectionPos.z())) {
			ChunkAccess chunkAccess = level.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.STRUCTURE_REFERENCES);
			LongSet references = chunkAccess.getReferencesForStructure(structure);
			ImmutableList.Builder<StructureStart> builder = ImmutableList.builder();
			fillStartsForStructure(level, structureManager, structure, references, builder::add);
			return builder.build();
		}
		return List.of();
	}

	@SuppressWarnings("deprecation")
	private static void fillStartsForStructure(LevelReader level, StructureManager structureManager, Structure structure, LongSet references, Consumer<StructureStart> consumer) {
		for (long ref : references) {
			SectionPos sectionPos = SectionPos.of(ChunkPos.unpack(ref), level.getMinSectionY());
			if (!level.hasChunk(sectionPos.x(), sectionPos.z())) {
				continue;
			}
			StructureStart structureStart = structureManager.getStartForStructure(sectionPos, structure, level.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.STRUCTURE_STARTS));
			if (structureStart != null && structureStart.isValid()) {
				consumer.accept(structureStart);
			}
		}
	}
}
