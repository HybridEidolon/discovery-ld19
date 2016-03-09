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
import ld19.GoodEndGameSwitch
import ld19.state.PlayfieldState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException
import org.newdawn.slick.util.ResourceLoader
import java.io.InputStream
import java.util.*

class TextMapLevelGenerator(private val xR: Int, private val yR: Int) : Entity() {
    private var mapFile: InputStream? = null

    init {
        PRIORITY = 1
    }

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        try {
            mapFile = ResourceLoader.getResource(
                    "map/" + xR + "x" + yR + ".txt").openStream()
            val scanner = Scanner(mapFile)
            var ix: Int
            var iy: Int
            var read: Int
            iy = 0
            while (iy < 15) {
                ix = 0
                while (ix < 20) {
                    read = scanner.nextInt()
                    addNum(read, ix, iy, state)
                    ix++
                }
                iy++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            state.destroyEntity(this)
        }
    }

    fun addNum(type: Int, x: Int, y: Int, state: PlayfieldState) {
        when (type) {
            SPACE -> {
            }
            WALL -> state.addEntity(Wall((x * 16).toFloat(), (y * 16).toFloat()))
            LATCHMOB_LEFT -> state.addEntity(LatchMob((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(), LatchMob.Companion.LEFT))
            LATCHMOB_RIGHT -> state.addEntity(LatchMob((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(), LatchMob.Companion.RIGHT))
            LATCHMOB_UP -> state.addEntity(LatchMob((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(), LatchMob.Companion.UP))
            LATCHMOB_DOWN -> state.addEntity(LatchMob((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(), LatchMob.Companion.DOWN))
            LATCHMOB_FLOATING -> state.addEntity(LatchMob((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(), LatchMob.Companion.FLOATING))
            OSCILLATINGBALL_LEFT_RIGHT -> state.addEntity(OscillatingBall((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(),
                    OscillatingBall.Companion.LEFT_RIGHT))
            OSCILLATINGBALL_UP_DOWN -> state.addEntity(OscillatingBall((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(),
                    OscillatingBall.Companion.UP_DOWN))
            OSCILLATINGBALL_TOPLEFT_BOTTOMRIGHT -> state.addEntity(OscillatingBall((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(),
                    OscillatingBall.Companion.TOPLEFT_BOTTOMRIGHT))
            OSCILLATINGBALL_TOPRIGHT_BOTTOMLEFT -> state.addEntity(OscillatingBall((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(),
                    OscillatingBall.Companion.TOPRIGHT_BOTTOMLEFT))
            OSCILLATINGBALL_NONE -> state.addEntity(OscillatingBall((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat(),
                    OscillatingBall.Companion.NONE))
            LIFE_PICKUP -> state.addEntity(LifeUpgrade((x * 16).toFloat(), (y * 16).toFloat()))
            ARTIFACT_A -> state.addEntity(ArtifactA((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat()))
            ARTIFACT_B -> state.addEntity(ArtifactB((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat()))
            ARTIFACT_C -> state.addEntity(ArtifactC((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat()))
            ARTIFACT_D -> state.addEntity(ArtifactD((x * 16 + 8).toFloat(), (y * 16 + 8).toFloat()))
            BREAKABLE_WALL -> state.addEntity(BreakableWall((x * 16).toFloat(), (y * 16).toFloat()))
            BADENDGAMESWITCH -> state.addEntity(BadEndGameSwitch((x * 16).toFloat(), (y * 16).toFloat()))
            GOODENDGAMESWITCH -> state.addEntity(GoodEndGameSwitch((x * 16).toFloat(), (y * 16).toFloat()))
            ENDGAMEWALL -> state.addEntity(EndGameWall((x * 16).toFloat(), (y * 16).toFloat()))
            CHECKPOINT -> state.addEntity(CheckPoint((x * 16).toFloat(), (y * 16).toFloat()))
            FINALBOSS -> state.addEntity(FinalBoss((x * 16).toFloat(), (y * 16).toFloat()))
            ARTIFACTDOOR -> state.addEntity(ArtifactDoor((x * 16).toFloat(), (y * 16).toFloat()))
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {

    }

    override fun keyPressed(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override fun keyReleased(key: Int, c: Char) {
        // TODO Auto-generated method stub

    }

    override // TODO Auto-generated method stub
    val type: String
        get() = "Entity.TextMapLevelGenerator"

    override fun create(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    override fun destroy(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    companion object {
        val SPACE = 0
        val WALL = 1
        val LATCHMOB_LEFT = 2
        val LATCHMOB_RIGHT = 3
        val LATCHMOB_UP = 4
        val LATCHMOB_DOWN = 5
        val LATCHMOB_FLOATING = 6
        val LIFE_PICKUP = 7
        val OSCILLATINGBALL_LEFT_RIGHT = 8
        val OSCILLATINGBALL_UP_DOWN = 9
        val OSCILLATINGBALL_TOPLEFT_BOTTOMRIGHT = 10
        val OSCILLATINGBALL_TOPRIGHT_BOTTOMLEFT = 11
        val OSCILLATINGBALL_NONE = 12
        val ARTIFACT_A = 13
        val ARTIFACT_B = 14
        val ARTIFACT_C = 15
        val ARTIFACT_D = 16
        val BREAKABLE_WALL = 17
        val BADENDGAMESWITCH = 18
        val GOODENDGAMESWITCH = 19
        val ENDGAMEWALL = 20
        val CHECKPOINT = 21
        val FINALBOSS = 22
        val ARTIFACTDOOR = 23
    }

}
