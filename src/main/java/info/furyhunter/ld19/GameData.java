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
package info.furyhunter.ld19;

public class GameData {
	public GameData() {
		life = 20;
		maxLife = 20;
		roomX = 10;
		roomY = 5;
		startX = 160;
		startY = 120;
		checkpointRoomX = 10;
		checkpointRoomY = 5;
		checkpointStartX = 160;
		checkpointStartY = 120;
		startXVelocity = 0;
		startYVelocity = 0;
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
		haveLifeUpgrade = new boolean[20][15];
		artA = ".";
		artB = ".";
		artC = ".";
		artD = ".";
	}
	
	public float life;
	public float maxLife;
	public int roomX;
	public int roomY;
	public int checkpointRoomX;
	public int checkpointRoomY;
	public float checkpointStartX;
	public float checkpointStartY;
	public float startX;
	public float startY;
	public float startXVelocity;
	public float startYVelocity;
	public boolean movingUp;
	public boolean movingDown;
	public boolean movingLeft;
	public boolean movingRight;
	public boolean haveLifeUpgrade[][];
	public String artA, artB, artC, artD;
}
