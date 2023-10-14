/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "KeepContainer", category = ModuleCategory.MISC)
public class KeepContainer extends Module {

    private GuiContainer container;

    @Override
    public void onDisable() {
        if(container != null)
            mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow(container.inventorySlots.windowId));

        container = null;
    }

    @EventTarget
    public void onGui(final ScreenEvent event) {
        if(event.getGuiScreen() instanceof GuiContainer && !(event.getGuiScreen() instanceof GuiInventory))
            container = (GuiContainer) event.getGuiScreen();
    }

    @EventTarget
    public void onKey(final KeyEvent event) {
        if(event.getKey() == Keyboard.KEY_RCONTROL) {
            if(container == null)
                return;

            mc.displayGuiScreen(container);
        }
    }

    @EventTarget
    public void onPacket(final PacketEvent event) {
        if(event.getPacket() instanceof C0DPacketCloseWindow)
            event.cancelEvent();

        if(event.getPacket() instanceof S2EPacketCloseWindow) {
            final S2EPacketCloseWindow packetCloseWindow = (S2EPacketCloseWindow) event.getPacket();

            if(container != null && container.inventorySlots != null && packetCloseWindow.windowId == container.inventorySlots.windowId)
                container = null;
        }
    }

}
