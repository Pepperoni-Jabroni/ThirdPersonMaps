package pepjebs.thirdpersonmaps.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
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

import java.util.stream.StreamSupport;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsHUD extends DrawableHelper {

    private static final Identifier MAP_BKGND = new Identifier("minecraft:textures/map/map_background.png");
    private static final Identifier MAP_ICONS = new Identifier("minecraft:textures/map/map_icons.png");
    private static MinecraftClient client;
    private static MapRenderer mapRenderer;

    public ThirdPersonMapsHUD() {
        client = MinecraftClient.getInstance();
        mapRenderer = client.gameRenderer.getMapRenderer();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (shouldDraw(client)) {
            if (client.player.getMainHandStack().isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP))) {
                renderMapHUDFromItemStack(matrices, client.player.getMainHandStack(), client.getWindow().getScaledWidth()-64);
            }
            if (client.player.getOffHandStack().isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP))) {
                renderMapHUDFromItemStack(matrices, client.player.getOffHandStack(), 0);
            }
        }
    }

    private boolean shouldDraw(MinecraftClient client) {
        return client.options.getPerspective() == Perspective.THIRD_PERSON_BACK && !client.options.debugEnabled;
    }

    private void renderMapHUDFromItemStack(MatrixStack matrices, ItemStack map, int x) {
        // Draw map background
        matrices.push();
        client.getTextureManager().bindTexture(MAP_BKGND);
        matrices.pop();
        drawTexture(matrices,x,0,0,0,64,64, 64, 64);
        // Draw map data
        MapState state = FilledMapItem.getMapState(map, client.world);
        VertexConsumerProvider vertices = client.getBufferBuilders().getEntityVertexConsumers();
        matrices.push();
        matrices.scale(0,0,0);
        mapRenderer.draw(matrices, vertices, state, false, 13);
        matrices.pop();
        drawTexture(matrices,x + 4,4,0,0,56,56, 64, 64);
    }
}
