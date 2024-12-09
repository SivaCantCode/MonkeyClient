package store.scriptkitty.module.impl.combat;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

@ModuleInfo(
        name = "AutoClicker",
        description = "Automatically clicks to break blocks and attack entities in crosshair",
        category = Category.Combat
)
public class AutoClicker extends Module {

    private final Minecraft mc = Minecraft.getMinecraft();
    private long lastClickTime;
    private final int clicksPerSecond = 20;

    public AutoClicker() {
        setKey(Keyboard.KEY_V);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        lastClickTime = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        mc.thePlayer.swingItem();
        if (mc.objectMouseOver != null) {
            switch (mc.objectMouseOver.typeOfHit) {
                case BLOCK:
                    mc.playerController.onPlayerDamageBlock(mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit);
                    break;
                case ENTITY:
                    if (shouldClick()) {
                        performEntityAttack();
                        lastClickTime = System.currentTimeMillis();
                    }
                    break;
                default:
                    break;
            }
        }

    });

    private boolean shouldClick() {
        return System.currentTimeMillis() - lastClickTime >= 50;
    }

    private void performEntityAttack() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
            if (mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
            }
        }
    }
}
