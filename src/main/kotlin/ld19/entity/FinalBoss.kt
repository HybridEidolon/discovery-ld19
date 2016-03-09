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
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

class FinalBoss(x: Float, y: Float) : Entity() {
    private var mob1: LatchMob? = null
    private var mob2: LatchMob? = null
    private var mob3: LatchMob? = null
    private var mob4: LatchMob? = null
    private var mob5: LatchMob? = null

    init {
        this.x = x
        this.y = y
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        if (!state.hasEntity(mob1!!) && !state.hasEntity(mob2!!)
                && !state.hasEntity(mob3!!) && !state.hasEntity(mob4!!)
                && !state.hasEntity(mob5!!)) {
            state.destroyEntity(this)
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {

    }

    override fun keyPressed(key: Int, c: Char) {

    }

    override fun keyReleased(key: Int, c: Char) {

    }

    override val type: String
        get() = "Entity.FinalBoss"

    override fun create(state: PlayfieldState) {
        mob1 = LatchMob(x - 16, y, LatchMob.Companion.FLOATING)
        mob2 = LatchMob(x + 16, y, LatchMob.Companion.FLOATING)
        mob3 = LatchMob(x, y - 16, LatchMob.Companion.FLOATING)
        mob4 = LatchMob(x, y + 16, LatchMob.Companion.FLOATING)
        mob5 = LatchMob(x, y, LatchMob.Companion.FLOATING)

        state.addEntity(mob1!!)
        state.addEntity(mob2!!)
        state.addEntity(mob3!!)
        state.addEntity(mob4!!)
        state.addEntity(mob5!!)
    }

    override fun destroy(state: PlayfieldState) {
        for (i in 0..499) {
            state.addEntity(BrokenPieceParticle(x, y))
        }
        Sounds.get("player-explode")!!.play(0.1f, 1f)
        state.addEntity(YouWinController())
    }

}
