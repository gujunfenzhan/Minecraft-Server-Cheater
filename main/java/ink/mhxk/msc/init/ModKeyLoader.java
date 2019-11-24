package ink.mhxk.msc.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModKeyLoader {
    public static KeyBinding FLY;
    public ModKeyLoader(){
        FLY = new KeyBinding("key.msc.fly", Keyboard.KEY_F,"key.categories.msc");
        ClientRegistry.registerKeyBinding(FLY);
    }
}
