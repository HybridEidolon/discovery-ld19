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
import info.furyhunter.ld19.state.PlayfieldState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class HUD extends Entity {
	private Player followPlayer;
	private GameData data;
	public static final Color RED = new Color(198, 39, 16);
	public static final Color GREEN = new Color(64, 153, 56);
	public HUD(Player player, GameData data) {
		followPlayer = player;
		this.data = data;
		PRIORITY = 99;
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		if (data.life <= 0) {
			state.destroyEntity(this);
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		graphics.setColor(RED);
		graphics.fillRect(followPlayer.x-10, followPlayer.y-11, 20, 3);
		graphics.setColor(GREEN);
		graphics.fillRect(followPlayer.x-10, followPlayer.y-11,
				((float)data.life/data.maxLife)*20, 3);
		
		graphics.setFont(DiscoveryGame.font);
		graphics.setColor(Color.white);
		graphics.drawString(data.artA+data.artB+data.artC+data.artD, 8, 220);
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
		return "Entity.HUD";
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