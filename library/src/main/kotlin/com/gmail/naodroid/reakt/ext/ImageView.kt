package com.gmail.naodroid.reakt.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/22.
 */
//----------------------------------------------------------------
//creating
fun Reakt.imageView(block : ImageView.() -> Unit) : ImageView {
	return imageView(null, block)
}
fun Reakt.imageView(style : ViewStyle<in ImageView>?, block : ImageView.() -> Unit) : ImageView {
	val textView = ImageView(this.context)
	commonProcess(textView, style, block)
	return textView
}

var Reakt.ImageStyle : ViewStyle<ImageView>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(ImageView::class.java, value)


//----------------------------------------------------------------
//properties
//image (Drawable)
var ImageView.image : Drawable?
	get() = getDrawable()
	set(value) = setImageDrawable(value)
var ImageView.imageBind : () -> Drawable?
	get() = throw java.lang.UnsupportedOperationException()
	set(value) = Reakt.addBinding { setImageDrawable(value()) }

//image (Resource ID)
var ImageView.imageId : Int
	get() = throw UnsupportedOperationException()
	set(value) = setImageResource(value)
var ImageView.imageIdBind : () -> Int
	get() = throw java.lang.UnsupportedOperationException()
	set(value) = Reakt.addBinding { setImageResource(value()) }

//scale type
var ImageView.scaleType : ImageView.ScaleType
	get() = getScaleType()
	set(value) = setScaleType(value)
var ImageView.scaleTypeBind : () -> ImageView.ScaleType
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setScaleType(value()) }


