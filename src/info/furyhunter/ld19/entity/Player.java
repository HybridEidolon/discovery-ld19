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

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	
	private float particleCreationTimer = 0.2f;
	
	public static final Color BODY1 = new Color(128, 128, 128);
	public static final Color BODY2 = new Color(209, 85, 0);
	public static final Color BODY3 = new Color(209, 170, 13);
	public static final int SHOT_NONE = 0;
	public static final int SHOT_UP = 1;
	public static final int SHOT_DOWN = 2;
	public static final int SHOT_LEFT = 3;
	public static final int SHOT_RIGHT = 4;
	private int shooting = SHOT_NONE;
	private float shotCooldown = 0;
	
	public float xVelocity;
	public float yVelocity;
	
	public Player() {
		this(160,120);
	}
	
	public Player(float x, float y) {
		this(x, y, 0, 0, false, false, false, false);
	}
	
	public Player(float x, float y, float xVelocity, float yVelocity, boolean u,
			boolean d, boolean l, boolean r) {
		leftOffset = -8;
		topOffset = -8;
		width = 16;
		height = 16;
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		movingLeft = l;
		movingRight = r;
		movingUp = u;
		movingDown = d;
		PRIORITY = 200;
		
		// Load desired resources
		Sounds.get("player-hit");
		Sounds.get("player-explode");
	}
	
	public void keyPressed(int key, char c) {
		
	}

	public void keyReleased(int key, char c) {
		
	}

	@Override
	public String getType() {
		return "Entity.Player";
	}

	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		/*
		 * Input
		 */
		// Movement
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_W)) {
			movingUp = true;
		} else {
			movingUp = false;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			movingDown = true;
		} else {
			movingDown = false;
		}
		if (input.isKeyDown(Input.KEY_A)) {
			movingLeft = true;
		} else {
			movingLeft = false;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			movingRight = true;
		} else {
			movingRight = false;
		}
		
		// Shooting
		if (input.isKeyDown(Input.KEY_I)) {
			shooting = SHOT_UP;
		}
		if (input.isKeyDown(Input.KEY_K)) {
			shooting = SHOT_DOWN;
		}
		if (input.isKeyDown(Input.KEY_J)) {
			shooting = SHOT_LEFT;
		}
		if (input.isKeyDown(Input.KEY_L)) {
			shooting = SHOT_RIGHT;
		}
		if (!input.isKeyDown(Input.KEY_I) && !input.isKeyDown(Input.KEY_K)
				&& !input.isKeyDown(Input.KEY_J)
				&& !input.isKeyDown(Input.KEY_L)) {
			shooting = SHOT_NONE;
		}
		
		// Terminal Velocity
		if (xVelocity > 1) xVelocity = 1f;
		if (xVelocity < -1) xVelocity = -1f;
		if (yVelocity > 1) yVelocity = 1f;
		if (yVelocity < -1) yVelocity = -1f;
		
		/*
		 * New Collision
		 */
		List<Entity> wall = state.getAllEntsOfType("Wall");
		List<Entity> wallList = new LinkedList<Entity>(wall);
		for (Entity e : wall) {
			if (!Collision.near(this, e, 32)) {
				wallList.remove(e);
			}
		}
		wall.clear();
		
		float i = 0;
		boolean collided = false;
		if (xVelocity > 0) {
			for (i=0; i<xVelocity; i+=0.0005) {
				x+=0.0005*delta;
				for (Entity e : wallList) {
					if (Collision.entLine(this, Collision.RIGHT, e)) {
						game.data.life-= velocityDamage(xVelocity);
						collided = true;
						xVelocity = -xVelocity;
						break;
					}
				}
				if (collided) {
					x-=0.0005*delta;
					break;
				}
			}
		}
		if (xVelocity < 0) {
			for (i=0; i>xVelocity; i-=0.0005) {
				x-=0.0005*delta;
				for (Entity e : wallList) {
					if (Collision.entLine(this, Collision.LEFT, e)) {
						game.data.life-= velocityDamage(xVelocity);
						collided = true;
						xVelocity = -xVelocity;
						break;
					}
				}
				if (collided) {
					x+=0.0005*delta;
					break;
				}
			}
		}
		
		collided = false;
		if (yVelocity > 0) {
			for (i=0; i<yVelocity; i+=0.0005) {
				y+=0.0005*delta;
				for (Entity e : wallList) {
					if (Collision.entLine(this, Collision.BOTTOM, e)) {
						game.data.life-= velocityDamage(yVelocity);
						collided = true;
						yVelocity = -yVelocity;
						break;
					}
				}
				if (collided) {
					y-=0.0005*delta;
					break;
				}
			}
		}
		if (yVelocity < 0) {
			for (i=0; i>yVelocity; i-=0.0005) {
				y-=0.0005*delta;
				for (Entity e : wallList) {
					if (Collision.entLine(this, Collision.TOP, e)) {
						game.data.life-= velocityDamage(yVelocity);
						collided = true;
						yVelocity = -yVelocity;
						break;
					}
				}
				if (collided) {
					y+=0.0005*delta;
					break;
				}
			}
		}
		
		// Moving obstacles kill you instantly.
		List<Entity> moveList = state.getAllEntsOfType(".MovingObstacle");
		for (Entity e : moveList) {
			if (Collision.entBox(this, e)) {
				state.destroyEntity(this);
				game.data.life = 0;
				return;
			}
		}
		
		/*
		 * Movement
		 */
		if (movingUp) {
			yVelocity -= (float)0.0005*delta;
			makeThrusterParticle(state);
			particleCreationTimer -= 0.0008*delta;
		}
		if (movingDown) {
			yVelocity += (float)0.0005*delta;
			makeThrusterParticle(state);
			particleCreationTimer -= 0.0008*delta;
		}
		if (movingLeft) {
			xVelocity -= (float)0.0005*delta;
			makeThrusterParticle(state);
			particleCreationTimer -= 0.0008*delta;
		}
		if (movingRight) {
			xVelocity += (float)0.0005*delta;
			makeThrusterParticle(state);
			particleCreationTimer -= 0.0008*delta;
		}
		
		// Dying
		if (game.data.life <= 0) {
			state.destroyEntity(this);
			return;
		}
		
		// Health regeneration
		game.data.life += 0.005*delta;
		if (game.data.life > game.data.maxLife)
			game.data.life = game.data.maxLife;
		
		// Switching screens
		if (x < 0) {
			game.data.roomX -= 1;
			game.data.startX = 320;
			game.data.startY = y;
			game.data.startXVelocity = xVelocity;
			game.data.startYVelocity = yVelocity;
			game.data.movingLeft = movingLeft;
			game.data.movingRight = movingRight;
			game.data.movingUp = movingUp;
			game.data.movingDown = movingDown;
			game.enterState(DiscoveryGame.STATE_PLAYFIELD);
		}
		if (x >= 320) {
			game.data.roomX += 1;
			game.data.startX = 0;
			game.data.startY = y;
			game.data.startXVelocity = xVelocity;
			game.data.startYVelocity = yVelocity;
			game.data.movingLeft = movingLeft;
			game.data.movingRight = movingRight;
			game.data.movingUp = movingUp;
			game.data.movingDown = movingDown;
			game.enterState(DiscoveryGame.STATE_PLAYFIELD);
		}
		if (y < 0) {
			game.data.roomY -= 1;
			game.data.startX = x;
			game.data.startY = 240;
			game.data.startXVelocity = xVelocity;
			game.data.startYVelocity = yVelocity;
			game.data.movingLeft = movingLeft;
			game.data.movingRight = movingRight;
			game.data.movingUp = movingUp;
			game.data.movingDown = movingDown;
			game.enterState(DiscoveryGame.STATE_PLAYFIELD);
		}
		if (y >= 240) {
			game.data.roomY += 1;
			game.data.startX = x;
			game.data.startY = 0;
			game.data.startXVelocity = xVelocity;
			game.data.startYVelocity = yVelocity;
			game.data.movingLeft = movingLeft;
			game.data.movingRight = movingRight;
			game.data.movingUp = movingUp;
			game.data.movingDown = movingDown;
			game.enterState(DiscoveryGame.STATE_PLAYFIELD);
		}
		
		/*
		 * Shooting
		 */
		if (shotCooldown > 0) shotCooldown-=0.001*delta;
		if (shooting > SHOT_NONE && shotCooldown <= 0) {
			switch (shooting) {
			case SHOT_UP:
				state.addEntity(new SafeBullet(x, y-8, 0, -0.1f));
				Sounds.get("player-shot").play();
				break;
			case SHOT_DOWN:
				state.addEntity(new SafeBullet(x, y+8, 0, 0.1f));
				Sounds.get("player-shot").play();
				break;
			case SHOT_LEFT:
				state.addEntity(new SafeBullet(x-8, y, -0.1f, 0));
				Sounds.get("player-shot").play();
				break;
			case SHOT_RIGHT:
				state.addEntity(new SafeBullet(x+8, y, 0.1f, 0));
				Sounds.get("player-shot").play();
				break;
			}
			shotCooldown = 0.2f;
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		//Image sprite = Images.get("player");
		//graphics.drawImage(sprite, getXOffset(), getYOffset());
		graphics.setColor(BODY1);
		graphics.fillRect(getXOffset(), getYOffset(), width, height);
		graphics.setColor(BODY2);
		graphics.fillRect(getXOffset()+2, getYOffset()+2, width-4, height-4);
		graphics.setColor(BODY3);
		graphics.fillRect(getXOffset()+4, getYOffset()+4, width-8, height-8);
	}

	public int velocityDamage(float velo) {
		if (velo < 0) velo = -velo;
		
		Sounds.get("player-hit").play((float)Math.random()*0.5f+0.5f, 1);
		if (velo >= 0.1 && velo < 0.2) {
			return 5;
		} else if (velo >= 0.2 && velo < 0.5) {
			return 10;
		} else if (velo >= 0.5) {
			return 20;
		} else {
			return 0;
		}
	}

	@Override
	public void create(PlayfieldState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy(PlayfieldState state) {
		for(int i=0; i<500; i++) {
			state.addEntity(new BrokenPieceParticle(x, y));
		}
		GotoGameOverControl control = new GotoGameOverControl();
		state.addEntity(control);
		state.controlEntity = control;
		Sounds.get("player-explode").play((float)Math.random()+0.5f, 1);
	}
	
	public void makeThrusterParticle(PlayfieldState state) {
		if (particleCreationTimer <= 0) {
			state.addEntity(new BurningMaterialParticle(x, y, xVelocity/10,
					yVelocity/10));
			particleCreationTimer = 0.1f;
		}
	}
}
