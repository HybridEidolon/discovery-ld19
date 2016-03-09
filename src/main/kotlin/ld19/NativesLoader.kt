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

import java.io.*
import java.util.*

object NativesLoader {

    var tempDir: File? = null

    @Throws(Exception::class)
    fun load() {
        extractResource("/lwjgl.dll", "lwjgl.dll")
        extractResource("/lwjgl64.dll", "lwjgl64.dll")
        extractResource("/OpenAL32.dll", "OpenAL32.dll")
        extractResource("/OpenAL64.dll", "OpenAL64.dll")
        extractResource("/liblwjgl.so", "liblwjgl.so")
        extractResource("/liblwjgl64.so", "liblwjgl64.so")
        extractResource("/libopenal.so", "libopenal.so")
        extractResource("/libopenal64.so", "libopenal64.so")
        extractResource("/liblwjgl.dylib", "liblwjgl.dylib")
        extractResource("/openal.dylib", "openal.dylib")

        val osName = System.getProperty("os.name")
        val sep: String
        if (osName.contains("Windows")) {
            sep = ";"
        } else if (osName.contains("Linux")) {
            sep = ":"
        } else if (osName.contains("Mac OS X")) {
            sep = ":"
        } else {
            throw IllegalStateException("Unsupported operating system")
        }

        val oldLibraryPath = System.getProperty("java.library.path")
        if (oldLibraryPath.isEmpty()) {
            System.setProperty("java.library.path", tempDir!!.absolutePath)
        } else {
            System.setProperty("java.library.path", oldLibraryPath + sep + tempDir!!.absolutePath)
        }

        // Hack. Fuck you Java.
        val sysPathsField = ClassLoader::class.java.getDeclaredField("sys_paths")
        sysPathsField.isAccessible = true
        sysPathsField.set(null, null)

        println(Arrays.toString(tempDir!!.listFiles()))
    }

    @Throws(Exception::class)
    fun extractResource(name: String, dest: String) {
        var stream: InputStream? = null
        var resStreamOut: OutputStream? = null
        if (tempDir == null) {
            tempDir = createTempDir()
        }
        try {
            stream = NativesLoader::class.java.getResourceAsStream(name)//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if (stream == null) {
                throw FileNotFoundException("Cannot get resource \"$name\" from classpath.")
            }

            var readBytes: Int
            val buffer = ByteArray(4096)

            resStreamOut = FileOutputStream(tempDir!!.absolutePath + "/" + dest)
            readBytes = stream.read(buffer);
            while (readBytes > 0) {
                resStreamOut.write(buffer, 0, readBytes)
                readBytes = stream.read(buffer);
            }
        } catch (ex: Exception) {
            throw ex
        } finally {
            if (stream != null) stream.close()
            if (resStreamOut != null) resStreamOut.close()
        }
    }

    private val TEMP_DIR_ATTEMPTS = 10000

    fun createTempDir(): File {
        val baseDir = File(System.getProperty("java.io.tmpdir"))
        val baseName = "" + System.currentTimeMillis() + "-"

        for (counter in 0..TEMP_DIR_ATTEMPTS - 1) {
            val tempDir = File(baseDir, baseName + counter)
            if (tempDir.mkdir()) {
                return tempDir
            }
        }
        throw IllegalStateException("Failed to create directory within "
                + TEMP_DIR_ATTEMPTS + " attempts (tried "
                + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')')
    }
}
