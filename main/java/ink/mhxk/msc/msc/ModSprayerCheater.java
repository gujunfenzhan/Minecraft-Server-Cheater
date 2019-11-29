package ink.mhxk.msc.msc;

import ink.mhxk.msc.init.ModSentenceLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Creative by GoldMain on 2019/11/29
 */
public class ModSprayerCheater {
    public Random random = new Random();
    public final int interval;
    public int nu;
    public ModSprayerCheater(int interval){
        this.interval = interval;
    }
    @SideOnly(Side.CLIENT)
    public void update(){
        nu++;
        List<String> list = ModSentenceLoader.sentences;
        if(nu>=interval&&list.size()>0) {
            nu = 0;
            FMLClientHandler.instance().getClientPlayerEntity().sendChatMessage(list.get(random.nextInt(list.size())));
        }
    }
}
