package ink.mhxk.msc.init;

import ink.mhxk.msc.msc.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * Creative by GoldMain on 2019/11/24
 */

public class ModCheaterHandle {
    private static ModCheaterHandle INSTANCE;
    public String chat;
    public ModFlyCheater flyCheater = new ModFlyCheater(ModConfigLoader.FLY_SPEED);
    public ModSuperRunCheater superRunCheater = new ModSuperRunCheater(ModConfigLoader.SUPER_RUN_SPEED);
    public ModFlashCheater flashCheater = new ModFlashCheater();
    public ModLongHandCheater longHandCheater = new ModLongHandCheater(ModConfigLoader.HAND_DISTANCE);
    public ModHawkingCheater hawkingCheater = new ModHawkingCheater(ModConfigLoader.HAWKING_INTERVAL);
    //客户端玩家实例
    public EntityPlayerSP entityPlayerSP;
    private boolean fly = false;
    private boolean superRun = false;
    private boolean longHand = false;
    public boolean hawking = false;
    private ModCheaterHandle(){
        entityPlayerSP = FMLClientHandler.instance().getClientPlayerEntity();
    }
    public void update(EntityPlayer entityPlayer){
        if(ModKeyLoader.FLY.isPressed()){
            fly = !fly;
            String str = fly?"key.fly.true":"key.fly.false";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }
        if(ModKeyLoader.SUPER_RUN.isPressed()){
            superRun = !superRun;
            String str = superRun?"key.superRun.true":"key.superRun.false";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }
        if(ModKeyLoader.FLASH.isPressed()){
            flashCheater.update(entityPlayer,ModConfigLoader.FLASH_DISTANCE);
        }
        if(ModKeyLoader.LONG_HAND.isPressed()){
            longHand = !longHand;
            String str = longHand?"key.longHand.true":"key.longHand.false";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }
        if(ModKeyLoader.HAWKING.isPressed()){
            if(chat!=null){
                hawking = !hawking;
                String str = hawking?"key.hawking.true":"key.hawking.false";
                entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
            }else{
                entityPlayerSP.sendMessage(new TextComponentString(I18n.format("key.hawking.null")));
            }
        }
        if(fly)flyCheater.update(entityPlayer);
        if(superRun)superRunCheater.update(entityPlayer);
        if(longHand)longHandCheater.update(entityPlayer);
        if(hawking)hawkingCheater.update(chat);
    }
    public void onChat(String chat){
        this.chat = chat;
    }
    public static ModCheaterHandle getInstance(){
        if(INSTANCE == null)INSTANCE = new ModCheaterHandle();
        return INSTANCE;
    }
}
