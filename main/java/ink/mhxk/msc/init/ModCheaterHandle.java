package ink.mhxk.msc.init;

import ink.mhxk.msc.msc.*;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;
import java.util.List;

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
    public ModSprayerCheater sprayerCheater = new ModSprayerCheater(ModConfigLoader.SPRAYER_INTERVAL);
    public ModReboundCheater reboundCheater = new ModReboundCheater();
    public ModOreTrackingCheater oreTrackingCheater = new ModOreTrackingCheater();
    //客户端玩家实例
    public EntityPlayerSP entityPlayerSP;
    private boolean fly = false;
    private boolean superRun = false;
    private boolean longHand = false;
    public boolean hawking = false;
    public boolean sprayer = false;
    public boolean rebound = false;
    public List<Block> blocks = new ArrayList<Block>();
    public int blockIndex = -1;
    private ModCheaterHandle(){
        entityPlayerSP = FMLClientHandler.instance().getClientPlayerEntity();
        initBlockList();
    }
    public void initBlockList(){
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.DIAMOND_ORE);
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
        if(ModKeyLoader.SPRAYER.isPressed()){
            sprayer = !sprayer;
            String str = sprayer?"key.sprayer.true":"key.sprayer.false";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }
        if(ModKeyLoader.REBOUND.isPressed()){
            rebound = !rebound;
            String str = rebound?"key.rebound.true":"key.rebound.false";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }
        if(ModKeyLoader.ORE_TRACKING.isPressed()){
            blockIndex++;
            if(blockIndex>=blocks.size())blockIndex=-1;
            String str = blockIndex==-1?"key.oreTracking.false":blocks.get(blockIndex).getUnlocalizedName()+".name";
            entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
        }

        if(fly)flyCheater.update(entityPlayer);
        if(superRun)superRunCheater.update(entityPlayer);
        if(longHand)longHandCheater.update(entityPlayer);
        if(hawking)hawkingCheater.update(chat);
        if(sprayer)sprayerCheater.update();
        if(rebound)reboundCheater.update(entityPlayer);
        if(blockIndex!=-1)oreTrackingCheater.update(entityPlayer,blocks.get(blockIndex));
    }
    public void onChat(String chat){
        this.chat = chat;
        String str = "key.hawking.success";
        entityPlayerSP.sendMessage(new TextComponentString(I18n.format(str)));
    }
    public static ModCheaterHandle getInstance(){
        if(INSTANCE == null)INSTANCE = new ModCheaterHandle();
        return INSTANCE;
    }
}
