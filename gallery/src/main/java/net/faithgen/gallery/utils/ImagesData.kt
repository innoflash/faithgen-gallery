package net.faithgen.gallery.utils

import net.faithgen.gallery.models.Image

/**
 * Maps images to an album
 */
final data class ImagesData(
        val images: MutableList<Image>
)