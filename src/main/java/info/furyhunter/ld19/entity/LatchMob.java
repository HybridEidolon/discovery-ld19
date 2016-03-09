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

public class LatchMob extends Mob {
	public static final int LEFT = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int FLOATING = 4;
	
	public static final Color BODY = new Color(68, 114, 47);
	public static final Color TURRET = new Color(147, 160, 141);
	
	private int direction;
	private float shotTimer = 0;
	
	public LatchMob(float x, float y, int direction) {
		this.x = x;
		this.y = y;
		leftOffset = -8;
		topOffset = -8;
		width = 16;
		height = 16;
		life = 5;
		this.direction = direction;
		PRIORITY = 1000;
		
		// load resources
		Sounds.get("latchmob-shot");
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		shotTimer -= 0.001*delta;
		if (shotTimer <= 0) {
			Sounds.get("latchmob-shot").play();
			if (direction == LEFT) {
				state.addEntity(new Bullet(x, y, -0.1f, 0));
				shotTimer = 2f;
			}
			if (direction == RIGHT) {
				state.addEntity(new Bullet(x, y, 0.1f, 0));
				shotTimer = 2f;
			}
			if (direction == UP) {
				state.addEntity(new Bullet(x, y, 0, -0.1f));
				shotTimer = 2f;
			}
			if (direction == DOWN) {
				state.addEntity(new Bullet(x, y, 0, 0.1f));
				shotTimer = 2f;
			}
			if (direction == FLOATING) {
				state.addEntity(new Bullet(x, y,
						(float)Math.random()*0.1f-0.05f,
						(float)Math.random()*0.1f-0.05f));
				shotTimer = 0.1f;
			}
		}
		
		if (life <= 0) {
			state.destroyEntity(this);
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		switch (direction) {
		case RIGHT:
			graphics.setColor(BODY);
			graphics.fillRect(getXOffset(), getYOffset()+1, 8, 14);
			graphics.setColor(TURRET);
			graphics.fillRect(getXOffset()+8, getYOffset()+5, 4, 6);
			break;
		case LEFT:
			graphics.setColor(BODY);
			graphics.fillRect(getXOffset()+8, getYOffset()+1, 8, 14);
			graphics.setColor(TURRET);
			graphics.fillRect(getXOffset()+4, getYOffset()+5, 4, 6);
			break;
		case DOWN:
			graphics.setColor(BODY);
			graphics.fillRect(getXOffset()+1, getYOffset(), 14, 8);
			graphics.setColor(TURRET);
			graphics.fillRect(getXOffset()+5, getYOffset()+8, 6, 4);
			break;
		case UP:
			graphics.setColor(BODY);
			graphics.fillRect(getXOffset()+1, getYOffset()+8, 14, 8);
			graphics.setColor(TURRET);
			graphics.fillRect(getXOffset()+5, getYOffset()+4, 6, 4);
			break;
		case FLOATING:
			graphics.setColor(BODY);
			graphics.fillRect(getXOffset(), getYOffset(), width, height);
			graphics.setColor(TURRET);
			graphics.fillRect(getXOffset()+4, getYOffset()+4, width-8,
					height-8);
			break;
		}
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
		return "Entity.Mob.Latch";
	}

	@Override
	public void create(PlayfieldState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy(PlayfieldState state) {
		for(int i=0; i<100; i++) {
			state.addEntity(new BrokenPieceParticle(x, y));
		}
		Sounds.get("player-explode").play((float)Math.random()+0.5f, 1f);
	}

}
