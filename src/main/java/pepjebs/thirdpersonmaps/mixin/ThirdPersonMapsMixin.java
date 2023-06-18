package pepjebs.thirdpersonmaps.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepjebs.thirdpersonmaps.client.ui.ThirdPersonMapsHUD;

@Mixin(InGameHud.class)
public class ThirdPersonMapsMixin {
    private static final ThirdPersonMapsHUD mapHUD = new ThirdPersonMapsHUD();

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void renderThirdPersonMap(DrawContext context, float tickDelta, CallbackInfo ci) {
        mapHUD.render(context);
    }
}
