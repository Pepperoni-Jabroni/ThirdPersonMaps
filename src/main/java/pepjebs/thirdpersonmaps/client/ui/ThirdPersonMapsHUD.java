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
                ItemStack map = client.player.getMainHandStack();
                client.getTextureManager().bindTexture(MAP_BKGND);
                drawTexture(matrices,client.getWindow().getScaledWidth()-64,0,0,0,64,64, 64, 64);
                MapState state = FilledMapItem.getMapState(map, client.world);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(0.0D, client.getWindow().getScaledHeight(), -90.0D).texture(0.0F, 1.0F).next();
                bufferBuilder.vertex(client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), -90.0D).texture(1.0F, 1.0F).next();
                bufferBuilder.vertex(client.getWindow().getScaledWidth(), 0.0D, -90.0D).texture(1.0F, 0.0F).next();
                bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
                tessellator.draw();
                mapRenderer.draw(matrices, VertexConsumerProvider.immediate(tessellator.getBuffer()), state, true, 16);
            }
            if (client.player.getOffHandStack().isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP))) {
                // TODO: Copy
            }
        }
    }

    private boolean shouldDraw(MinecraftClient client) {
        return client.options.getPerspective() == Perspective.THIRD_PERSON_BACK && !client.options.debugEnabled;
    }
}
