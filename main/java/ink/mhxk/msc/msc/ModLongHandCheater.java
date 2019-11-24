package ink.mhxk.msc.msc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Creative by GoldMain on 2019/11/24
 */
public class ModLongHandCheater {
    public double handDistance;
    public ModLongHandCheater(double handDistance){
        this.handDistance = handDistance;
    }
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer entityPlayer){
        World world = entityPlayer.getEntityWorld();
        List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, entityPlayer.getEntityBoundingBox().grow(handDistance, handDistance, handDistance));
        for (Entity entity : entityList) {
            if(entity instanceof EntityLiving)
                FMLClientHandler.instance().getClient().playerController.attackEntity(entityPlayer,entity);
        }
    }
}
