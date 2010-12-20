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

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	
	public static Image get(String name) {
		// Check if this image has been loaded
		Image image = images.get(name);
		if (image == null) {
			// load image
			try {
				Image put = new Image("image/"+name+".png");
				images.put(name, put);
				return put;
			} catch (SlickException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return image;
		}
	}
	
	public static void unload(String name) {
		Image image = images.get(name);
		try {
			image.destroy();
			images.remove(image);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Inserts an image in to the image list, removing and unloading the one
	 * that was already there if there was one.</p>
	 * @param name name of image
	 * @param image image to replace
	 */
	public static void put(String name, Image image) {
		Image prevImage = images.get(name);
		if (prevImage != null) {
			try {
				prevImage.destroy();
			} catch (SlickException e) {
				e.printStackTrace();
			}
			images.put(name, image);
		}
	}
}
