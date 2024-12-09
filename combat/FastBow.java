package store.scriptkitty.module.impl.combat;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.util.ChatUtil;

import java.util.Iterator;

@ModuleInfo(
        name = "FastBow",
        description = "Shoot Quick",
        category = Category.Combat
)


public class FastBow extends Module {
    public FastBow () {

    }
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
        if (Minecraft.getMinecraft().thePlayer.getHealth() > 0
                && (Minecraft.getMinecraft().thePlayer.onGround || Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
                && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null
                && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
               ) {

            Minecraft.getMinecraft().playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

            for (int i = 0; i < 20; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }

            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 10);
        }

    });
}
