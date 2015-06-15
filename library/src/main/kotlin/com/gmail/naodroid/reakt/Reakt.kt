package com.gmail.naodroid.reakt

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import java.util
import java.util.ArrayList
import java.util.HashMap
import java.util.Stack
import kotlin.properties.ReadOnlyProperty

/**
 * Created by nao on 15/05/19.
 */

public open class Reakt {
	
	val context : Context
	private val stack = Stack<ViewGroup>()
	private val block : Reakt.() -> View
	private val bindingList = ArrayList<() -> Unit>()
	
	//
	public constructor(context : Context, block : Reakt.() -> View) {
		this.context = context
		this.block = block
	}
	public fun toView() : View {
		ReaktStack.push(this)
		val view = this.block()
		ReaktStack.pop()
		update()
		return view
	}
	
	//management view group
	/**
	 * add viewgroup to stack
	 * A ViewGroup at top of stack, is using to add child view
	 * Usually, you don't need to call this
	 */
	public fun pushStack(viewGroup : ViewGroup) {
		stack.push(viewGroup)
	}
	/**
	 * remove top of stack
	 * A ViewGroup at top of stack, is using to add child view
	 * Usually, you don't need to call this
	 */
	public fun popStack() {
		stack.pop()
	}

	/**
	 * returns stack top ViewGroup to use add child view.
	 * Usually, you don't need to call this
	 */
	public fun currentViewGroup() : ViewGroup? {
		if (stack.size() > 0) {
			return stack.peek()
		}
		return null
	}
	
	//re-binding view
	/**
	 * Update view properties with 'xxBind='
	 * Notice, all views will be updated in this method
	 */
	public fun update() {
		for (block in this.bindingList) {
			block()
		}
	}

	//Default Styles management---------------------
	
	//map for storing (Class-ViewStyle) set
	private val defaultStyleMap = HashMap<Class<*>, ViewStyle<*>>()

	/**
	 * Register Default ViewStyle for View Class.
	 * When you want to create your custom-View default style, you need to use this
	 * See TextView.kt
	 */
	public fun registerDefaultStyle<T : View>(clazz : Class<T>, style : ViewStyle<T>) {
		defaultStyleMap.put(clazz, style)
	}
	/**
	 * Returns default style of view.
	 */
	public fun getDefaultStyle<T : View>(view : T) : ViewStyle<T>? {
		var clazz : Class<*> = view.javaClass
		while (true) {
			val style = defaultStyleMap.get(clazz)
			if (style != null) {
				return style as ViewStyle<T>
			}
			if (clazz.equals(javaClass<View>())) {
				return null;
			}
			try {
				clazz = clazz.getGenericSuperclass() as Class<*>
			} catch (e : Throwable) {
				clazz = if (view is ViewGroup) {
					javaClass<ViewGroup>()
				} else {
					javaClass<View>()
				}
			}
		}
		return null
	}

	//-----------
	companion object {
		/**
		 * add binding lambda for current Reakt object
		 */
		fun addBinding(binding : () -> Unit) {
			current().bindingList.add(binding)
		}

		/**
		 * returns current Reakt object
		 */
		fun current() : Reakt {
			return ReaktStack.current()
		}

		/**
		 * For lazy Reakt declaration
		 * ex:
		 * val mReakt : by Reakt.lazy { /** do anything **/ }
		 */
		public fun lazy(initializer: Reakt.() -> View): ReadOnlyProperty<Activity, Reakt> = ReaktLazy(initializer)

		/**
		 * shortcut for Reakt.current().registerDefaultStyle
		 */
		fun registerDefaultStyle<T : View>(clazz : Class<T>, style : ViewStyle<T>) {
			current().registerDefaultStyle(clazz, style)
		}
		/**
		 * shortcut for Reakt.current().getDefaultStyle
		 */
		fun getDefaultStyle<T : View>(view : T) : ViewStyle<T>? {
			return current().getDefaultStyle(view)
		}
	}
}

/**
 * inner class for lazy Reakt declaration
 */
private class ReaktLazy(private val initializer : Reakt.() -> View) : ReadOnlyProperty<Activity, Reakt> {
	var value : Reakt? = null
	
	override fun get(thisRef: Activity, desc: PropertyMetadata): Reakt {
		if (value == null) {
			value = Reakt(thisRef, initializer)
		}
		return value!!
	}

}

//----------------------------------------------------------------
//Reakt Stack management
private object ReaktStack {
	val stack = Stack<Reakt>()
	fun push(reakt : Reakt) {
		stack.push(reakt)
	}
	fun pop() {
		stack.pop()
	}
	fun current() : Reakt {
		return stack.peek()
	}
}
