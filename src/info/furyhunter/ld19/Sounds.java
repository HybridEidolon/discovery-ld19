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

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	
	public static Sound get(String name) {
		// Check if this image has been loaded
		Sound sound = sounds.get(name);
		if (sound == null) {
			// load image
			try {
				Sound put = new Sound("sound/"+name+".wav");
				sounds.put(name, put);
				return put;
			} catch (SlickException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return sound;
		}
	}
	
	public static void unload(String name) {
		Sound sound = sounds.get(name);
		sound.stop();
		sounds.remove(sound);
	}
	
	/**
	 * <p>Inserts an sound in to the sound list, removing and unloading the one
	 * that was already there if there was one.</p>
	 * @param name name of sound
	 * @param sound sound to replace
	 */
	public static void put(String name, Sound sound) {
		Sound prevSound = sounds.get(name);
		if (prevSound != null) {
			prevSound.stop();
			sounds.put(name, sound);
		}
	}
}
