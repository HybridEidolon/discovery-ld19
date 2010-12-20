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
import info.furyhunter.ld19.GoodEndGameSwitch;
import info.furyhunter.ld19.state.PlayfieldState;

import java.io.InputStream;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

public class TextMapLevelGenerator extends Entity {
	public static final int SPACE = 0;
	public static final int WALL = 1;
	public static final int LATCHMOB_LEFT = 2;
	public static final int LATCHMOB_RIGHT = 3;
	public static final int LATCHMOB_UP = 4;
	public static final int LATCHMOB_DOWN = 5;
	public static final int LATCHMOB_FLOATING = 6;
	public static final int LIFE_PICKUP = 7;
	public static final int OSCILLATINGBALL_LEFT_RIGHT = 8;
	public static final int OSCILLATINGBALL_UP_DOWN = 9;
	public static final int OSCILLATINGBALL_TOPLEFT_BOTTOMRIGHT = 10;
	public static final int OSCILLATINGBALL_TOPRIGHT_BOTTOMLEFT = 11;
	public static final int OSCILLATINGBALL_NONE = 12;
	public static final int ARTIFACT_A = 13;
	public static final int ARTIFACT_B = 14;
	public static final int ARTIFACT_C = 15;
	public static final int ARTIFACT_D = 16;
	public static final int BREAKABLE_WALL = 17;
	public static final int BADENDGAMESWITCH = 18;
	public static final int GOODENDGAMESWITCH = 19;
	public static final int ENDGAMEWALL = 20;
	public static final int CHECKPOINT = 21;
	public static final int FINALBOSS = 22;
	public static final int ARTIFACTDOOR = 23;
	private InputStream mapFile;
	private int xR, yR;
	
	public TextMapLevelGenerator(int x, int y) {
		xR = x;
		yR = y;
		PRIORITY = 1;
	}
	
	@Override
	public void update(GameContainer container, PlayfieldState state,
			DiscoveryGame game, int delta) throws SlickException {
		try {
			mapFile = ResourceLoader.getResource(
					"map/"+xR+"x"+yR+".txt").openStream();
			Scanner scanner = new Scanner(mapFile);
			int ix, iy, read;
			for (iy=0; iy<15; iy++) {
				for (ix=0; ix<20; ix++) {
					read = scanner.nextInt();
					addNum(read, ix, iy, state);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			state.destroyEntity(this);
		}
	}
	
	public void addNum(int type, int x, int y, PlayfieldState state) {
		switch (type) {
		case SPACE:
			break;
		case WALL:
			state.addEntity(new Wall(x*16, y*16));
			break;
		case LATCHMOB_LEFT:
			state.addEntity(new LatchMob(x*16+8, y*16+8, LatchMob.LEFT));
			break;
		case LATCHMOB_RIGHT:
			state.addEntity(new LatchMob(x*16+8, y*16+8, LatchMob.RIGHT));
			break;
		case LATCHMOB_UP:
			state.addEntity(new LatchMob(x*16+8, y*16+8, LatchMob.UP));
			break;
		case LATCHMOB_DOWN:
			state.addEntity(new LatchMob(x*16+8, y*16+8, LatchMob.DOWN));
			break;
		case LATCHMOB_FLOATING:
			state.addEntity(new LatchMob(x*16+8, y*16+8, LatchMob.FLOATING));
			break;
		case OSCILLATINGBALL_LEFT_RIGHT:
			state.addEntity(new OscillatingBall(x*16+8, y*16+8,
					OscillatingBall.LEFT_RIGHT));
			break;
		case OSCILLATINGBALL_UP_DOWN:
			state.addEntity(new OscillatingBall(x*16+8, y*16+8,
					OscillatingBall.UP_DOWN));
			break;
		case OSCILLATINGBALL_TOPLEFT_BOTTOMRIGHT:
			state.addEntity(new OscillatingBall(x*16+8, y*16+8,
					OscillatingBall.TOPLEFT_BOTTOMRIGHT));
			break;
		case OSCILLATINGBALL_TOPRIGHT_BOTTOMLEFT:
			state.addEntity(new OscillatingBall(x*16+8, y*16+8,
					OscillatingBall.TOPRIGHT_BOTTOMLEFT));
			break;
		case OSCILLATINGBALL_NONE:
			state.addEntity(new OscillatingBall(x*16+8, y*16+8,
					OscillatingBall.NONE));
			break;
		case LIFE_PICKUP:
			state.addEntity(new LifeUpgrade(x*16, y*16));
			break;
		case ARTIFACT_A:
			state.addEntity(new ArtifactA(x*16+8, y*16+8));
			break;
		case ARTIFACT_B:
			state.addEntity(new ArtifactB(x*16+8, y*16+8));
			break;
		case ARTIFACT_C:
			state.addEntity(new ArtifactC(x*16+8, y*16+8));
			break;
		case ARTIFACT_D:
			state.addEntity(new ArtifactD(x*16+8, y*16+8));
			break;
		case BREAKABLE_WALL:
			state.addEntity(new BreakableWall(x*16, y*16));
			break;
		case BADENDGAMESWITCH:
			state.addEntity(new BadEndGameSwitch(x*16, y*16));
			break;
		case GOODENDGAMESWITCH:
			state.addEntity(new GoodEndGameSwitch(x*16, y*16));
			break;
		case ENDGAMEWALL:
			state.addEntity(new EndGameWall(x*16, y*16));
			break;
		case CHECKPOINT:
			state.addEntity(new CheckPoint(x*16, y*16));
			break;
		case FINALBOSS:
			state.addEntity(new FinalBoss(x*16, y*16));
			break;
		case ARTIFACTDOOR:
			state.addEntity(new ArtifactDoor(x*16, y*16));
			break;
		}
	}

	@Override
	public void render(GameContainer container, PlayfieldState state,
			DiscoveryGame game, Graphics graphics) throws SlickException {
		
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
		return "Entity.TextMapLevelGenerator";
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
