package ink.mhxk.msc.msc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModFlyCheater {
    public double flySpeed;
    public ModFlyCheater(double flySpeed){
        this.flySpeed = flySpeed;
    }
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer){
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            entityPlayer.motionY = flySpeed;
        }
    }
}
