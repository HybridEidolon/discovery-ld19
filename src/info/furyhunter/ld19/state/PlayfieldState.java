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
package info.furyhunter.ld19.state;

import info.furyhunter.ld19.DiscoveryGame;
import info.furyhunter.ld19.GameData;
import info.furyhunter.ld19.entity.Entity;
import info.furyhunter.ld19.entity.Player;
import info.furyhunter.ld19.entity.HUD;
import info.furyhunter.ld19.entity.StarsBackground;
import info.furyhunter.ld19.entity.TextMapLevelGenerator;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayfieldState extends BasicGameState {
	private List<Entity> list;
	public Entity controlEntity;
	public GameData data;
	
	@Override
	public void keyPressed(int key, char c) {
		controlEntity.keyPressed(key, c);
	}
	
	@Override
	public void keyReleased(int key, char c) {
		controlEntity.keyReleased(key, c);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Reset values
		controlEntity = null;
		data = ((DiscoveryGame)game).data;
		
		// Initialize entity list
		list = new LinkedList<Entity>();
		
		// set up level based on GameData
		addEntity(new StarsBackground(-data.roomX, -data.roomY));
		addEntity(new TextMapLevelGenerator(data.roomX, data.roomY));
		Player player = new Player(data.startX, data.startY,
				data.startXVelocity, data.startYVelocity, data.movingUp,
				data.movingDown, data.movingLeft, data.movingRight);
		addEntity(player);
		controlEntity = player;
		addEntity(new HUD(player, data));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		List<Entity> copyList = new LinkedList<Entity>(list);
		// Iterate through entities and draw them
		for (int i=0; i<copyList.size(); i++) {
			copyList.get(i).render(container, this, (DiscoveryGame)game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		List<Entity> copyList = new LinkedList<Entity>(list);
		// Iterate through entities and pop them
		for (int i=0; i<copyList.size(); i++) {
			copyList.get(i).update(container, this, (DiscoveryGame)game, delta);
		}
	}

	@Override
	public int getID() {
		return DiscoveryGame.STATE_PLAYFIELD;
	}
	
	public List<Entity> getAllEntsOfType(String name) {
		LinkedList<Entity> newList = new LinkedList<Entity>();
		for (Entity e : list) {
			if (e.getType().contains(name)) {
				newList.add(e);
			}
		}
		return newList;
	}
	
	public Entity getFirstOfType(String name) {
		for (Entity e : list) {
			if (e.getType().contains(name)) {
				return e;
			}
		}
		return null;
	}
	
	public void addEntity(Entity ent) {
		Entity place;
		int index = 0;
		for (index=0; index<list.size(); index++) {
			place = list.get(index);
			if (place != null && ent.PRIORITY > place.PRIORITY) {
				break;
			}
		}
		list.add(index, ent);
		ent.create(this);
	}
	
	public void destroyEntity(Entity ent) {
		if (list.contains(ent)) {
			ent.destroy(this);
			list.remove(ent);
		}
	}
	
	public void destroyEntitiesOfType(String type) {
		for (Entity e : list) {
			if (e.getType().contains(type)) {
				e.destroy(this);
				list.remove(e);
			}
		}
	}
	
	public void destroyAllEntities() {
		for (Entity e : list) {
			if (e.getType().contains("Particle")) {
				e.destroy(this);
				list.remove(e);
			}
		}
	}
	
	public void removeEntity(Entity ent) {
		if (list.contains(ent)) {
			list.remove(ent);
		}
	}
	
	public boolean hasEntity(Entity ent) {
		if (list.contains(ent)) {
			return true;
		} else return false;
	}
}
