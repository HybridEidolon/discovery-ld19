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
package ld19.state

import ld19.DiscoveryGame
import ld19.GameData
import ld19.Images
import org.newdawn.slick.*
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.state.StateBasedGame

class TitleScreenState : BasicGameState() {
    var textYOffset: Float = 0.toFloat()
    var posInc = 0f
    var readyToGo = false

    override fun keyPressed(key: Int, c: Char) {
        if (key == Input.KEY_ENTER) {
            readyToGo = true
        }
    }

    @Throws(SlickException::class)
    override fun init(container: GameContainer, game: StateBasedGame) {

    }

    @Throws(SlickException::class)
    override fun enter(container: GameContainer?, game: StateBasedGame?) {
        readyToGo = false
        posInc = 0f
        (game as DiscoveryGame).data = GameData()
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, game: StateBasedGame, g: Graphics) {
        g.scale(2f, 2f)
        g.drawImage(Images.get("logo"), 0f, 0f)
        g.color = Color.white
        g.font = DiscoveryGame.font
        g.drawString("Press Enter", 190f, 150 - textYOffset)
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, game: StateBasedGame, delta: Int) {
        textYOffset = (Math.sin(posInc.toDouble()) * 16).toFloat()
        posInc += (0.005 * delta).toFloat()

        if (readyToGo) {
            game.enterState(DiscoveryGame.STATE_PLAYFIELD)
        }
    }

    override fun leave(container: GameContainer?, game: StateBasedGame?) {
        this.posInc = 1f
    }

    override fun getID(): Int {
        // TODO Auto-generated method stub
        return DiscoveryGame.STATE_TITLESCREEN
    }

}
