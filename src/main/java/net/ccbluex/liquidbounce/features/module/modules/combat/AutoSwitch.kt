package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.IntegerValue

@ModuleInfo(name = "AutoSwitch", category = ModuleCategory.COMBAT)
class AutoSwitch : Module() {
    /*
    private val timer = MSTimer()
    private val delay = IntegerValue("Delay", 50, 5, 500)
    override fun onEnable() {
        timer.reset()
    }

    override fun onDisable() {
        timer.reset()
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (timer.hasTimePassed(delay.get().toLong())) {
            mc.thePlayer.inventory.currentItem = 1
            mc.playerController.updateController()
        }
        if (timer.hasTimePassed((2 * delay.get()).toLong())) {
            mc.thePlayer.inventory.currentItem = 2
            mc.playerController.updateController()
        }
        if (timer.hasTimePassed((3 * delay.get()).toLong())) {
            mc.thePlayer.inventory.currentItem = 3
            mc.playerController.updateController()
            timer.reset()
        }
    }
     */

    //让ChatGPT优化了上面的狗屎代码
    private val timer = MSTimer()
    private val delay = IntegerValue("Delay", 50, 5, 500)
    private var currentSlot = 0
    override fun onEnable() {
        timer.reset()
        currentSlot = 0
    }

    override fun onDisable() {
        timer.reset()
        currentSlot = 0
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (timer.hasTimePassed(delay.get().toLong())) {
            currentSlot++
            if (currentSlot > 3) currentSlot = 1
            mc.thePlayer.inventory.currentItem = currentSlot
            mc.playerController.updateController()
            timer.reset()
        }
    }
}