package roadhog.customskygrid.world.chunk;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

public class SkyGridChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<SkyGridChunkGenerator> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source")
                    .forGetter((generator) -> generator.biomeSource), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings")
                    .forGetter((generator) -> generator.settings)).apply(instance, instance.stable(SkyGridChunkGenerator::new)));

    public SkyGridChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
    }
    public int getSpawnHeight(HeightLimitView world) {
        return 64;
    }

    private OptionalInt sampleHeightmap(HeightLimitView world, NoiseConfig noiseConfig, int x, int z, @Nullable MutableObject<VerticalBlockSample> columnSample, @Nullable Predicate<BlockState> stopPredicate) {
        return OptionalInt.of(getSpawnHeight(world));
    }

    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {
    }

    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        for (int x = 0; x < 4; x++) {
            for (int z = 0; z < 4; z++) {
                for (int y = -16; y <= 16; y++) {
                    BlockPos pos = new BlockPos(
                            chunk.getPos().getStartPos().getX() + (x * 4),
                            y * 4,
                            chunk.getPos().getStartPos().getZ() + (z * 4));
                    chunk.setBlockState(pos,
                            y == 16 ? Blocks.GRASS_BLOCK.getDefaultState() : Blocks.STONE.getDefaultState(),
                            false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }
}
