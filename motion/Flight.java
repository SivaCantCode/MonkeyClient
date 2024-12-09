package store.scriptkitty.module.impl.motion;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.util.ChatUtil;


@ModuleInfo(
        name = "Flight",
        description = "Superman type shit",
        category = Category.Motion
)
public class Flight extends Module {
    public static float jumpSpeed = 0.1f;


    public Flight () {

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
        super.onDisable();
    }


    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY += 0.05f;
            mc.thePlayer.capabilities.isFlying = true;
        }

        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.thePlayer.capabilities.setFlySpeed(jumpSpeed);
        }

        // Only send packet if fall distance exceeds threshold and motion indicates imminent landing
        if (mc.thePlayer.fallDistance > 2.0f ) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    });


}
