package ink.mhxk.msc.msc;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative by GoldMain on 2019/11/29
 */
public class ModReboundCheater {
    public int nu;
    public ModReboundCheater(){

    }
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer){
        nu++;
        if(nu<20)return;
        nu=0;
        World world = entityPlayer.world;
        BlockPos pos = new BlockPos(entityPlayer);
        while(pos.getY()>=0){
            if(world.getBlockState(pos).getMaterial()!= Material.AIR)return;
            pos = pos.down();
        }
        entityPlayer.motionY = 0.365F;
    }
}
