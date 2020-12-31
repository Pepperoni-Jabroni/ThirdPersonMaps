package pepjebs.thirdpersonmaps.client;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import pepjebs.thirdpersonmaps.config.ThirdPersonMapsConfig;

public class ThirdPersonMapsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ThirdPersonMapsConfig.class, JanksonConfigSerializer::new);
    }
}
