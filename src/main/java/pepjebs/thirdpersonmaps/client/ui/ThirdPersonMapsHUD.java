package pepjebs.thirdpersonmaps.client.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsHUD extends DrawableHelper {

    private static final Identifier MAP_BKGND = new Identifier("minecraft:textures/map/map_background.png");
    private static final Identifier MAP_CHKRBRD = new Identifier("minecraft:textures/map/map_background_checkerboard.png");
    private static MinecraftClient client;
    private static MapRenderer mapRenderer;

    public ThirdPersonMapsHUD() {
        client = MinecraftClient.getInstance();
        mapRenderer = client.gameRenderer.getMapRenderer();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (shouldDraw(client)) {
            if (client.player.getMainHandStack().isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP))) {
                renderMapHUDFromItemStack(matrices, client.player.getMainHandStack(), false);
            }
            if (client.player.getOffHandStack().isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP))) {
                renderMapHUDFromItemStack(matrices, client.player.getOffHandStack(), true);
            }
        }
    }

    private boolean shouldDraw(MinecraftClient client) {
        return client.options.getPerspective() == Perspective.THIRD_PERSON_BACK && !client.options.debugEnabled;
    }

    private void renderMapHUDFromItemStack(MatrixStack matrices, ItemStack map, boolean isLeft) {
        if (client.world == null) return;
        // Draw map background
        MapState state = FilledMapItem.getMapState(map, client.world);
        int x = client.getWindow().getScaledWidth()-64;
        if (isLeft) {
            x = 0;
        }
        client.getTextureManager().bindTexture(MAP_CHKRBRD);
        drawTexture(matrices,x,0,0,0,64,64, 64, 64);

        // Draw map data
        x = client.getWindow().getScaledWidth()-60;
        if (isLeft) {
            x = 4;
        }
        VertexConsumerProvider.Immediate vcp = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        matrices.push();
        matrices.translate(x,4.0,0.0);
        matrices.scale(0.45f, 0.45f, 0);
        mapRenderer.draw(matrices, vcp, state, false, Integer.parseInt("0000000011110000", 2));
        vcp.draw();
        matrices.pop();
    }
}
