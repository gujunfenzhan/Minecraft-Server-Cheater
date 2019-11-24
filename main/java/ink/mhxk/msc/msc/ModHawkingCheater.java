package ink.mhxk.msc.msc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModHawkingCheater {
    public final int interval;
    public int nu;
    public ModHawkingCheater(int interval){
        this.interval = interval;
    }
    @SideOnly(Side.CLIENT)
    public void update(String str){
        nu++;
        if(nu>=interval)nu=0;
        FMLClientHandler.instance().getClientPlayerEntity().sendChatMessage(str);
    }

}
