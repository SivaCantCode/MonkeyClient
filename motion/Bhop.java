package store.scriptkitty.module.impl.motion;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

import java.util.Iterator;

@ModuleInfo(
        name = "Bhop",
        description = "Cant touch this",
        category = Category.Motion
)
public class Bhop extends Module {

    public Bhop() {}

    private static final float FORWARD_SPEED = 0.4f;
    private static final float SPIN_RADIUS = 0.5f;
    private static final float JUMP_BOOST = 0.42f;
    private float spinAngle = 0.0f;

    @Override
    public void onEnable() {
        super.onEnable();
        spinAngle = 0.0f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {


        for (Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext(); ) {
            Object theObject = entities.next();


            if (theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;


                if (entity instanceof EntityPlayerSP) continue;


                if (mc.thePlayer.getDistanceToEntity(entity) <= 6.2173613F) {
                    if (entity.isEntityAlive()) {
                        mc.playerController.attackEntity(mc.thePlayer, entity);
                        mc.thePlayer.swingItem();
                    }
                }
            }
        }



        if (mc.gameSettings.keyBindForward.isKeyDown()) {

            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = JUMP_BOOST;
            }


            float yaw = mc.thePlayer.rotationYaw;
            float forwardX = (float) (-Math.sin(Math.toRadians(yaw)) * FORWARD_SPEED);
            float forwardZ = (float) (Math.cos(Math.toRadians(yaw)) * FORWARD_SPEED);


            spinAngle += 20.0f;
            if (spinAngle >= 360.0f) {
                spinAngle -= 360.0f;
            }


            float spinOffsetX = (float) (-Math.sin(Math.toRadians(spinAngle)) * SPIN_RADIUS);
            float spinOffsetZ = (float) (Math.cos(Math.toRadians(spinAngle)) * SPIN_RADIUS);


            mc.thePlayer.motionX = forwardX + spinOffsetX;
            mc.thePlayer.motionZ = forwardZ + spinOffsetZ;

        }
    });
}
