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

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

public class Collision {
	public static final int LEFT = 0;
	public static final int TOP = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 3;
	
	/**
	 * <p>Does a collision check based on an entities' box collision parameters.
	 * Uses the <code>top</code> and <code>left</code> properties to define the
	 * top-left corner of the box in the collision check.</p>
	 * @param entA
	 * @param entB
	 */
	public static boolean entBox(Entity entA, Entity entB) {
		Rectangle rectA = new Rectangle(entA.getXOffset(),
				entA.getYOffset(), entA.width, entA.height);
		Rectangle rectB = new Rectangle(entB.getXOffset(),
				entB.getYOffset(), entB.width, entB.height);
		if (rectA.intersects(rectB)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean entLine(Entity entA, int side, Entity entB) {
		Line lineA = null;
		if (side == LEFT) {
			lineA = new Line(entA.getXOffset(), entA.getYOffset(),
					entA.getXOffset(), entA.getYOffset()+entA.height);
		} else if (side == TOP) {
			lineA = new Line(entA.getXOffset(), entA.getYOffset(),
					entA.getXOffset()+entA.width, entA.getYOffset());
		} else if (side == RIGHT) {
			lineA = new Line(entA.getXOffset()+entA.width,
					entA.getYOffset(), entA.getXOffset()+entA.width,
					entA.getYOffset()+entA.height);
		} else if (side == BOTTOM) {
			lineA = new Line(entA.getXOffset(),
					entA.getYOffset()+entA.height,
					entA.getXOffset()+entA.width,
					entA.getYOffset()+entA.height);
		} else {
			return false;
		}
		Rectangle rectB = new Rectangle(entB.getXOffset(),
				entB.getYOffset(), entB.width, entB.height);
		
		if (lineA.intersects(rectB)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean near(Entity entA, Entity entB, float maxDistance) {
		if (Math.sqrt((entA.x - entB.x)*(entA.x - entB.x)
				+ (entA.y - entB.y)*(entA.y - entB.y)) < maxDistance) {
			return true;
		}
		
		return false;
	}
}
