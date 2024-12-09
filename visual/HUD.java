package store.scriptkitty.module.impl.visual;


import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import store.scriptkitty.Monkey;
import store.scriptkitty.event.impl.render.Event2D;
import store.scriptkitty.module.Category;
import store.scriptkitty.module.Module;
import store.scriptkitty.module.ModuleInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.ArrayList;


@ModuleInfo(
        name = "HUD",
        description = "Heads Up display",
        category = Category.Misc
)
public class HUD extends Module {

    private  FontRenderer fr = null;
    private List <Long> click;
    private boolean wasPressed;
    private long lastPressed;



    public HUD () {
        fr = mc.fontRendererObj;
        this.click = new ArrayList<Long>();

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
    private final Listener<Event2D> on2D = new Listener<>(
        e -> {
            // Register mouse clicks
            final boolean pressed = Mouse.isButtonDown(0);
            if (pressed != this.wasPressed) {
                this.lastPressed = System.currentTimeMillis();
                this.wasPressed = pressed;
                if (pressed) {
                    this.click.add(this.lastPressed);
                }
            }


            fr.drawString("FPS: " + Minecraft.getDebugFPS(), 9, 300, Color.WHITE.getRGB());
            fr.drawString("BPS: " + getBPS(), 9, 310, Color.WHITE.getRGB());
            fr.drawString("CPS: " + getCps(), 9, 320, Color.WHITE.getRGB());


            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int screenHeight = sr.getScaledHeight();
            int screenWidth = sr.getScaledWidth();
            int offset = 20;

            for (Module module : Monkey.INSTANCE.getMm().getModules().values()) {
                if (!module.isToggled()) continue;


                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);


                float scaledX = (screenWidth - fr.getStringWidth(module.getName()) * 1.5F - 5) / 1.5F;
                float scaledY = (screenHeight - offset) / 1.5F;


                fr.drawString(module.getName(), scaledX, scaledY, Color.CYAN.getRGB(), true);


                GL11.glPopMatrix();
                offset += 20;
            }
        }
    );






    private final String getBPS() {
        final float ticks = mc.timer.ticksPerSecond * mc.timer.timerSpeed;
        final double bps =  mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX,mc.thePlayer.lastTickPosY,mc.thePlayer.lastTickPosZ) * ticks;
        return String.format("%.2f", bps);
    }




    private int getCps() {
        final long time = System.currentTimeMillis();
        Iterator<Long> iterator = this.click.iterator();

        while (iterator.hasNext()) {
            Long aLong = iterator.next();
            if (aLong + 1000L < time) {
                iterator.remove();
            }
        }
        return this.click.size();
    }




    //    @Subscribe
    //    private final Listener<Event2D> on2D = new Listener<>(
    //            e -> {
    ////
    //
    //                fr.drawString("FPS: " + Minecraft.getDebugFPS() ,9 , 300, Color.WHITE.getRGB());
    //                fr.drawString("BPS: " + getBPS() ,9 , 310, Color.WHITE.getRGB());
    //
    //                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    //                int screenHeight = sr.getScaledHeight();
    //                int screenWidth = sr.getScaledWidth();
    //                int offset = 20;
    //
    //                for (Module module : Monkey.INSTANCE.getMm().getModules().values()) {
    //                    if (!module.isToggled()) continue;
    //                    fr.drawString(module.getName(), screenWidth - fr.getStringWidth(module.getName()) - 5, screenHeight - offset, -1, false);
    //                    offset += 20;
    //                }
    //
    //
    //            }
    //    );








}
