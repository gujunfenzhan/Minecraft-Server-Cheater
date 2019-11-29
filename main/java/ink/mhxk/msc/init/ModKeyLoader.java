package ink.mhxk.msc.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModKeyLoader {
    public static KeyBinding FLY;
    public static KeyBinding SUPER_RUN;
    public static KeyBinding FLASH;
    public static KeyBinding LONG_HAND;
    public static KeyBinding HAWKING;
    public static KeyBinding SPRAYER;
    public static KeyBinding REBOUND;
    public static KeyBinding ORE_TRACKING;
    public ModKeyLoader(){
        FLY = new KeyBinding("key.msc.fly", Keyboard.KEY_K,"key.categories.msc");
        SUPER_RUN = new KeyBinding("key.msc.superRun",Keyboard.KEY_O,"key.categories.msc");
        FLASH = new KeyBinding("key.msc.flash",Keyboard.KEY_R,"key.categories.msc");
        LONG_HAND = new KeyBinding("key.msc.longHand",Keyboard.KEY_C,"key.categories.msc");
        HAWKING = new KeyBinding("key.msc.hawking",Keyboard.KEY_U,"key.categories.msc");
        SPRAYER = new KeyBinding("key.msc.sprayer",Keyboard.KEY_P,"key.categories.msc");
        REBOUND = new KeyBinding("key.msc.rebound",Keyboard.KEY_M,"key.categories.msc");
        ORE_TRACKING = new KeyBinding("key.msc.oreTracking",Keyboard.KEY_N,"key.categories.msc");
        ClientRegistry.registerKeyBinding(FLY);
        ClientRegistry.registerKeyBinding(SUPER_RUN);
        ClientRegistry.registerKeyBinding(FLASH);
        ClientRegistry.registerKeyBinding(LONG_HAND);
        ClientRegistry.registerKeyBinding(HAWKING);
        ClientRegistry.registerKeyBinding(SPRAYER);
        ClientRegistry.registerKeyBinding(REBOUND);
        ClientRegistry.registerKeyBinding(ORE_TRACKING);
    }
}
