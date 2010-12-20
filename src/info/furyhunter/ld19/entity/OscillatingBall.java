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

public class OscillatingBall extends MovingObstacle {
	public static final int LEFT_RIGHT = 0;
	public static final int UP_DOWN = 1;
	public static final int TOPLEFT_BOTTOMRIGHT = 2;
	public static final int TOPRIGHT_BOTTOMLEFT = 3;
	public static final int NONE = 4;
	
	private float xOrigin;
	private float yOrigin;
	private float offset;
	private int direction;
	private float blink;
	private boolean visible = true;
	
	public OscillatingBall(float x, float y, int direction) {
		this.x = x;
		this.y = y;
		xOrigin = x;
		yOrigin = y;
		leftOffset = -8;
		topOffset = -8;
		width = 16;
		height = 16;
		this.direction = direction;
	}
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		offset+=0.005*delta;
		switch (direction) {
		case LEFT_RIGHT:
			x = (float)(Math.sin(offset)*64)+xOrigin;
			y = yOrigin;
			break;
		case UP_DOWN:
			x = xOrigin;
			y = (float)(Math.sin(offset)*64)+yOrigin;
			break;
		case TOPLEFT_BOTTOMRIGHT:
			x = (float)(Math.sin(offset)*64)+xOrigin;
			y = (float)(Math.sin(offset)*64)+yOrigin;
			break;
		case TOPRIGHT_BOTTOMLEFT:
			x = (float)(Math.sin(offset)*-64)+xOrigin;
			y = (float)(Math.sin(offset)*64)+yOrigin;
			break;
		case NONE:
			x = xOrigin;
			y = yOrigin;
			break;
		}
		
		blink-=0.001*delta;
		if (blink <= 0) {
			if (visible) visible = false; else visible = true;
			blink = 0.05f;
			for (int i=0; i<=5; i++) {
				state.addEntity(new OscBallParticle(x, y));
			}
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		if (visible) {
			graphics.setColor(Color.white);
			graphics.fillOval(getXOffset(), getYOffset(), width, height);
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
		return "Entity.MovingObstacle.OscillatingBall";
	}

	@Override
	public void create(PlayfieldState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy(PlayfieldState state) {
		// TODO Auto-generated method stub

	}

}
