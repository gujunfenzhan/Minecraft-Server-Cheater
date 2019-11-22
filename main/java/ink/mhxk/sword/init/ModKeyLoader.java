package ink.mhxk.sword.init;

import ca.weblite.objc.Client;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Creative by GoldMain on 2019/11/16
 */
public class ModKeyLoader {
    public static KeyBinding fly;
    public static KeyBinding flash;
    public static KeyBinding superRun;
    public static KeyBinding longHand;
    public static KeyBinding FP;
    public static KeyBinding trackingArrow;
    public static KeyBinding mining;
    public ModKeyLoader(){
        fly = new KeyBinding("key.bigsword.fly", Keyboard.KEY_SPACE,"key.categories.bigsword");
        flash = new KeyBinding("key.bigsword.flash",Keyboard.KEY_R,"key.categories.bigsword");
        superRun = new KeyBinding("key.bigsword.superRun",Keyboard.KEY_LSHIFT,"key.categories.bigsword");
        longHand = new KeyBinding("key.bigsword.longHand",Keyboard.KEY_C,"key.categories.bigsword");
        FP = new KeyBinding("key.bigsword.FP",Keyboard.KEY_P,"key.categories.bigsword");
        trackingArrow = new KeyBinding("key.bigsword.trackingArrow",Keyboard.KEY_K,"key.categories.bigsword");
        mining = new KeyBinding("key.bigsword.mining",Keyboard.KEY_F12,"key.categories.bigsword");
        ClientRegistry.registerKeyBinding(fly);
        ClientRegistry.registerKeyBinding(flash);
        ClientRegistry.registerKeyBinding(superRun);
        ClientRegistry.registerKeyBinding(longHand);
        ClientRegistry.registerKeyBinding(FP);
        ClientRegistry.registerKeyBinding(trackingArrow);
        ClientRegistry.registerKeyBinding(mining);
    }
}
