package ink.mhxk.msc;

import ink.mhxk.msc.common.CommonProxy;
import ink.mhxk.msc.init.ModKeyLoader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Creative by GoldMain on 2019/11/24
 */
@Mod(name = ModMSCMain.MODNAME,modid = ModMSCMain.MODID,version = ModMSCMain.MODVERSION)
public class ModMSCMain {
    @SidedProxy(serverSide = "ink.mhxk.msc.common.CommonProxy",clientSide = "ink.mhxk.msc.client.ClientProxy")
    public static CommonProxy proxy;
    public static final String MODID = "msc";
    public static final String MODNAME = "MinecraftServerCheater";
    public static final String MODVERSION = "1.0.0";
    @Mod.Instance
    public static ModMSCMain INSTANCE;
    @Mod.EventHandler
    public void pre(FMLPreInitializationEvent event){
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(proxy);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        new ModKeyLoader();
    }
    @Mod.EventHandler
    public void post(FMLPostInitializationEvent event){

    }
}
