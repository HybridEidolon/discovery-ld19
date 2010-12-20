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
package info.furyhunter.ld19.entity;

import info.furyhunter.ld19.DiscoveryGame;
import info.furyhunter.ld19.Sounds;
import info.furyhunter.ld19.state.PlayfieldState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class FinalBoss extends Entity {
	private LatchMob mob1;
	private LatchMob mob2;
	private LatchMob mob3;
	private LatchMob mob4;
	private LatchMob mob5;
	
	public FinalBoss(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		if (!state.hasEntity(mob1) && !state.hasEntity(mob2)
				&& !state.hasEntity(mob3) && !state.hasEntity(mob4)
				&& !state.hasEntity(mob5)) {
			state.destroyEntity(this);
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
	}

	@Override
	public void keyReleased(int key, char c) {
		
	}

	@Override
	public String getType() {
		return "Entity.FinalBoss";
	}

	@Override
	public void create(PlayfieldState state) {
		mob1 = new LatchMob(x-16, y, LatchMob.FLOATING);
		mob2 = new LatchMob(x+16, y, LatchMob.FLOATING);
		mob3 = new LatchMob(x, y-16, LatchMob.FLOATING);
		mob4 = new LatchMob(x, y+16, LatchMob.FLOATING);
		mob5 = new LatchMob(x, y, LatchMob.FLOATING);
		
		state.addEntity(mob1);
		state.addEntity(mob2);
		state.addEntity(mob3);
		state.addEntity(mob4);
		state.addEntity(mob5);
	}

	@Override
	public void destroy(PlayfieldState state) {
		for(int i=0; i<500; i++) {
			state.addEntity(new BrokenPieceParticle(x, y));
		}
		Sounds.get("player-explode").play(0.1f, 1);
		state.addEntity(new YouWinController());
	}

}
