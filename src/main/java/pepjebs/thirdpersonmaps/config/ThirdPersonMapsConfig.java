package pepjebs.thirdpersonmaps.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "third_person_maps")
public class ThirdPersonMapsConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip()
    @Comment("Scale the mini-map to a given % of the height of your screen.")
    public int forceMapScaling = 30;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Enter an integer which will offset the mini-map horizontally")
    public int mapHorizontalOffsetRightHand = 0;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Enter an integer which will offset the mini-map vertically")
    public int mapVerticalOffsetRightHand = 0;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Enter an integer which will offset the mini-map horizontally")
    public int mapHorizontalOffsetLeftHand = 0;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Enter an integer which will offset the mini-map vertically")
    public int mapVerticalOffsetLeftHand = 0;
}
