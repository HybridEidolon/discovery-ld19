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

class Bullet(x: Float, y: Float, private val xVelocity: Float, private val yVelocity: Float) : Entity() {

    init {
        this.x = x
        this.y = y
        leftOffset = -1f
        topOffset = -1f
        PRIORITY = 1050

        Sounds.get("bullet-hitwall")
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        // Check for wall collision
        val walls = state.getAllEntsOfType("Wall")
        for (e in walls) {
            if (Collision.entBox(this, e)) {
                state.destroyEntity(this)
                Sounds.get("bullet-hitwall")!!.play()
            }
        }

        // Check for player collision
        val players = state.getAllEntsOfType("Player")
        for (e in players) {
            if (Collision.entBox(this, e)) {
                state.destroyEntity(this)
                game.data!!.life -= 10f
                Sounds.get("player-hit")!!.play()
            }
        }

        if (x > 320 || x < 0 || y > 240 || y < 0) state.destroyEntity(this)

        x += xVelocity * delta
        y += yVelocity * delta
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        graphics.color = Color.red
        graphics.fillRect(xOffset, yOffset, 2f, 2f)
    }

    override fun keyPressed(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override fun keyReleased(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override // TODO Auto-generated method stub
    val type: String
        get() = "Entity.Bullet"

    override fun create(state: PlayfieldState) {

    }

    override fun destroy(state: PlayfieldState) {
        for (i in 0..3) {
            state.addEntity(BurningMaterialParticle(x, y,
                    Math.random().toFloat() * 0.05f - 0.025f,
                    Math.random().toFloat() * 0.05f - 0.025f))
        }
    }

}
