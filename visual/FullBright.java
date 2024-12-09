package store.scriptkitty.module.impl.visual;


import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;
import store.scriptkitty.event.impl.update.EventUpdate;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;
import store.scriptkitty.settings.impl.DoubleSetting;
import store.scriptkitty.settings.impl.ModeSetting;
import store.scriptkitty.util.ChatUtil;

@ModuleInfo (

        name = "FullBright",
        description = "Full of Brightness",
        category = Category.Visual


)

public final class FullBright extends Module {

    private final ModeSetting mode = new ModeSetting("Mode","Gamma","NightVision");
    private final DoubleSetting gamma = new DoubleSetting("Brightness",3,1,3,0.1);

    private  float oldGamma;

    public FullBright () {
        addSettings(mode , gamma);
        setKey(Keyboard.KEY_M);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        oldGamma = mc.gameSettings.gammaSetting;
        ChatUtil.addChatMessage("Toggled FullBright");

    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = oldGamma;
        ChatUtil.addChatMessage("Untoggled FullBright");
        mc.thePlayer.removePotionEffect(Potion.nightVision.id);
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e-> {
        switch (mode.getCurrentMode()) {
            case "Gamma":
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                mc.gameSettings.gammaSetting = (float) gamma.getVal();
                break;
            case "NightVision":
                mc.gameSettings.gammaSetting = oldGamma;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id,Integer.MAX_VALUE, 0));
                break;

        }

    });
}
