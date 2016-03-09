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
import org.newdawn.slick.*
import java.util.*

class Player @JvmOverloads constructor(x: Float = 160f, y: Float = 120f, var xVelocity: Float = 0f, var yVelocity: Float = 0f, private var movingUp: Boolean = false,
                                       private var movingDown: Boolean = false, private var movingLeft: Boolean = false, private var movingRight: Boolean = false) : Entity() {

    private var particleCreationTimer = 0.2f
    private var shooting = SHOT_NONE
    private var shotCooldown = 0f

    init {
        leftOffset = -8f
        topOffset = -8f
        width = 16f
        height = 16f
        this.x = x
        this.y = y
        PRIORITY = 200

        // Load desired resources
        Sounds.get("player-hit")
        Sounds.get("player-explode")
    }

    override fun keyPressed(key: Int, c: Char) {

    }

    override fun keyReleased(key: Int, c: Char) {

    }

    override val type: String
        get() = "Entity.Player"

    @Throws(SlickException::class)
    override fun update(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, delta: Int) {
        /*
		 * Input
		 */
        // Movement
        val input = container.input
        if (input.isKeyDown(Input.KEY_W)) {
            movingUp = true
        } else {
            movingUp = false
        }
        if (input.isKeyDown(Input.KEY_S)) {
            movingDown = true
        } else {
            movingDown = false
        }
        if (input.isKeyDown(Input.KEY_A)) {
            movingLeft = true
        } else {
            movingLeft = false
        }
        if (input.isKeyDown(Input.KEY_D)) {
            movingRight = true
        } else {
            movingRight = false
        }

        // Shooting
        if (input.isKeyDown(Input.KEY_I)) {
            shooting = SHOT_UP
        }
        if (input.isKeyDown(Input.KEY_K)) {
            shooting = SHOT_DOWN
        }
        if (input.isKeyDown(Input.KEY_J)) {
            shooting = SHOT_LEFT
        }
        if (input.isKeyDown(Input.KEY_L)) {
            shooting = SHOT_RIGHT
        }
        if (!input.isKeyDown(Input.KEY_I) && !input.isKeyDown(Input.KEY_K)
                && !input.isKeyDown(Input.KEY_J)
                && !input.isKeyDown(Input.KEY_L)) {
            shooting = SHOT_NONE
        }

        // Terminal Velocity
        if (xVelocity > 1) xVelocity = 1f
        if (xVelocity < -1) xVelocity = -1f
        if (yVelocity > 1) yVelocity = 1f
        if (yVelocity < -1) yVelocity = -1f

        /*
		 * New Collision
		 */
        var wall = state.getAllEntsOfType("Wall")
        val wallList = LinkedList(wall)
        for (e in wall) {
            if (!Collision.near(this, e, 32f)) {
                wallList.remove(e)
            }
        }

        var i = 0f
        var collided = false
        if (xVelocity > 0) {
            i = 0f
            while (i < xVelocity) {
                x += (0.0005 * delta).toFloat()
                for (e in wallList) {
                    if (Collision.entLine(this, Collision.RIGHT, e)) {
                        game.data!!.life -= velocityDamage(xVelocity).toFloat()
                        collided = true
                        xVelocity = -xVelocity
                        break
                    }
                }
                if (collided) {
                    x -= (0.0005 * delta).toFloat()
                    break
                }
                i += 0.0005f
            }
        }
        if (xVelocity < 0) {
            i = 0f
            while (i > xVelocity) {
                x -= (0.0005 * delta).toFloat()
                for (e in wallList) {
                    if (Collision.entLine(this, Collision.LEFT, e)) {
                        game.data!!.life -= velocityDamage(xVelocity).toFloat()
                        collided = true
                        xVelocity = -xVelocity
                        break
                    }
                }
                if (collided) {
                    x += (0.0005 * delta).toFloat()
                    break
                }
                i -= 0.0005f
            }
        }

        collided = false
        if (yVelocity > 0) {
            i = 0f
            while (i < yVelocity) {
                y += (0.0005 * delta).toFloat()
                for (e in wallList) {
                    if (Collision.entLine(this, Collision.BOTTOM, e)) {
                        game.data!!.life -= velocityDamage(yVelocity).toFloat()
                        collided = true
                        yVelocity = -yVelocity
                        break
                    }
                }
                if (collided) {
                    y -= (0.0005 * delta).toFloat()
                    break
                }
                i += 0.0005f
            }
        }
        if (yVelocity < 0) {
            i = 0f
            while (i > yVelocity) {
                y -= (0.0005 * delta).toFloat()
                for (e in wallList) {
                    if (Collision.entLine(this, Collision.TOP, e)) {
                        game.data!!.life -= velocityDamage(yVelocity).toFloat()
                        collided = true
                        yVelocity = -yVelocity
                        break
                    }
                }
                if (collided) {
                    y += (0.0005 * delta).toFloat()
                    break
                }
                i -= 0.0005f
            }
        }

        // Moving obstacles kill you instantly.
        val moveList = state.getAllEntsOfType(".MovingObstacle")
        for (e in moveList) {
            if (Collision.entBox(this, e)) {
                state.destroyEntity(this)
                game.data!!.life = 0f
                return
            }
        }

        /*
		 * Movement
		 */
        if (movingUp) {
            yVelocity -= 0.0005.toFloat() * delta
            makeThrusterParticle(state)
            particleCreationTimer -= (0.0008 * delta).toFloat()
        }
        if (movingDown) {
            yVelocity += 0.0005.toFloat() * delta
            makeThrusterParticle(state)
            particleCreationTimer -= (0.0008 * delta).toFloat()
        }
        if (movingLeft) {
            xVelocity -= 0.0005.toFloat() * delta
            makeThrusterParticle(state)
            particleCreationTimer -= (0.0008 * delta).toFloat()
        }
        if (movingRight) {
            xVelocity += 0.0005.toFloat() * delta
            makeThrusterParticle(state)
            particleCreationTimer -= (0.0008 * delta).toFloat()
        }

        // Dying
        if (game.data!!.life <= 0) {
            state.destroyEntity(this)
            return
        }

        // Health regeneration
        game.data!!.life += (0.005 * delta).toFloat()
        if (game.data!!.life > game.data!!.maxLife)
            game.data!!.life = game.data!!.maxLife

        // Switching screens
        if (x < 0) {
            game.data!!.roomX -= 1
            game.data!!.startX = 320f
            game.data!!.startY = y
            game.data!!.startXVelocity = xVelocity
            game.data!!.startYVelocity = yVelocity
            game.data!!.movingLeft = movingLeft
            game.data!!.movingRight = movingRight
            game.data!!.movingUp = movingUp
            game.data!!.movingDown = movingDown
            game.enterState(DiscoveryGame.STATE_PLAYFIELD)
        }
        if (x >= 320) {
            game.data!!.roomX += 1
            game.data!!.startX = 0f
            game.data!!.startY = y
            game.data!!.startXVelocity = xVelocity
            game.data!!.startYVelocity = yVelocity
            game.data!!.movingLeft = movingLeft
            game.data!!.movingRight = movingRight
            game.data!!.movingUp = movingUp
            game.data!!.movingDown = movingDown
            game.enterState(DiscoveryGame.STATE_PLAYFIELD)
        }
        if (y < 0) {
            game.data!!.roomY -= 1
            game.data!!.startX = x
            game.data!!.startY = 240f
            game.data!!.startXVelocity = xVelocity
            game.data!!.startYVelocity = yVelocity
            game.data!!.movingLeft = movingLeft
            game.data!!.movingRight = movingRight
            game.data!!.movingUp = movingUp
            game.data!!.movingDown = movingDown
            game.enterState(DiscoveryGame.STATE_PLAYFIELD)
        }
        if (y >= 240) {
            game.data!!.roomY += 1
            game.data!!.startX = x
            game.data!!.startY = 0f
            game.data!!.startXVelocity = xVelocity
            game.data!!.startYVelocity = yVelocity
            game.data!!.movingLeft = movingLeft
            game.data!!.movingRight = movingRight
            game.data!!.movingUp = movingUp
            game.data!!.movingDown = movingDown
            game.enterState(DiscoveryGame.STATE_PLAYFIELD)
        }

        /*
		 * Shooting
		 */
        if (shotCooldown > 0) shotCooldown -= (0.001 * delta).toFloat()
        if (shooting > SHOT_NONE && shotCooldown <= 0) {
            when (shooting) {
                SHOT_UP -> {
                    state.addEntity(SafeBullet(x, y - 8, 0f, -0.1f))
                    Sounds.get("player-shot")!!.play()
                }
                SHOT_DOWN -> {
                    state.addEntity(SafeBullet(x, y + 8, 0f, 0.1f))
                    Sounds.get("player-shot")!!.play()
                }
                SHOT_LEFT -> {
                    state.addEntity(SafeBullet(x - 8, y, -0.1f, 0f))
                    Sounds.get("player-shot")!!.play()
                }
                SHOT_RIGHT -> {
                    state.addEntity(SafeBullet(x + 8, y, 0.1f, 0f))
                    Sounds.get("player-shot")!!.play()
                }
            }
            shotCooldown = 0.2f
        }
    }

    @Throws(SlickException::class)
    override fun render(container: GameContainer, state: PlayfieldState,
                        game: DiscoveryGame, graphics: Graphics) {
        //Image sprite = Images.get("player");
        //graphics.drawImage(sprite, getXOffset(), getYOffset());
        graphics.color = BODY1
        graphics.fillRect(xOffset, yOffset, width, height)
        graphics.color = BODY2
        graphics.fillRect(xOffset + 2, yOffset + 2, width - 4, height - 4)
        graphics.color = BODY3
        graphics.fillRect(xOffset + 4, yOffset + 4, width - 8, height - 8)
    }

    fun velocityDamage(velo: Float): Int {
        var velo = velo
        if (velo < 0) velo = -velo

        Sounds.get("player-hit")!!.play(Math.random().toFloat() * 0.5f + 0.5f, 1f)
        if (velo >= 0.1 && velo < 0.2) {
            return 5
        } else if (velo >= 0.2 && velo < 0.5) {
            return 10
        } else if (velo >= 0.5) {
            return 20
        } else {
            return 0
        }
    }

    override fun create(state: PlayfieldState) {
        // TODO Auto-generated method stub

    }

    override fun destroy(state: PlayfieldState) {
        for (i in 0..499) {
            state.addEntity(BrokenPieceParticle(x, y))
        }
        val control = GotoGameOverControl()
        state.addEntity(control)
        state.controlEntity = control
        Sounds.get("player-explode")!!.play(Math.random().toFloat() + 0.5f, 1f)
    }

    fun makeThrusterParticle(state: PlayfieldState) {
        if (particleCreationTimer <= 0) {
            state.addEntity(BurningMaterialParticle(x, y, xVelocity / 10,
                    yVelocity / 10))
            particleCreationTimer = 0.1f
        }
    }

    companion object {

        val BODY1 = Color(128, 128, 128)
        val BODY2 = Color(209, 85, 0)
        val BODY3 = Color(209, 170, 13)
        val SHOT_NONE = 0
        val SHOT_UP = 1
        val SHOT_DOWN = 2
        val SHOT_LEFT = 3
        val SHOT_RIGHT = 4
    }
}
