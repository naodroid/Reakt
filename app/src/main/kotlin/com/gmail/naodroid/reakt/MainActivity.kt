package com.gmail.naodroid.reakt

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.naodroid.reakt.ext.*
import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by nao on 15/05/19.
 */

public class MainActivity : Activity() {
	//
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		var count = 0;
		val reakt = Reakt(this) {
			//define default style
			ButtonStyle = buttonStyle
			
			//view creating
			verticalLayout {
				gravity = Gravity.CENTER_HORIZONTAL
				
				textView(textStyle) {
					textBind = {"" + count} //binding variable to view
				}
				
				gridLayout {
					layoutSize = wrap_wrap
					orientation = GridLayout.HORIZONTAL
					rowCount = 4
					columnCount = 3
					
					
					for (i in 1..10) {
						val j = i % 10;
						val span = if (j == 0) 3 else 1
						button {
							text = "" + j
							rowSpec = GridSpec(span, 1f)
						}
					}
				}
			}
		}	
		
		setContentView(reakt.toView())
	}
	
	val textStyle = Style<TextView> {
		layoutSize = wrap_wrap
		textSize = 80f
		margin = dip(30)
	}
	val buttonStyle = Style<Button> {
		layoutWidth = fill
		layoutHeight = wrap
		margin = dip(4)
		textSize = 24f
	}
}
