package ink.mhxk.msc.msc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative by GoldMain on 2019/11/29
 */
public class ModOreTrackingCheater {
    public int nu;
    public BlockPos pos;
    public ModOreTrackingCheater() {
    }
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer, Block block) {
        World world = entityPlayer.world;
        if (pos != null && entityPlayer.world.getBlockState(pos).getBlock() == block) {
            renderOreAndUpdate(entityPlayer);
        } else if (pos == null || world.getBlockState(pos).getBlock() != block) {
            nu++;
            if (nu < 20) return;
            nu = 0;
            pos = findOre(entityPlayer, block);
        }
    }
    public void renderOreAndUpdate(EntityPlayer player) {
        double x = pos.getX() - player.posX+0.5;
        double y = pos.getY() - player.posY-0.5;
        double z = pos.getZ() - player.posZ+0.5;
        while (true) {
            if (Math.abs(x) > 1 || Math.abs(y) > 1 || Math.abs(z) > 1) {
                x = x / 2;
                y = y / 2;
                z = z / 2;
            } else {
                break;
            }
        }
        player.world.spawnParticle(EnumParticleTypes.FLAME, player.posX + x, player.posY + 1.5F + y, player.posZ + z, 0.0F, 0.0F, 0.0F);
    }
    public BlockPos findOre(EntityPlayer player, Block block) {
        World world = player.world;
        BlockPos neverPos = null;
        BlockPos ppos = new BlockPos(player);
        int len = Integer.MAX_VALUE;
        for (int x = -100; x < 100; x++) {
            for (int y = -100; y < 100; y++) {
                for (int z = -100; z < 100; z++) {
                    BlockPos npos = new BlockPos(ppos.getX()+x,ppos.getY()+y,ppos.getZ()+z);
                    if (world.getBlockState(npos).getBlock() == block) {
                        int nlen = getQ(ppos, npos);
                        if (nlen < len) {
                            len = nlen;
                            neverPos = npos;
                        }
                    }
                }
            }
        }
        return neverPos;
    }
    public int getQ(BlockPos pos1,BlockPos pos2){
        int x = Math.abs(pos1.getX())-Math.abs(pos2.getX());
        int y = Math.abs(pos1.getY())-Math.abs(pos2.getY());
        int z = Math.abs(pos1.getZ())-Math.abs(pos2.getZ());
        return x*x+y*y+z*z;
    }
}