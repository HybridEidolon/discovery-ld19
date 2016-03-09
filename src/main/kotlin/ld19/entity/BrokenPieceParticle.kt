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
import ld19.state.PlayfieldState
import org.newdawn.slick.Color
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

class BrokenPieceParticle @JvmOverloads constructor(x: Float = 0f, y: Float = 0f, private val xVelocity: Float = (Math.random() * .5 - 0.25f).toFloat(),
                                                    private val yVelocity: Float = (Math.random() * .5 - 0.25f).toFloat(), private var life: Float = Math.random().toFloat() * 2f) : Entity() {
    private val color: Color

    private var thrusterCreationTimer = Math.random().toFloat()

    init {
        this.x = x
        this.y = y
        PRIORITY = 50

        // Random color, always
        color = Color(Math.random().toFloat(), Math.random().toFloat(),
                Math.random().toFloat())
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        x += xVelocity * delta
        y += yVelocity * delta
        life -= (0.001 * delta).toFloat()
        if (life < 0f) {
            state.destroyEntity(this)
        }

        thrusterCreationTimer -= (0.001 * delta).toFloat()
        if (thrusterCreationTimer <= 0) {
            state.addEntity(BurningMaterialParticle(x, y,
                    xVelocity / 10 + (Math.random() * 0.1 - 0.05).toFloat(),
                    yVelocity / 10 + (Math.random() * 0.1 - 0.05).toFloat()))
            thrusterCreationTimer = Math.random().toFloat()
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        graphics.color = color
        graphics.fillRect(x, y, 2f, 2f)
    }

    override fun keyPressed(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override fun keyReleased(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override val type: String
        get() = "Entity.Particle.BrokenPieceParticle"

    override fun create(state: PlayfieldState) {
        // Do nothing.
    }

    override fun destroy(state: PlayfieldState) {

    }

}
