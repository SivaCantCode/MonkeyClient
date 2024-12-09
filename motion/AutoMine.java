package store.scriptkitty.module.impl.motion;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

import java.lang.reflect.Field;


@ModuleInfo(
        name = "AutoMine",
        description = "Automatically mines for you",
        category = Category.Motion
)
public class AutoMine extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();


    private long lastClickTime;
    private final int clicksPerSecond = 20;

    public AutoMine() {

    }

    @Override
    public void onEnable() {
        super.onEnable();
        lastClickTime = System.currentTimeMillis();
        setKeyPressed(mc.gameSettings.keyBindForward, true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        setKeyPressed(mc.gameSettings.keyBindForward, false);
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) {
            if (shouldClick()) {
                mc.playerController.onPlayerDamageBlock(mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit);
                mc.thePlayer.swingItem();
                lastClickTime = System.currentTimeMillis();
            }
        }

    });

    private boolean shouldClick() {
        return System.currentTimeMillis() - lastClickTime >= 50;
    }



    public void setKeyPressed(KeyBinding key, boolean state) {
        try {
            Field pressedField = KeyBinding.class.getDeclaredField("pressed");
            pressedField.setAccessible(true);
            pressedField.setBoolean(key, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
