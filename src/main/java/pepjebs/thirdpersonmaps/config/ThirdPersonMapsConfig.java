package pepjebs.thirdpersonmaps.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "third_person_maps")
public class ThirdPersonMapsConfig implements ConfigData {

    @Comment("Scale the map to a given pixel size. (Default is 64)")
    public int forceMapScaling = 64;
}
