package pepjebs.thirdpersonmaps.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Client's doing it's thang");
    }
}
