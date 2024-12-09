package store.scriptkitty.module.impl.player;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.settings.Setting;

import java.util.List;

@ModuleInfo(
        name = "NoFallDamage",
        description = "Fuck Feather Falling",
        category = Category.Player
)


public class NoFallDamage extends Module {

    public NoFallDamage () {
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
        if (mc.thePlayer.fallDistance > 2.0f) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    });

}
