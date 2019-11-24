package ink.mhxk.msc.msc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModSuperRunCheater {
    public double superRunSpeed;
    public ModSuperRunCheater(double superRunSpeed){
        this.superRunSpeed = superRunSpeed;
    }
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer){
        if((entityPlayer.motionX<superRunSpeed&&entityPlayer.motionX>-superRunSpeed)&&(entityPlayer.motionZ<superRunSpeed&&entityPlayer.motionZ>-superRunSpeed)){
            entityPlayer.motionX = entityPlayer.motionX*2;
            entityPlayer.motionZ = entityPlayer.motionZ*2;
        }
    }
}
