package com.smakolyky.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

object YoutubeUtil {
    fun extractVideoId(youtubeUrl: String): String? = Uri.parse(youtubeUrl).getQueryParameter("v")

    fun thumbnailUrl(youtubeUrl: String): String? = extractVideoId(youtubeUrl)?.let { id -> "https://img.youtube.com/vi/$id/hqdefault.jpg" }

    fun openVideo(
        context: Context,
        youtubeUrl: String,
    ) {
        val videoId = extractVideoId(youtubeUrl)
        val appIntent =
            videoId?.let {
                Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$it"))
            }
        try {
            if (appIntent != null) {
                context.startActivity(appIntent)
            } else {
                throw ActivityNotFoundException()
            }
        } catch (e: ActivityNotFoundException) {
            // Якщо астосунку YouTube немає на пристрої, то відкривається посилання у браузері.
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)))
        }
    }
}
