package store.scriptkitty.module.impl.combat;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.input.Keyboard;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.util.ChatUtil;

import java.util.Iterator;

@ModuleInfo(
        name = "KillAura",
        description = "Kill Everything",
        category = Category.Combat
)
public class KillAura extends Module {

    private final Minecraft mc = Minecraft.getMinecraft();

    public KillAura() {
        setKey(Keyboard.KEY_Z);
    }

    @Override
    public void onEnable() {
        ChatUtil.addChatMessage("Toggled KillAura");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ChatUtil.addChatMessage("Untoggled KillAura");
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        for (Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext(); ) {
            Object theObject = entities.next();

            if (theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;

                // Skip the player itself
                if (entity instanceof EntityPlayerSP) continue;



                        if (mc.thePlayer.getDistanceToEntity(entity) <= 6.2173613F && entity.isEntityAlive()) {
                            mc.playerController.attackEntity(mc.thePlayer, entity);
                            mc.thePlayer.swingItem();
                        }

            }
        }
    });

}
