package pepjebs.thirdpersonmaps.client;


import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import pepjebs.thirdpersonmaps.config.ThirdPersonMapsConfig;

public class ThirdPersonMapsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ThirdPersonMapsConfig.class, JanksonConfigSerializer::new);
    }
}
