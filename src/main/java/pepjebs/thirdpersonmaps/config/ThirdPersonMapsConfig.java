package pepjebs.thirdpersonmaps.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "third_person_maps")
public class ThirdPersonMapsConfig implements ConfigData {

    @Comment("Scale the map to a given pixel size. (Default is 64)")
    public int forceMapScaling = 64;
}
