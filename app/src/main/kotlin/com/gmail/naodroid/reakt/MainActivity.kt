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
import com.gmail.naodroid.reakt.listview.listView
import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by nao on 15/05/19.
 */

public class MainActivity : Activity() {
	private var mItems = ArrayList<String>()

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
				
				listView<String> {
					weight = 1f
					listItemsBind = { mItems }
					cellCreator = { (item) -> cellCreator(item) }
				}

				button {
					text = "add"
					onClick = {
						mItems.add("COUNT " + mItems.count())
						update()
					}
				}
			}
		}	
		setContentView(reakt.toView())
	}
	private fun cellCreator(item : String) : Reakt {
		return Reakt(this) {
			horizontalLayout {
				textView {
					margin = dip(10)
					weight = 1f
					text = item
				}
				textView {
					text = ">"
					marginLeft = dip(10)
				}
			}
		}
	}
	private fun onButtonClick() {

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
