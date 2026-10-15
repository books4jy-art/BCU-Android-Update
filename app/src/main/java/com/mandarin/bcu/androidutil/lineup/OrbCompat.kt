package com.mandarin.bcu.androidutil.lineup

import common.pack.Identifier
import common.util.unit.Form
import common.util.unit.Trait

/*
 * Bridges to BCU core 0.7.20. Older cores kept a form's orb slots as one object with a slot count and level limits
 * (Form.orbs: slots, limits[i] = 1 when slot i needs level 60) and marked base-game traits with Trait.BCTrait;
 * the newer core keeps a list of slots on the unit (Unit.orbs, each with the form and level it needs) and has no
 * such flag. These give the orb screens the old shape, so they work unchanged.
 */

/** Orb slots of a form, the old way: null when the form has none. */
class OrbSlots(val slots: Int, val limits: IntArray)

val Form.orbs: OrbSlots?
    get() {
        val list = unit.orbs
        if (list.isEmpty() || list.none { fid >= it.minForm })
            return null
        return OrbSlots(list.size, IntArray(list.size) { if (list[it].minLv >= 60) 1 else 0 })
    }

/** A base-game trait (not one from a custom pack). */
fun Trait.isBC(): Boolean = id.pack == Identifier.DEF
