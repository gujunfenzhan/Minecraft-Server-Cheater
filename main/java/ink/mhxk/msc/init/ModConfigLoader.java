package ink.mhxk.msc.init;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModConfigLoader {
    private static Configuration configuration;
    public static double FLY_SPEED;
    public static double SUPER_RUN_SPEED;
    public static double FLASH_DISTANCE;
    public static double HAND_DISTANCE;
    public static int HAWKING_INTERVAL;
    public ModConfigLoader(FMLPreInitializationEvent event){
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
        load();
    }
    public static void load(){
        FLY_SPEED = configuration.get(Configuration.CATEGORY_GENERAL,"flySpeed",1.0D,I18n.format("config.msc.flySpeed")).getDouble();
        SUPER_RUN_SPEED = configuration.get(Configuration.CATEGORY_GENERAL,"superRunSpeed",0.5D,I18n.format("config.msc.superRunSpeed")).getDouble();
        FLASH_DISTANCE = configuration.get(Configuration.CATEGORY_GENERAL,"flashDistance",4.0D,I18n.format("config.msc.flashDistance")).getDouble();
        HAND_DISTANCE = configuration.get(Configuration.CATEGORY_GENERAL,"handDistance",30.0D,I18n.format("config.msc.handDistance")).getDouble();
        HAWKING_INTERVAL = configuration.get(Configuration.CATEGORY_GENERAL,"hawkingInterval",40,I18n.format("config.msc.hawkingInterval")).getInt();
        configuration.save();
    }
}
