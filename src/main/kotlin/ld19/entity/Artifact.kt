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
import ld19.GameData
import ld19.Sounds
import ld19.state.PlayfieldState
import org.newdawn.slick.Color
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

abstract class Artifact(x: Float, private val initY: Float) : Entity() {
    private var oscInc: Float = 0.toFloat()
    private var size: Float = 0.toFloat()

    init {
        this.x = x
        this.y = initY
        leftOffset = -4f
        topOffset = -4f
        width = 8f
        height = 8f
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        // Destroy if already have
        if (haveArtifact(game.data!!)) {
            state.destroyEntity(this)
            return
        }

        // Destroy if collide with player
        val player = state.getAllEntsOfType("Player")
        for (e in player) {
            if (Collision.entBox(this, e)) {
                state.destroyEntity(this)
                Sounds.get("artifact-pickup")!!.play()
            }
        }

        oscInc += (0.001 * delta).toFloat()
        y = Math.sin(oscInc.toDouble()).toFloat() * 2f + initY
        size = Math.sin(oscInc.toDouble()).toFloat() * 3f
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        graphics.color = GLOW
        graphics.fillOval(x - size, y - size, size * 2, size * 2)

        graphics.color = BODY
        graphics.fillRect(x - 8, y - 2, 4f, 4f)
        graphics.fillRect(x - 2, y - 8, 4f, 4f)
        graphics.fillRect(x + 4, y - 2, 4f, 4f)
        graphics.fillRect(x - 2, y + 4, 4f, 4f)
    }

    abstract fun giveArtifact(data: GameData)
    abstract fun haveArtifact(data: GameData): Boolean

    override fun destroy(state: PlayfieldState) {
        giveArtifact(state.data!!)
        // Do something else
    }

    companion object {

        val BODY = Color(160, 160, 160)
        val GLOW = Color.white
    }

}
