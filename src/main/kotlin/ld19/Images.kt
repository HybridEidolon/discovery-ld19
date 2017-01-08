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
package ld19

import org.newdawn.slick.Image
import org.newdawn.slick.SlickException
import java.util.*

object Images {
    private val images: HashMap<String, Image> = HashMap()

    operator fun get(name: String): Image? {
        // Check if this image has been loaded
        val image = images[name]
        if (image == null) {
            // load image
            try {
                val put = Image("image/$name.png")
                put.filter = Image.FILTER_NEAREST
                images.put(name, put)
                return put
            } catch (e: SlickException) {
                e.printStackTrace()
                return null
            }

        } else {
            return image
        }
    }

//    fun unload(name: String) {
//        val image = images[name]
//        try {
//            image?.destroy()
//            images.remove(image)
//        } catch (e: SlickException) {
//            e.printStackTrace()
//        }
//
//    }

    /**
     *
     * Inserts an image in to the image list, removing and unloading the one
     * that was already there if there was one.
     * @param name name of image
     * *
     * @param image image to replace
     */
    fun put(name: String, image: Image) {
        val prevImage = images[name]
        if (prevImage != null) {
            try {
                prevImage.destroy()
            } catch (e: SlickException) {
                e.printStackTrace()
            }

            images.put(name, image)
        }
    }
}
