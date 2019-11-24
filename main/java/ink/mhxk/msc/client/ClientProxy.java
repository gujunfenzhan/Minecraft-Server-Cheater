package ink.mhxk.msc.client;

import ink.mhxk.msc.common.CommonProxy;
import ink.mhxk.msc.init.ModCheaterHandle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ClientProxy
extends CommonProxy {
    @SubscribeEvent
    public void PlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer entityPlayer = event.player;
        //判断客户代理
        if (entityPlayer.world != null && event.side == Side.CLIENT) {
            //每次更新
            ModCheaterHandle.getInstance().update(entityPlayer);
        }
    }
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        String str = event.getMessage();
        if(str.substring(0,1).toCharArray()[0]=='#'&&str.length()>1){
            str = str.substring(1,str.length());
            ModCheaterHandle.getInstance().onChat(str);
            event.setCanceled(true);
        }
    }
}
