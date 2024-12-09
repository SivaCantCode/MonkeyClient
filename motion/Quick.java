package store.scriptkitty.module.impl.motion;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.util.ChatUtil;

@ModuleInfo(
        name = "Quick",
        description = "I am Speed",
        category = Category.Motion
)
public class Quick extends Module {

    public Quick() {}

    private static final float SPEED_BOOST = 1.5f; // Adjust this value for faster speed

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.thePlayer.capabilities.isFlying = false;
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        // Check if the player is moving by checking movement keys
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            float yaw = mc.thePlayer.rotationYaw;

            // Set consistent speed in the direction the player is facing
            mc.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * SPEED_BOOST;
            mc.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * SPEED_BOOST;
        }
    });
}
