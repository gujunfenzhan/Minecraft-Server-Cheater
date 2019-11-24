package ink.mhxk.msc.msc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModFlashCheater {
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer,double distance){
        float f = MathHelper.sin(entityPlayer.rotationYaw * 0.017453292F)*4;
        float f1 = MathHelper.cos(entityPlayer.rotationYaw * 0.017453292F)*4;
        World world = entityPlayer.getEntityWorld();
        int up = 0;
        for(int a = 0;a<256;a++){
            if(world.getBlockState(new BlockPos(entityPlayer.posX-f,entityPlayer.posY+a,entityPlayer.posZ+f1)).getBlock()== Blocks.AIR){
                up = a;
                break;
            }
        }
        entityPlayer.setPositionAndUpdate(entityPlayer.posX-f,entityPlayer.posY+up,entityPlayer.posZ+f1);
    }

}
