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
import info.furyhunter.ld19.GameData;
import info.furyhunter.ld19.Sounds;
import info.furyhunter.ld19.state.PlayfieldState;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Artifact extends Entity {
	private float oscInc;
	private float size;
	private float initY;
	
	public Artifact(float x, float y) {
		this.x = x;
		this.y = y;
		initY = y;
		leftOffset = -4;
		topOffset = -4;
		width = 8;
		height = 8;
	}
	
	public static final Color BODY = new Color(160, 160, 160);
	public static final Color GLOW = Color.white;
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		// Destroy if already have
		if (haveArtifact(game.data)) {
			state.destroyEntity(this);
			return;
		}
		
		// Destroy if collide with player
		List<Entity> player = state.getAllEntsOfType("Player");
		for (Entity e : player) {
			if (Collision.entBox(this, e)) {
				state.destroyEntity(this);
				Sounds.get("artifact-pickup").play();
			}
		}
		
		oscInc+=0.001*delta;
		y = (float)Math.sin(oscInc)*2f+initY;
		size = (float)Math.sin(oscInc)*3f;
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		graphics.setColor(GLOW);
		graphics.fillOval(x-size, y-size, size*2, size*2);
		
		graphics.setColor(BODY);
		graphics.fillRect(x-8, y-2, 4, 4);
		graphics.fillRect(x-2, y-8, 4, 4);
		graphics.fillRect(x+4, y-2, 4, 4);
		graphics.fillRect(x-2, y+4, 4, 4);
	}

	public abstract void giveArtifact(GameData data);
	public abstract boolean haveArtifact(GameData data);

	@Override
	public void destroy(PlayfieldState state) {
		giveArtifact(state.data);
		// Do something else
	}

}
