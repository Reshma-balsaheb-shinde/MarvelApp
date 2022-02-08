package com.example.marvelapp.Application

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.engine.executor.GlideExecutor.newDiskCacheBuilder
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideModule : AppGlideModule() {

   /* private val uncaughtThrowableStrategy = GlideExecutor.UncaughtThrowableStrategy {}

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions {
            RequestOptions()
                .dontAnimate()
                .dontTransform()
                .encodeFormat(Bitmap.CompressFormat.JPEG)
        }
            .setLogLevel(Log.ERROR)
            .setImageDecoderEnabledForBitmaps(true)
            .setDiskCacheExecutor(
                newDiskCacheBuilder()
                    .setUncaughtThrowableStrategy(
                        uncaughtThrowableStrategy
                    ).build()
            )
        super.applyOptions(context, builder)
    }*/
}