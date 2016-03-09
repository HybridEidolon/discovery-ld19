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
package ld19.entity

import ld19.DiscoveryGame
import ld19.Sounds
import ld19.state.PlayfieldState
import org.newdawn.slick.Color
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

class LifeUpgrade(private val initX: Float, private val initY: Float) : Entity() {

    init {
        this.x = initX
        this.y = initY
        leftOffset = -8f
        topOffset = -8f
        width = 16f
        height = 16f
        PRIORITY = 40

        // load resources
        Sounds.get("life-pickup")
    }

    private var offset: Float = 0.toFloat()

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        // Check if we've already been picked up
        if (game.data!!.haveLifeUpgrade[game.data!!.roomX][game.data!!.roomY]) {
            state.removeEntity(this)
        }

        // Check for collision with player
        val players = state.getAllEntsOfType("Player")
        for (e in players) {
            if (Collision.entBox(this, e)) {
                Sounds.get("life-pickup")!!.play()
                state.destroyEntity(this)
                game.data!!.haveLifeUpgrade[game.data!!.roomX][game.data!!.roomY] = true
                game.data!!.maxLife += 20f
            }
        }

        // Floating effect
        offset += (0.01 * delta).toFloat()
        x = (initX + Math.cos(offset.toDouble()) * 4).toFloat()
        y = (initY + Math.sin(offset.toDouble()) * 4).toFloat()
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        graphics.color = Color(249, 255, 91)
        graphics.fillOval(xOffset, yOffset, width, height)
        graphics.color = Color.red
        graphics.fillRect(x - 1, yOffset + 2, 2f, height - 4)
        graphics.fillRect(xOffset + 2, y - 1, width - 4, 2f)
    }

    override fun keyPressed(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override fun keyReleased(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override // TODO Auto-generated method stub
    val type: String
        get() = "Entity.LifeUpgrade"

    override fun create(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    override fun destroy(state: PlayfieldState) {
        for (i in 0..50) {
            state.addEntity(BrokenPieceParticle(x, y))
        }
    }

}
