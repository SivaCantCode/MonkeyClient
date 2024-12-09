package store.scriptkitty.module.impl.player;

import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;


@ModuleInfo(

        name = "Jesus",
        description = "Water Who?",
        category = Category.Player


)
public class Jesus extends Module {
    public Jesus () {}
    public static boolean enabled;

    @Override
    public void onEnable() {
        super.onEnable();
        Jesus.enabled = true;

    }

    @Override
    public void onDisable() {
        super.onDisable();
        Jesus.enabled = false;

    }

    public static boolean isEnabled () {
        return enabled;
    }
}
