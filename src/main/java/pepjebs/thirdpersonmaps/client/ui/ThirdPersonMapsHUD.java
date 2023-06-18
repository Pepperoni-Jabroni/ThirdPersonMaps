package pepjebs.thirdpersonmaps.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import pepjebs.thirdpersonmaps.config.ThirdPersonMapsConfig;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsHUD {

    private static final Identifier MAP_CHKRBRD =
            new Identifier("minecraft:textures/map/map_background.png");
    private static MinecraftClient client;
    private static MapRenderer mapRenderer;

    public ThirdPersonMapsHUD() {
        client = MinecraftClient.getInstance();
        mapRenderer = client.gameRenderer.getMapRenderer();
    }

    public void render(DrawContext context) {
        if (shouldDraw(client)) {
            boolean isPlayerLeftHanded = client.player.getMainArm() == Arm.LEFT;
            if (client.player.getMainHandStack().isOf(Items.FILLED_MAP)) {
                renderMapHUDFromItemStack(context, client.player.getMainHandStack(), isPlayerLeftHanded);
            }
            if (client.player.getOffHandStack().isOf(Items.FILLED_MAP)) {
                renderMapHUDFromItemStack(context, client.player.getOffHandStack(), !isPlayerLeftHanded);
            }
        }
    }

    private boolean shouldDraw(MinecraftClient client) {
        if (client.player == null) return false;
        return client.options.getPerspective() != Perspective.FIRST_PERSON && !client.options.debugEnabled;
    }

    private void renderMapHUDFromItemStack(DrawContext context, ItemStack map, boolean isLeft) {
        var matrices = context.getMatrices();
        if (client.world == null || client.player == null) return;
        if (map.getNbt() == null || !map.getNbt().contains("map")) return;
        MapState state = FilledMapItem.getMapState(map.getNbt().getInt("map"), client.world);
        if (state == null) return;
        ThirdPersonMapsConfig conf = AutoConfig.getConfigHolder(ThirdPersonMapsConfig.class).getConfig();

        // Draw map background
        int mapScaling = (int) Math.floor(conf.forceMapScaling / 100.0 * client.getWindow().getScaledHeight());
        String anchorLocation = "UpperRight";
        if (isLeft) {
            anchorLocation = "UpperLeft";
        }
        int x = anchorLocation.contains("Left") ? 0 : client.getWindow().getScaledWidth()-mapScaling;
        int y = 0;
        if (isLeft) {
            x += conf.mapHorizontalOffsetLeftHand;
            y += conf.mapVerticalOffsetLeftHand;
        } else {
            x += conf.mapHorizontalOffsetRightHand;
            y += conf.mapVerticalOffsetRightHand;
        }
        if (anchorLocation.contentEquals("UpperRight")) {
            boolean hasBeneficial =
                    client.player.getStatusEffects().stream().anyMatch(p -> p.getEffectType().isBeneficial());
            boolean hasNegative =
                    client.player.getStatusEffects().stream().anyMatch(p -> !p.getEffectType().isBeneficial());

            if (hasNegative && y < 52) {
                y += (52 - y);
            } else if (hasBeneficial && y < 26) {
                y += (26 - y);
            }
        }
        RenderSystem.setShaderTexture(0, MAP_CHKRBRD);
        context.drawTexture(MAP_CHKRBRD,x,y,0,0,mapScaling,mapScaling, mapScaling, mapScaling);

        // Draw map data
        x += (mapScaling / 16) - (mapScaling / 64);
        y += (mapScaling / 16) - (mapScaling / 64);
        VertexConsumerProvider.Immediate vcp;
        vcp = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        matrices.push();
        matrices.translate(x, y, 0.0);
        // Prepare yourself for some magic numbers
        matrices.scale((float) mapScaling / 142, (float) mapScaling / 142, -1);
        mapRenderer.draw(
                matrices,
                vcp,
                map.getNbt().getInt("map"),
                state,
                false,
                Integer.parseInt("F000F0", 16)
        );
        vcp.draw();
        matrices.pop();
    }
}
