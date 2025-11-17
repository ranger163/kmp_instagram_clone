package me.inassar.shared.helpers

import co.touchlab.kermit.Logger

/**
 * Convenience helper returning a tagged Kermit logger instance.
 */
fun logger(tag: String) = Logger.withTag(tag)
