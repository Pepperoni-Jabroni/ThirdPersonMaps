package pepjebs.thirdpersonmaps.client.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.stream.StreamSupport;

@Environment(EnvType.CLIENT)
public class ThirdPersonMapsHUD implements Drawable {

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (StreamSupport.stream(client.player.getItemsHand().spliterator(), false)
                .anyMatch(itemStack -> itemStack.isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP)))) {
            ItemStack map = StreamSupport.stream(client.player.getItemsHand().spliterator(), false)
                    .filter(itemStack -> itemStack.isItemEqualIgnoreDamage(new ItemStack(Items.FILLED_MAP)))
                    .findFirst()
                    .get();
            System.out.println(FilledMapItem.getMapId(map));
        }
    }
}
