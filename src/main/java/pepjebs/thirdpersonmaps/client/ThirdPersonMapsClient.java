package pepjebs.thirdpersonmaps.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // See:
        // https://github.com/gbl/DurabilityViewer/blob/fabric_1_16/src/main/java/de/guntram/mcmod/durabilityviewer/mixin/PotionEffectsMixin.java
    }
}
