package store.scriptkitty.module.impl.player;

import net.minecraft.block.Block;
import org.lwjgl.input.Keyboard;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

import java.util.ArrayList;
import java.util.Arrays;


@ModuleInfo(
        name = "Xray",
        description = "Easy Money",
        category = Category.Player
)


public class Xray extends Module {

    public static boolean enabled;
    private float oldGamma;
    private static ArrayList<Block> xrayBlocks = new ArrayList<>(Arrays.asList(
            Block.getBlockById(14),  // Gold ore
            Block.getBlockById(15),  // Iron ore
            Block.getBlockById(16),  // Coal ore
            Block.getBlockById(21),  // Lapis ore
            Block.getBlockById(56),  // Diamond ore
            Block.getBlockById(73),  // Redstone ore
            Block.getBlockById(74),  // Redstone ore (Glow)
            Block.getBlockById(129), // Emerald ore
            Block.getBlockById(153), // Quartz ore
            Block.getBlockById(8),   // Flowing Water
            Block.getBlockById(9),   // Still Water
            Block.getBlockById(10),  // Flowing Lava
            Block.getBlockById(11)   // Still Lava
    ));


    public Xray () {
        setKey(Keyboard.KEY_X);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Xray.enabled = true;
        this.oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10.0f;
        mc.gameSettings.ambientOcclusion = 0;
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {

        super.onDisable();
        Xray.enabled = false;
        mc.gameSettings.gammaSetting = this.oldGamma;
        mc.gameSettings.ambientOcclusion = 1;
        mc.renderGlobal.loadRenderers();
    }


    public static boolean canXray(Block blockID) {
        if (xrayBlocks.contains(blockID) && enabled) {
            return true;
        }
        return false;
    }

}
