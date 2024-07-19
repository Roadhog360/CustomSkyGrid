package roadhog.customskygrid;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import roadhog.customskygrid.world.chunk.SkyGridChunkGenerator;

public class CustomSkyGrid implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registries.CHUNK_GENERATOR, "customskygrid:skygrid", SkyGridChunkGenerator.CODEC);
    }
}
