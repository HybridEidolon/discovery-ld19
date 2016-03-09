/* discovery . Palpable Heroic Perception
 * Copyright (c) 2010, Furyhunter <furyhunter600@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *   
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *   
 * * Neither the name of Furyhunter nor the names of his contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package ld19

import ld19.state.PlayfieldState
import ld19.state.TitleScreenState
import ld19.state.YouWinstate
import org.newdawn.slick.AngelCodeFont
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.GameContainer
import org.newdawn.slick.SlickException
import org.newdawn.slick.state.StateBasedGame

class DiscoveryGame : StateBasedGame(Companion.gameName) {
    var data: GameData? = null
    var defaultState: Int = 0

    @Throws(SlickException::class)
    override fun initStatesList(container: GameContainer) {
        container.setTargetFrameRate(60)
        container.alwaysRender = true
        container.setVSync(true)
        container.soundVolume = 2f
        container.setSmoothDeltas(true)
        container.setClearEachFrame(true)
        container.setShowFPS(false)
        // Initialize the game data
        data = GameData()
        font = AngelCodeFont("font/font.fnt", "font/font_0.png")

        // Add gamestates
        addState(TitleScreenState())
        addState(YouWinstate())
        addState(PlayfieldState())
    }

    companion object {
        val gameName = "discovery . Palpable Heroic Perception"
        val STATE_PLAYFIELD = 1
        val STATE_TITLESCREEN = 0
        val STATE_GAMEOVER = 2
        val GAME_WIDTH = 320
        val GAME_HEIGHT = 240
        var font: AngelCodeFont? = null

        /**
         * @param args
         */
        @JvmStatic fun main(args: Array<String>) {
            try {
                NativesLoader.load()
                val app = AppGameContainer(DiscoveryGame())
                app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false)
                app.soundVolume = 2f
                app.start()
            } catch (e: SlickException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
