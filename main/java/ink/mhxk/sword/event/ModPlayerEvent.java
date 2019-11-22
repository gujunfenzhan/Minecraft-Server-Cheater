package ink.mhxk.sword.event;
import ink.mhxk.sword.ModBigSwordMain;
import ink.mhxk.sword.client.render.RenderBigSword;
import ink.mhxk.sword.init.ModConfigLoader;
import ink.mhxk.sword.init.ModItemLoader;
import ink.mhxk.sword.init.ModKeyLoader;
import ink.mhxk.sword.init.ModSentenceLoader;
import ink.mhxk.sword.utils.obj.WavefrontObject;
import io.netty.buffer.Unpooled;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import java.util.List;
import java.util.Random;
public class ModPlayerEvent {
    /*
    ģ��Դ��ַ:https://www.cgmodel.com/model-121184.html
     */
    public static int fpNumber = 0;
    public static WavefrontObject obj =  new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/rotate_shield.obj"));;
    public static ResourceLocation TEXTURE = new ResourceLocation(ModBigSwordMain.MODID, "textures/entity/rotate_shield.png");
    public static int chatTime = 0;
    public static String chatLore;
    public static Random rand = new Random();
    public static boolean FP = false;
    public static boolean Mining = false;
    public static BlockPos OrePos = null;
    public static PathNavigate pathNavigate;
    @SubscribeEvent
    public void onAttack(TickEvent.PlayerTickEvent event){

        EntityPlayer entityPlayer = event.player;

        InventoryPlayer inventory = entityPlayer.inventory;
        ItemStack boots = inventory.getStackInSlot(36);
        fly(entityPlayer);
        flash(entityPlayer);
        superRun(entityPlayer);
        longHand(entityPlayer);
        FP(entityPlayer);
        //Mining(entityPlayer);������̫����
        //findOre(entityPlayer);
        if(chatTime>0&&System.currentTimeMillis()%(chatTime+1)==chatTime&&entityPlayer.world.isRemote){
            FMLClientHandler clientHandler = FMLClientHandler.instance();
            if(clientHandler!=null){
                EntityPlayerSP playerSP= clientHandler.getClientPlayerEntity();
                if(playerSP!=null)playerSP.sendChatMessage(chatLore);
            }
        }
        if(boots.getItem()== ModItemLoader.ARMOR_FEET){
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)&&entityPlayer.motionY<0.1){
                entityPlayer.motionY=0.3F;
            }
        }
        if(event.side==Side.SERVER) {
            /*
                39:ͷ��
                38:�·�
                37:����
                36:ѥ��
             */

            if (entityPlayer.isServerWorld() && entityPlayer.isHandActive()) {
                ItemStack stack = entityPlayer.getActiveItemStack();
                if (stack.getItem() != Items.SHIELD) return;

                dBlock(entityPlayer);
                AxisAlignedBB AABB = entityPlayer.getEntityBoundingBox().grow(3.0F);
                List<Entity> entityList = entityPlayer.world.getEntitiesWithinAABB(Entity.class, AABB);
                for (Entity entityLiving : entityList) {
                    if (entityLiving instanceof EntityLiving || entityLiving instanceof EntityLivingBase) {
                        if (entityLiving instanceof EntityPlayer) {
                            if (entityLiving == entityPlayer) continue;
                        }
                        entityPlayer.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        entityLiving.attackEntityFrom(DamageSource.MAGIC, 4);
                        entityLiving.motionY = 0.4;
                        double x = (entityPlayer.posX - entityLiving.posX) / 3;
                        double z = (entityPlayer.posZ - entityLiving.posZ) / 3;
                        entityLiving.motionX = -x;
                        entityLiving.motionZ = -z;
                    } else {
                        entityLiving.setDead();
                    }
                }
            }
        }
    }
    /*
    �Զ�����
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        String str = event.getMessage();
        if(str.substring(0,1).toCharArray()[0]=='#'&&str.length()>1){

            str = str.substring(1,str.length());
            String[] strs = str.split(" ");
            if(strs.length==1&&strs[0].equals("close")){
                chatTime = 0;
            }
            else if(strs.length==2){
                chatLore = strs[0];
                chatTime = Integer.parseInt(strs[1]);
            }
            event.setCanceled(true);
        }

    }
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Post event){
        Entity entity = event.getEntity();
        if (entity.world!=null&&entity.world.isRemote&&entity instanceof EntityPlayer){
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            if(entityPlayer.isHandActive()){
                ItemStack stack = entityPlayer.getActiveItemStack();
                if(stack.getItem()!= Items.SHIELD)return;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
                GlStateManager.scale(0.0025F, 0.0025F, 0.0025F);
                GL11.glTranslated(0,500,0);
                GL11.glRotated(System.currentTimeMillis()%60*6, 0, 1, 0);
                obj.renderAll();
                GlStateManager.popMatrix();
            }
            /*
            System.out.println("user:"+entityPlayer.isUser());
            System.out.println("swing:"+entityPlayer.isSwingInProgress);
            System.out.println("isHandActive:"+entityPlayer.isHandActive());
            */

        }
    }
    public void dBlock(EntityPlayer player){
        World world = player.world;
        if(world==null)return;
        BlockPos pos = new BlockPos(player);
        world.destroyBlock(pos.add(-2,0,-1),true);
        world.destroyBlock(pos.add(-2,0,0),true);
        world.destroyBlock(pos.add(-2,0,1),true);
        world.destroyBlock(pos.add(-2,1,-1),true);
        world.destroyBlock(pos.add(-2,1,0),true);
        world.destroyBlock(pos.add(-2,1,1),true);

        world.destroyBlock(pos.add(2,0,-1),true);
        world.destroyBlock(pos.add(2,0,0),true);
        world.destroyBlock(pos.add(2,0,1),true);
        world.destroyBlock(pos.add(2,1,-1),true);
        world.destroyBlock(pos.add(2,1,0),true);
        world.destroyBlock(pos.add(2,1,1),true);

        world.destroyBlock(pos.add(-1,0,-2),true);
        world.destroyBlock(pos.add(0,0,-2),true);
        world.destroyBlock(pos.add(1,0,-2),true);
        world.destroyBlock(pos.add(-1,1,-2),true);
        world.destroyBlock(pos.add(0,1,-2),true);
        world.destroyBlock(pos.add(1,1,-2),true);

        world.destroyBlock(pos.add(-1,0,2),true);
        world.destroyBlock(pos.add(0,0,2),true);
        world.destroyBlock(pos.add(1,0,2),true);
        world.destroyBlock(pos.add(-1,1,2),true);
        world.destroyBlock(pos.add(0,1,2),true);
        world.destroyBlock(pos.add(1,1,2),true);

        world.destroyBlock(pos.add(-1,0,-1),true);
        world.destroyBlock(pos.add(-1,0,1),true);
        world.destroyBlock(pos.add(1,0,-1),true);
        world.destroyBlock(pos.add(1,1,1),true);
    }
    /*
    Ԥ����
    �����һ����Ⱦ��������
     */
    @SideOnly(Side.CLIENT)
    public static void initTexture(){
            TextureManager manager = Minecraft.getMinecraft().getTextureManager();
            manager.loadTexture(TEXTURE,new SimpleTexture(TEXTURE));
            manager.loadTexture(RenderBigSword.TEXTURE,new SimpleTexture(RenderBigSword.TEXTURE));
    }
    /*
    ���+�ʵ�
    ��������д�������
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void BookOpen(GuiScreenEvent event){
        if(event.getGui() instanceof GuiScreenBook){
            GuiScreenBook bookGui = (GuiScreenBook)event.getGui();
            ItemStack stack = FMLClientHandler.instance().getClientPlayerEntity().getHeldItemMainhand();
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if(nbttagcompound==null){
                nbttagcompound = new NBTTagCompound();
                stack.setTagCompound(nbttagcompound);
            }

            NBTTagList bookPages = nbttagcompound.getTagList("pages", 8).copy();
            if (bookPages == null)
            {
                bookPages = new NBTTagList();
                bookPages.appendTag(new NBTTagString(""));
            }
            try {
                if(bookPages.tagCount()<1000)
                pageSetCurrent(getRandString(),bookPages,0);
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    sendBookToServer(false, bookPages, stack, Minecraft.getMinecraft());
                    FMLClientHandler.instance().getClientPlayerEntity().connection.sendPacket(new CPacketPlayerDigging( CPacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void sendBookToServer(boolean publish,NBTTagList bookPages,ItemStack book,Minecraft mc) throws IOException
    {
            if (bookPages != null)
            {
                while (bookPages.tagCount() > 1)
                {
                    String s = bookPages.getStringTagAt(bookPages.tagCount() - 1);

                    if (!s.isEmpty())
                    {
                        break;
                    }

                    bookPages.removeTag(bookPages.tagCount() - 1);
                }

                if (book.hasTagCompound())
                {
                    NBTTagCompound nbttagcompound = book.getTagCompound();
                    nbttagcompound.setTag("pages", bookPages);
                }
                else
                {
                    book.setTagInfo("pages", bookPages);
                }

                String s1 = "MC|BEdit";

                if (publish)
                {
                    s1 = "MC|BSign";
                    //book.setTagInfo("author", new NBTTagString(editingPlayer.getName()));
                    //book.setTagInfo("title", new NBTTagString(bookTitle.trim()));
                }

                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeItemStack(book);
                mc.getConnection().sendPacket(new CPacketCustomPayload(s1, packetbuffer));
            }
    }
    private void pageSetCurrent(String p_146457_1_,NBTTagList bookPages,int currPage)
    {
        //if (bookPages != null && currPage >= 0 && currPage < bookPages.tagCount())
       // {
        for(int i = 0;i<50;i++){
            //if(bookPages.get(i)!=null)
            //bookPages.removeTag(i);
            bookPages.appendTag(new NBTTagString(getRandString()));

        }
            //bookPages.set(currPage, new NBTTagString(p_146457_1_));

            //bookIsModified = true;
       // }
    }
    /*
    ׷�ټ���
    ���ڼ��Ĵ����ڷ����,���ڵ�����Ч
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityInit(EntityJoinWorldEvent event){
       Entity entity =  event.getEntity();
       if(entity instanceof EntityArrow&&entity.world!=null){
           EntityArrow arrow = (EntityArrow)entity;
           AxisAlignedBB aabb = arrow.getEntityBoundingBox().grow(100.0F);
           List<EntityLiving> entityLiving = entity.world.getEntitiesWithinAABB(EntityLiving.class,aabb);
           double len = Double.MAX_VALUE;
           double len2;
           EntityLiving entityLiving1=null;
           for (EntityLiving living : entityLiving) {
                //arrow.setPositionAndUpdate(living.posX,living.posY,living.posZ);
               len2 = arrow.getDistanceSq(living);
               if(len2<len&&check(entity.world,arrow,living)){
                   len = len2;
                   entityLiving1 = living;
               }
           }
           if(entityLiving1!=null) {
               arrow.motionX = entityLiving1.posX - arrow.posX;
               arrow.motionY = entityLiving1.posY - arrow.posY;
               arrow.motionZ = entityLiving1.posZ - arrow.posZ;
           }
       }
    }
    public boolean check(World world,Entity entity1,Entity entity2){
        if(world==null)return false;
        double vaule = 0;
        double x;
        double y;
        double z;
        for(vaule = 0;vaule<1.0D;vaule+=0.1){
            x = entity1.posX*vaule+(1.0D-vaule)*entity2.posX;
            y = entity1.posY*vaule+(1.0D-vaule)*entity2.posY;
            z = entity1.posZ*vaule+(1.0D-vaule)*entity2.posZ;
            if(world.getBlockState(new BlockPos(x,y,z)).getBlock()!=Blocks.AIR)return false;
        }
        return true;
    }
    public String getRandString(){
        StringBuffer sb = new StringBuffer();
        byte b;
        for(int a= 0;a<200;a++){
            b = (byte) ((rand.nextBoolean()?0:1)|
                    ((rand.nextBoolean()?0:1)<<1)|
                    ((rand.nextBoolean()?0:1)<<2)|
                    ((rand.nextBoolean()?0:1)<<3)|
                    ((rand.nextBoolean()?0:1)<<4)|
                    ((rand.nextBoolean()?0:1)<<5)|
                    ((rand.nextBoolean()?0:1)<<6)|
                    ((rand.nextBoolean()?0:1)<<7));
            sb.append((char)b);
        }
        return sb.toString();

    }
    /*
    ���й�
    �ұ�ģʽ
     */
    @SideOnly(Side.CLIENT)
    public void fly(EntityPlayer player)
    {
        if(ModKeyLoader.fly.isKeyDown())
        player.motionY = 0.5F;
    }
    /*
    ���ֹ�
     */
    @SideOnly(Side.CLIENT)
    public void flash(EntityPlayer player){
       // System.out.println(player.yaw);
        if(!ModKeyLoader.flash.isPressed())return;
        float f = MathHelper.sin(player.rotationYaw * 0.017453292F)*4;
        float f1 = MathHelper.cos(player.rotationYaw * 0.017453292F)*4;
        World world = player.getEntityWorld();
        int up = 0;
        for(int a = 0;a<256;a++){
            if(world.getBlockState(new BlockPos(player.posX-f,player.posY+a,player.posZ+f1)).getBlock()==Blocks.AIR){
                up = a;
                break;
            }
        }
        System.out.println(up);
        player.setPositionAndUpdate(player.posX-f,player.posY+up,player.posZ+f1);
    }
    /*
    ���ܹ�
     */
    @SideOnly(Side.CLIENT)
    public void superRun(EntityPlayer player){
        if(ModKeyLoader.superRun.isKeyDown()){
            if((player.motionX<0.5F&&player.motionX>-0.5F)&&(player.motionZ<0.5F&&player.motionZ>-0.5F)){
                player.motionX = player.motionX*2;
                player.motionZ = player.motionZ*2;
            }
        }
    }
    /*
    ���ֹ�
     */
    @SideOnly(Side.CLIENT)
    public void longHand(EntityPlayer player){
        if(ModKeyLoader.longHand.isKeyDown()) {
            World world = player.getEntityWorld();
            List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().grow(30.0F, 30.0F, 30.0F));
            for (Entity entity : entityList) {
                if(entity instanceof EntityLiving)
                FMLClientHandler.instance().getClient().playerController.attackEntity(player,entity);
               // player.attackTargetEntityWithCurrentItem(entity);
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public void FP(EntityPlayer entityPlayer){
        fpNumber++;
        if(ModKeyLoader.FP.isPressed()){
            FP = !FP;
        }
        if(FP&&fpNumber> ModConfigLoader.fpSpeed){
            fpNumber = 0;
            FMLClientHandler clientHandler = FMLClientHandler.instance();
            if(clientHandler!=null){
                EntityPlayerSP playerSP= clientHandler.getClientPlayerEntity();
                if(playerSP!=null&&ModSentenceLoader.sentences.size()>0)playerSP.sendChatMessage(ModSentenceLoader.sentences.get(rand.nextInt(ModSentenceLoader.sentences.size()-1)));
            }
        }
    }
    /*
    Ŀǰ�ݲ�֧��
    @SideOnly(Side.CLIENT)
    public void findOre(EntityPlayer entityPlayer){
        World world = entityPlayer.world;
        if(world!=null){
            for(int x = -20;x<=20;x++){
                for(int y = -20;y<=20;y++){
                    for(int z = -20;z<=20;z++){
                        BlockPos pos = new BlockPos(entityPlayer.posX+x,entityPlayer.posY+y,entityPlayer.posZ+z);
                        if(world.getBlockState(pos).getBlock()==Blocks.DIAMOND_ORE){
                            world.spawnParticle(EnumParticleTypes.FLAME,entityPlayer.posX,entityPlayer.posY+1.0F,entityPlayer.posZ,
                                    x/4,y/4,z/4);
                        }
                    }
                }
            }
        }
    }*/
    @SideOnly(Side.CLIENT)
    public void Mining(EntityPlayer player) {
        if (ModKeyLoader.mining.isPressed()) {
            Mining = !Mining;
        }

        if (Mining) {
            World world = player.world;
            if(pathNavigate==null&&player!=null){
                //pathNavigate = new PathNavigateGround()
            }
            FMLClientHandler clientHandler = FMLClientHandler.instance();
            if (clientHandler == null) return;
            EntityPlayerSP playerSP = clientHandler.getClientPlayerEntity();
            if (world != null && playerSP != null) {
                System.out.println("�ڿ�");

                Minecraft mc = Minecraft.getMinecraft();
                BlockPos pos = new BlockPos(player);
            /*
            IBlockState iblockstate = mc.world.getBlockState(pos);
            mc.getTutorial().onHitBlock(mc.world, pos, iblockstate, 0.0F);
            FMLClientHandler.instance().getClientPlayerEntity().connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,pos.up(),EnumFacing.DOWN));
            iblockstate.getBlock().onBlockClicked(mc.world, pos,mc.player);*/
            //if(OrePos==null)findOre(player);
            //if(OrePos==null)return;
                BlockPos poss = new BlockPos(player).east();

                if (world.getBlockState(poss).getMaterial() != Material.AIR) {
                    FMLClientHandler.instance().getClient().playerController.onPlayerDamageBlock(poss, EnumFacing.DOWN);
                } else {
                    FMLClientHandler.instance().getClient().playerController.onPlayerDamageBlock(poss.up(), EnumFacing.DOWN);
                }
                if (world.getBlockState(poss).getMaterial() == Material.AIR && world.getBlockState(poss.up()).getMaterial() == Material.AIR)
                    FMLClientHandler.instance().getClientPlayerEntity().move(MoverType.PLAYER,poss.getX()-player.posX,poss.getY()-player.posY,poss.getZ()-player.posZ);
                   // FMLClientHandler.instance().getClientPlayerEntity().moveToBlockPosAndAngles(OrePos, player.rotationYaw, player.rotationPitch);
                   // EntityZombie zombie = new EntityZombie(world);
                    //zombie.getNavigator().tryMoveToXYZ()

            }
        }
    }
    public void findOre(EntityPlayer player){
        World world = player.world;
        BlockPos ppos = new BlockPos(player);
        int len = Integer.MAX_VALUE;
        for(int x = -100;x<100;x++){
            for(int y = -100;y<100;y++){
                for(int z = -100;z<100;z++){
                    BlockPos npos = ppos.add(x,y,z);
                    if(world.getBlockState(npos).getBlock()==Blocks.DIAMOND_ORE){
                        int nlen = getQ(ppos,npos);
                        if(nlen<len){
                            OrePos = npos;
                        }
                    }
                }
            }
        }
    }
    public int getQ(BlockPos pos1,BlockPos pos2){
        int x = Math.abs(pos1.getX())-Math.abs(pos2.getX());
        int y = Math.abs(pos1.getY())-Math.abs(pos2.getY());
        int z = Math.abs(pos1.getZ())-Math.abs(pos2.getZ());
        return x*x+y*y+z*z;
    }

}
