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
import info.furyhunter.ld19.state.PlayfieldState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BrokenPieceParticle extends Entity {
	private float xVelocity;
	private float yVelocity;
	private float life;
	private Color color;
	
	private float thrusterCreationTimer = (float) Math.random();
	
	public BrokenPieceParticle() {
		this(0, 0);
	}
	
	public BrokenPieceParticle(float x, float y) {
		this(x, y, (float)((Math.random()*.5)-0.25f),
				(float)((Math.random()*.5)-0.25f), (float)Math.random()*2f);
	}
	
	public BrokenPieceParticle(float x, float y, float xVelocity,
			float yVelocity, float life) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.x = x;
		this.y = y;
		this.life = life;
		PRIORITY = 50;
		
		// Random color, always
		color = new Color((float)Math.random(), (float)Math.random(),
				(float)Math.random());
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		x+=xVelocity*delta;
		y+=yVelocity*delta;
		life-=(float)(0.001*delta);
		if (life < 0f) {
			state.destroyEntity(this);
		}
		
		thrusterCreationTimer-=0.001*delta;
		if (thrusterCreationTimer <= 0) {
			state.addEntity(new BurningMaterialParticle(x, y,
					xVelocity/10 + (float)((Math.random()*0.1)-0.05),
					yVelocity/10 + (float)((Math.random()*0.1)-0.05)));
			thrusterCreationTimer=(float) Math.random();
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		graphics.setColor(color);
		graphics.fillRect(x, y, 2, 2);
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
		return "Entity.Particle.BrokenPieceParticle";
	}

	@Override
	public void create(PlayfieldState state) {
		// Do nothing.
	}

	@Override
	public void destroy(PlayfieldState state) {
		
	}

}
