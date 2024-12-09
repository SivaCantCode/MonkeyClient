package store.scriptkitty.module.impl.visual;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import org.lwjgl.input.Keyboard;
import store.scriptkitty.Monkey;
import store.scriptkitty.event.impl.render.Event2D;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.util.ChatUtil;
import store.scriptkitty.windows.dropdown.DropDownGUI;


@ModuleInfo(
        name = "ClickGUI",
        description = "Graphical click GUI",
        category = Category.Visual

)
public class ClickGui extends Module {

    public ClickGui() {
        setKey(Keyboard.KEY_RSHIFT);

    }


    @Override
    public void onEnable() {
        super.onEnable();


    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(null);
        super.onDisable();
    }


    @Subscribe
    private final Listener<Event2D> onUpdate = new Listener<>(e-> {
        mc.displayGuiScreen(Monkey.INSTANCE.getGui());


    });


}
