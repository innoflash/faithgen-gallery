package net.faithgen.gallery.models

import net.faithgen.sdk.models.Avatar
import net.faithgen.sdk.models.Date
import org.itishka.gsonflatten.Flatten

/**
 * Albums model mapping
 */
final data class Album(
        val id: String,
        val name: String,
        val description: String,
        val date: Date,
        val avatar: Avatar,
        @Flatten("images::count")
        val size: Int
)