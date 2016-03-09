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
import ld19.entity.*
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.state.StateBasedGame
import java.util.*

class PlayfieldState : BasicGameState() {
    private var list: MutableList<Entity>? = null
    var controlEntity: Entity? = null
    var data: GameData? = null

    override fun keyPressed(key: Int, c: Char) {
        controlEntity!!.keyPressed(key, c)
    }

    override fun keyReleased(key: Int, c: Char) {
        controlEntity!!.keyReleased(key, c)
    }

    @Throws(SlickException::class)
    override fun init(container: GameContainer, game: StateBasedGame) {

    }

    @Throws(SlickException::class)
    override fun enter(container: GameContainer?, game: StateBasedGame?) {
        // Reset values
        controlEntity = null
        data = (game as DiscoveryGame).data!!

        // Initialize entity list
        list = LinkedList<Entity>()

        // set up level based on GameData
        addEntity(StarsBackground((-data!!.roomX).toFloat(), (-data!!.roomY).toFloat()))
        addEntity(TextMapLevelGenerator(data!!.roomX, data!!.roomY))
        val player = Player(data!!.startX, data!!.startY,
                data!!.startXVelocity, data!!.startYVelocity, data!!.movingUp,
                data!!.movingDown, data!!.movingLeft, data!!.movingRight)
        addEntity(player)
        controlEntity = player
        addEntity(HUD(player, data!!))
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, game: StateBasedGame, g: Graphics) {
        val copyList = LinkedList(list)
        // Iterate through entities and draw them
        for (i in copyList.indices) {
            copyList[i].render(container, this, game as DiscoveryGame, g)
        }
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, game: StateBasedGame, delta: Int) {
        val copyList = LinkedList(list)
        // Iterate through entities and pop them
        for (i in copyList.indices) {
            copyList[i].update(container, this, game as DiscoveryGame, delta)
        }
    }

    override fun getID(): Int {
        return DiscoveryGame.STATE_PLAYFIELD
    }

    fun getAllEntsOfType(name: String): List<Entity> {
        val newList = LinkedList<Entity>()
        for (e in list!!) {
            if (e.type.contains(name)) {
                newList.add(e)
            }
        }
        return newList
    }

    fun getFirstOfType(name: String): Entity? {
        for (e in list!!) {
            if (e.type.contains(name)) {
                return e
            }
        }
        return null
    }

    fun addEntity(ent: Entity) {
        var place: Entity?
        var index = 0
        index = 0
        while (index < list!!.size) {
            place = list!![index]
            if (place != null && ent.PRIORITY > place.PRIORITY) {
                break
            }
            index++
        }
        list!!.add(index, ent)
        ent.create(this)
    }

    fun destroyEntity(ent: Entity) {
        if (list!!.contains(ent)) {
            ent.destroy(this)
            list!!.remove(ent)
        }
    }

    fun destroyEntitiesOfType(type: String) {
        for (e in list!!) {
            if (e.type.contains(type)) {
                e.destroy(this)
                list!!.remove(e)
            }
        }
    }

    fun destroyAllEntities() {
        for (e in list!!) {
            if (e.type.contains("Particle")) {
                e.destroy(this)
                list!!.remove(e)
            }
        }
    }

    fun removeEntity(ent: Entity) {
        if (list!!.contains(ent)) {
            list!!.remove(ent)
        }
    }

    fun hasEntity(ent: Entity): Boolean {
        if (list!!.contains(ent)) {
            return true
        } else
            return false
    }
}
