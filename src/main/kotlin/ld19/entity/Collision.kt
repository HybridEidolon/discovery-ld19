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

import org.newdawn.slick.geom.Line
import org.newdawn.slick.geom.Rectangle

object Collision {
    val LEFT = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3

    /**
     *
     * Does a collision check based on an entities' box collision parameters.
     * Uses the `top` and `left` properties to define the
     * top-left corner of the box in the collision check.
     * @param entA
     * *
     * @param entB
     */
    fun entBox(entA: Entity, entB: Entity): Boolean {
        val rectA = Rectangle(entA.xOffset,
                entA.yOffset, entA.width, entA.height)
        val rectB = Rectangle(entB.xOffset,
                entB.yOffset, entB.width, entB.height)
        if (rectA.intersects(rectB)) {
            return true
        }

        return false
    }

    fun entLine(entA: Entity, side: Int, entB: Entity): Boolean {
        var lineA: Line? = null
        if (side == LEFT) {
            lineA = Line(entA.xOffset, entA.yOffset,
                    entA.xOffset, entA.yOffset + entA.height)
        } else if (side == TOP) {
            lineA = Line(entA.xOffset, entA.yOffset,
                    entA.xOffset + entA.width, entA.yOffset)
        } else if (side == RIGHT) {
            lineA = Line(entA.xOffset + entA.width,
                    entA.yOffset, entA.xOffset + entA.width,
                    entA.yOffset + entA.height)
        } else if (side == BOTTOM) {
            lineA = Line(entA.xOffset,
                    entA.yOffset + entA.height,
                    entA.xOffset + entA.width,
                    entA.yOffset + entA.height)
        } else {
            return false
        }
        val rectB = Rectangle(entB.xOffset,
                entB.yOffset, entB.width, entB.height)

        if (lineA.intersects(rectB)) {
            return true
        }

        return false
    }

    fun near(entA: Entity, entB: Entity, maxDistance: Float): Boolean {
        if (Math.sqrt(((entA.x - entB.x) * (entA.x - entB.x) + (entA.y - entB.y) * (entA.y - entB.y)).toDouble()) < maxDistance) {
            return true
        }

        return false
    }
}
