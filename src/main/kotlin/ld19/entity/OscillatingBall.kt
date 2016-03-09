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

class OscillatingBall(private val xOrigin: Float, private val yOrigin: Float, private val direction: Int) : MovingObstacle() {
    private var offset: Float = 0.toFloat()
    private var blink: Float = 0.toFloat()
    private var visible = true

    init {
        this.x = xOrigin
        this.y = yOrigin
        leftOffset = -8f
        topOffset = -8f
        width = 16f
        height = 16f
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        offset += (0.005 * delta).toFloat()
        when (direction) {
            LEFT_RIGHT -> {
                x = (Math.sin(offset.toDouble()) * 64).toFloat() + xOrigin
                y = yOrigin
            }
            UP_DOWN -> {
                x = xOrigin
                y = (Math.sin(offset.toDouble()) * 64).toFloat() + yOrigin
            }
            TOPLEFT_BOTTOMRIGHT -> {
                x = (Math.sin(offset.toDouble()) * 64).toFloat() + xOrigin
                y = (Math.sin(offset.toDouble()) * 64).toFloat() + yOrigin
            }
            TOPRIGHT_BOTTOMLEFT -> {
                x = (Math.sin(offset.toDouble()) * -64).toFloat() + xOrigin
                y = (Math.sin(offset.toDouble()) * 64).toFloat() + yOrigin
            }
            NONE -> {
                x = xOrigin
                y = yOrigin
            }
        }

        blink -= (0.001 * delta).toFloat()
        if (blink <= 0) {
            if (visible) visible = false else visible = true
            blink = 0.05f
            for (i in 0..5) {
                state.addEntity(OscBallParticle(x, y))
            }
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        if (visible) {
            graphics.color = Color.white
            graphics.fillOval(xOffset, yOffset, width, height)
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
        get() = "Entity.MovingObstacle.OscillatingBall"

    override fun create(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    override fun destroy(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    companion object {
        val LEFT_RIGHT = 0
        val UP_DOWN = 1
        val TOPLEFT_BOTTOMRIGHT = 2
        val TOPRIGHT_BOTTOMLEFT = 3
        val NONE = 4
    }

}
