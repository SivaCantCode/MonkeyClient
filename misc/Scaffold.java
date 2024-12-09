package store.scriptkitty.module.impl.misc;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

@ModuleInfo(
        name = "Scaffold",
        description = "Speed bridging is for noobs",
        category = Category.Misc
)
public class Scaffold extends Module {
    public Scaffold () {}

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        EntityPlayerSP player = mc.thePlayer;
        BlockPos blockPosBelow = new BlockPos(player.posX, player.getEntityBoundingBox().minY - 1, player.posZ);


        if (valid(blockPosBelow)) {
            place(blockPosBelow, EnumFacing.UP);
        }
    });

    void place(BlockPos p, EnumFacing f) {
        EntityPlayerSP player = mc.thePlayer;


        BlockPos targetPos = p.offset(f.getOpposite());

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock) {
            player.swingItem();
            mc.playerController.onPlayerRightClick(player, mc.theWorld, player.getHeldItem(), targetPos, f, new Vec3(0.5, 0.5, 0.5));


            double x = targetPos.getX() + 0.5 - player.posX;
            double y = targetPos.getY() + 0.5 - player.posY - player.getEyeHeight();
            double z = targetPos.getZ() + 0.5 - player.posZ;
            double distance = Math.sqrt(x * x + z * z);
            float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI - 90);
            float pitch = (float) (-Math.atan2(y, distance) * 180 / Math.PI);

            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY, player.posZ, yaw, pitch, player.onGround));
        }
    }

    boolean valid(BlockPos p) {
        Block block = mc.theWorld.getBlock(p);
        return !(block instanceof BlockLiquid) && block.getMaterial() == Material.air;
    }
}
