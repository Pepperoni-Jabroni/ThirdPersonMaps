package pepjebs.thirdpersonmaps.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Identifier;
import pepjebs.thirdpersonmaps.config.ThirdPersonMapsConfig;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsHUD extends DrawableHelper {

    private static final Identifier MAP_CHKRBRD =
            new Identifier("minecraft:textures/map/map_background.png");
    private static MinecraftClient client;
    private static MapRenderer mapRenderer;

    public ThirdPersonMapsHUD() {
        client = MinecraftClient.getInstance();
        mapRenderer = client.gameRenderer.getMapRenderer();
    }

    public void render(MatrixStack matrices) {
        if (shouldDraw(client)) {
            if (client.player == null) return;
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
        ThirdPersonMapsConfig conf = AutoConfig.getConfigHolder(ThirdPersonMapsConfig.class).getConfig();

        // Draw map background
        int y = 0;
        if (!isLeft) {
            // Handle potion effects on right-hand side of screen
            if (client.player == null || !client.player.getStatusEffects().isEmpty()) {
                y = 26;
            }
        }
        int x = client.getWindow().getScaledWidth()-conf.forceMapScaling;
        if (isLeft) {
            x = 0;
        }
        RenderSystem.setShaderTexture(0, ThirdPersonMapsHUD.MAP_CHKRBRD);
        drawTexture(matrices,x,y,0,0, conf.forceMapScaling,
                conf.forceMapScaling, conf.forceMapScaling, conf.forceMapScaling);

        // Draw map data
        x += (int)(4.0 * (conf.forceMapScaling / 64.0));
        y += (int)(4.0 * (conf.forceMapScaling / 64.0));
        VertexConsumerProvider.Immediate vcp = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        matrices.push();
        matrices.translate(x, y, 0.0);
        // Prepare yourself for some magic numbers
        matrices.scale((float) conf.forceMapScaling / 142, (float) conf.forceMapScaling / 142, 0);
        if (map.getNbt() == null || !map.getNbt().contains("map")) return;
        MapState state = FilledMapItem.getMapState(map.getNbt().getInt("map"), client.world);
        mapRenderer.draw(
                matrices, vcp, map.getNbt().getInt("map"), state,
                false, Integer.parseInt("0000000011110000", 2)
        );
        vcp.draw();
        matrices.pop();
    }
}
