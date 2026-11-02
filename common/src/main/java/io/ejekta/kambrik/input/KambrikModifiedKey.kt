package io.ejekta.kambrik.input

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.Minecraft
import org.lwjgl.sdl.SDLMouse

sealed class KambrikModifiedBind(val keyMod: KambrikKeyModifier = KambrikKeyModifier.EMPTY) {

    abstract fun getIsPressed(): Boolean

    class Key(val keyCode: InputConstants.Key, mod: KambrikKeyModifier = KambrikKeyModifier.EMPTY) : KambrikModifiedBind(mod) {
        override fun getIsPressed(): Boolean {
            return InputConstants.isKeyDown(keyCode.value) && keyMod.getIsPressed()
        }
    }

    class Mouse(val key: Int, mod: KambrikKeyModifier = KambrikKeyModifier.EMPTY) : KambrikModifiedBind(mod) {
        override fun getIsPressed(): Boolean {
            // SDL mouse buttons are 1-based (left = 1, middle = 2, right = 3) and form a bitmask
            return (SDLMouse.SDL_GetMouseState(null, null) and (1 shl (key - 1))) != 0 && keyMod.getIsPressed()

        }
    }
}