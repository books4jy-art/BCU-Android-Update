package com.mandarin.bcu.androidutil.io

import android.content.Context
import common.system.files.FDByte
import common.system.files.VFile
import org.json.JSONObject

/**
 * The Korean game's versions of game files that differ from BCU's (Japanese) ones, shipped in the app's assets
 * (assets/kr, listed in kr/files.json as path -> size): stage files, talents and orb slots (Korean 15.6.0
 * DataLocal), and Korean enemy bases and backgrounds (Korean server packs). They replace BCU's file of the same
 * path in the core's file tree, after the asset packs are read and before the game data is loaded. Same files as
 * the Battle Cats Emulator website (its tools/kr_overlay.py makes them).
 */
object KoreanData {
    private const val DIR = "kr/"

    /** Puts the Korean files into the file tree; returns how many. */
    @JvmStatic
    fun apply(context: Context): Int {
        val list = try {
            context.assets.open(DIR + "files.json").use { JSONObject(it.readBytes().toString(Charsets.UTF_8)) }
        } catch (e: Exception) {
            return 0
        }

        val root = VFile.getBCFileTree()
        var n = 0

        for (path in list.keys()) {
            // "./org/stage/..." is shipped as assets/kr/stage/...
            val bytes = try {
                context.assets.open(DIR + path.removePrefix("./org/")).use { it.readBytes() }
            } catch (e: Exception) {
                continue
            }

            root.build(path, FDByte(bytes))
            n++
        }

        return n
    }
}
