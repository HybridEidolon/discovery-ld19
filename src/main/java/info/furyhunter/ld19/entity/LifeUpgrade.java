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

public class LifeUpgrade extends Entity {
	private float initX;
	private float initY;
	
	public LifeUpgrade(float x, float y) {
		this.x = x;
		this.y = y;
		initX = x;
		initY = y;
		leftOffset = -8;
		topOffset = -8;
		width = 16;
		height = 16;
		PRIORITY = 40;
		
		// load resources
		Sounds.get("life-pickup");
	}
	
	private float offset;
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		// Check if we've already been picked up
		if (game.data.haveLifeUpgrade[game.data.roomX][game.data.roomY]) {
			state.removeEntity(this);
		}
		
		// Check for collision with player
		List<Entity> players = state.getAllEntsOfType("Player");
		for (Entity e : players) {
			if (Collision.entBox(this, e)) {
				Sounds.get("life-pickup").play();
				state.destroyEntity(this);
				game.data.haveLifeUpgrade[game.data.roomX][game.data.roomY]=
					true;
				game.data.maxLife += 20;
			}
		}
		
		// Floating effect
		offset+=0.01*delta;
		x = (float) (initX+(Math.cos(offset))*4);
		y = (float) (initY+(Math.sin(offset))*4);
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		graphics.setColor(new Color(249, 255, 91));
		graphics.fillOval(getXOffset(), getYOffset(), width, height);
		graphics.setColor(Color.red);
		graphics.fillRect(x-1, getYOffset()+2, 2, height-4);
		graphics.fillRect(getXOffset()+2, y-1, width-4, 2);
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
		return "Entity.LifeUpgrade";
	}

	@Override
	public void create(PlayfieldState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy(PlayfieldState state) {
		for (int i=0;i<=50;i++) {
			state.addEntity(new BrokenPieceParticle(x, y));
		}
	}

}
