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

class LatchMob(x: Float, y: Float, private val direction: Int) : Mob() {
    private var shotTimer = 0f

    init {
        this.x = x
        this.y = y
        leftOffset = -8f
        topOffset = -8f
        width = 16f
        height = 16f
        life = 5f
        PRIORITY = 1000

        // load resources
        Sounds.get("latchmob-shot")
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        shotTimer -= (0.001 * delta).toFloat()
        if (shotTimer <= 0) {
            Sounds.get("latchmob-shot")!!.play()
            if (direction == LEFT) {
                state.addEntity(Bullet(x, y, -0.1f, 0f))
                shotTimer = 2f
            }
            if (direction == RIGHT) {
                state.addEntity(Bullet(x, y, 0.1f, 0f))
                shotTimer = 2f
            }
            if (direction == UP) {
                state.addEntity(Bullet(x, y, 0f, -0.1f))
                shotTimer = 2f
            }
            if (direction == DOWN) {
                state.addEntity(Bullet(x, y, 0f, 0.1f))
                shotTimer = 2f
            }
            if (direction == FLOATING) {
                state.addEntity(Bullet(x, y,
                        Math.random().toFloat() * 0.1f - 0.05f,
                        Math.random().toFloat() * 0.1f - 0.05f))
                shotTimer = 0.1f
            }
        }

        if (life <= 0) {
            state.destroyEntity(this)
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        when (direction) {
            RIGHT -> {
                graphics.color = BODY
                graphics.fillRect(xOffset, yOffset + 1, 8f, 14f)
                graphics.color = TURRET
                graphics.fillRect(xOffset + 8, yOffset + 5, 4f, 6f)
            }
            LEFT -> {
                graphics.color = BODY
                graphics.fillRect(xOffset + 8, yOffset + 1, 8f, 14f)
                graphics.color = TURRET
                graphics.fillRect(xOffset + 4, yOffset + 5, 4f, 6f)
            }
            DOWN -> {
                graphics.color = BODY
                graphics.fillRect(xOffset + 1, yOffset, 14f, 8f)
                graphics.color = TURRET
                graphics.fillRect(xOffset + 5, yOffset + 8, 6f, 4f)
            }
            UP -> {
                graphics.color = BODY
                graphics.fillRect(xOffset + 1, yOffset + 8, 14f, 8f)
                graphics.color = TURRET
                graphics.fillRect(xOffset + 5, yOffset + 4, 6f, 4f)
            }
            FLOATING -> {
                graphics.color = BODY
                graphics.fillRect(xOffset, yOffset, width, height)
                graphics.color = TURRET
                graphics.fillRect(xOffset + 4, yOffset + 4, width - 8,
                        height - 8)
            }
        }
    }

    override fun keyPressed(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override fun keyReleased(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override // TODO Auto-generated method stub
    val type: String
        get() = "Entity.Mob.Latch"

    override fun create(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    override fun destroy(state: PlayfieldState) {
        for (i in 0..99) {
            state.addEntity(BrokenPieceParticle(x, y))
        }
        Sounds.get("player-explode")!!.play(Math.random().toFloat() + 0.5f, 1f)
    }

    companion object {
        val LEFT = 0
        val UP = 1
        val RIGHT = 2
        val DOWN = 3
        val FLOATING = 4

        val BODY = Color(68, 114, 47)
        val TURRET = Color(147, 160, 141)
    }

}
