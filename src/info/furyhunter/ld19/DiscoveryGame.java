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

import info.furyhunter.ld19.state.YouWinstate;
import info.furyhunter.ld19.state.PlayfieldState;
import info.furyhunter.ld19.state.TitleScreenState;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DiscoveryGame extends StateBasedGame {
	public static final String gameName =
		"discovery . Palpable Heroic Perception";
	public static final int STATE_PLAYFIELD = 1;
	public static final int STATE_TITLESCREEN = 0;
	public static final int STATE_GAMEOVER = 2;
	public static final int GAME_WIDTH = 320;
	public static final int GAME_HEIGHT = 240;
	public GameData data;
	public int defaultState;
	public static AngelCodeFont font;
	
	public DiscoveryGame() {
		super(gameName);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		container.setTargetFrameRate(60);
		container.setAlwaysRender(true);
		container.setVSync(true);
		container.setSoundVolume(2);
		container.setSmoothDeltas(true);
		container.setClearEachFrame(true);
		container.setShowFPS(false);
		// Initialize the game data
		data = new GameData();
		font = new AngelCodeFont("font/font.fnt", "font/font_0.png");
		
		// Add gamestates
		addState(new TitleScreenState());
		addState(new YouWinstate());
		addState(new PlayfieldState());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new DiscoveryGame());
			app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
			app.setSoundVolume(2);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
