package com.mandarin.bcu.androidutil.io

import android.content.Context
import com.mandarin.bcu.androidutil.StaticStore
import common.system.files.FDByte
import common.system.files.VFile
import org.json.JSONObject

/**
 * Which country's version of the game to play. BCU's own data is the Japanese game's (15.5); the other choices
 * use that country's versions of files that differ from BCU's, shipped in the app's assets (assets/<code>, listed
 * in <code>/files.json as path -> size, made by tools/country_data.py from fieryhenry's BCData 15.6.0): stage
 * files, cat and enemy stats, cat levels, talents, orb slots and combos where they differ and BCU can use them,
 * and for Korea also its enemy bases and backgrounds. They replace BCU's file of the same path in the core's file
 * tree, after the asset packs are read and before the game data is loaded (Definer).
 */
object CountryData {
    /** Setting (in StaticStore.CONFIG) with the chosen country's code. */
    const val PREF = "game_country"

    /** Codes in the order of R.array.config_country_names; "" = BCU's own data. */
    val CODES = arrayOf("kr", "jp", "en", "tw", "")

    /** This app's default: the Korean version. */
    const val DEFAULT = "kr"

    fun chosen(context: Context): String {
        val shared = context.getSharedPreferences(StaticStore.CONFIG, Context.MODE_PRIVATE)
        val code = shared.getString(PREF, DEFAULT) ?: DEFAULT
        return if (code in CODES) code else DEFAULT
    }

    /** Puts the chosen country's files into the file tree; returns how many. */
    @JvmStatic
    fun apply(context: Context): Int {
        val code = chosen(context)

        if (code.isEmpty())
            return 0

        val dir = "$code/"

        val list = try {
            context.assets.open(dir + "files.json").use { JSONObject(it.readBytes().toString(Charsets.UTF_8)) }
        } catch (e: Exception) {
            return 0
        }

        val root = VFile.getBCFileTree()
        var n = 0

        for (path in list.keys()) {
            // "./org/stage/..." is shipped as assets/<code>/stage/...
            val bytes = try {
                context.assets.open(dir + path.removePrefix("./org/")).use { it.readBytes() }
            } catch (e: Exception) {
                continue
            }

            root.build(path, FDByte(bytes))
            n++
        }

        return n
    }
}
