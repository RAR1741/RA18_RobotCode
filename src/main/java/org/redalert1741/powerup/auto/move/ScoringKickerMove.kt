package org.redalert1741.powerup.auto.move

import org.redalert1741.powerup.Scoring
import org.redalert1741.robotbase.auto.move.EmptyMove

class ScoringKickerMove(private val score: Scoring) : EmptyMove() {
    private var kick: Boolean = false

    override fun setArgs(args: Map<String, String>) {
        kick = args.getOrDefault("kick","f").toBoolean()
    }

    override fun start() {
        if (kick) score.kick() else score.retract()
    }
}
