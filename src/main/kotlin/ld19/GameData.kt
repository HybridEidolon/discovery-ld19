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
package ld19

class GameData {

    var life: Float = 0.toFloat()
    var maxLife: Float = 0.toFloat()
    var roomX: Int = 0
    var roomY: Int = 0
    var checkpointRoomX: Int = 0
    var checkpointRoomY: Int = 0
    var checkpointStartX: Float = 0.toFloat()
    var checkpointStartY: Float = 0.toFloat()
    var startX: Float = 0.toFloat()
    var startY: Float = 0.toFloat()
    var startXVelocity: Float = 0.toFloat()
    var startYVelocity: Float = 0.toFloat()
    var movingUp: Boolean = false
    var movingDown: Boolean = false
    var movingLeft: Boolean = false
    var movingRight: Boolean = false
    var haveLifeUpgrade: Array<BooleanArray>
    var artA: String
    var artB: String
    var artC: String
    var artD: String

    init {
        life = 20f
        maxLife = 20f
        roomX = 10
        roomY = 5
        startX = 160f
        startY = 120f
        checkpointRoomX = 10
        checkpointRoomY = 5
        checkpointStartX = 160f
        checkpointStartY = 120f
        startXVelocity = 0f
        startYVelocity = 0f
        movingUp = false
        movingDown = false
        movingLeft = false
        movingRight = false
        haveLifeUpgrade = Array(20) { BooleanArray(15) }
        artA = "."
        artB = "."
        artC = "."
        artD = "."
    }
}
