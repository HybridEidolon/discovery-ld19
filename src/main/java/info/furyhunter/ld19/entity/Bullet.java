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
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.List;

public class Bullet extends Entity {
	private float xVelocity;
	private float yVelocity;
	
	public Bullet(float x, float y, float xVelocity, float yVelocity) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		leftOffset = -1;
		topOffset = -1;
		PRIORITY = 1050;
		
		Sounds.get("bullet-hitwall");
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		// Check for wall collision
		List<Entity> walls = state.getAllEntsOfType("Wall");
		for (Entity e : walls) {
			if (Collision.entBox(this, e)) {
				state.destroyEntity(this);
				Sounds.get("bullet-hitwall").play();
			}
		}
		
		// Check for player collision
		List<Entity> players = state.getAllEntsOfType("Player");
		for (Entity e : players) {
			if (Collision.entBox(this, e)) {
				state.destroyEntity(this);
				game.data.life -= 10;
				Sounds.get("player-hit").play();
			}
		}
		
		if (x > 320 || x < 0 || y > 240 || y < 0) state.destroyEntity(this);
		
		x+=xVelocity*delta;
		y+=yVelocity*delta;
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		graphics.setColor(Color.red);
		graphics.fillRect(getXOffset(), getYOffset(), 2, 2);
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Entity.Bullet";
	}

	@Override
	public void create(PlayfieldState state) {
		
	}

	@Override
	public void destroy(PlayfieldState state) {
		for(int i=0; i < 4; i++) {
			state.addEntity(new BurningMaterialParticle(x, y,
					(float)Math.random()*0.05f-0.025f,
					(float)Math.random()*0.05f-0.025f));
		}
	}

}
