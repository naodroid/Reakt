package com.gmail.naodroid.reakt

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import java.util.ArrayList
import java.util.HashMap
import java.util.Stack
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by nao on 15/05/19.
 */

open class Reakt {
	
	val context : Context
	private val stack = Stack<ViewGroup>()
	private val block : Reakt.() -> View
	private val bindingList = ArrayList<() -> Unit>()
	
	//
	constructor(context : Context, block : Reakt.() -> View) {
		this.context = context
		this.block = block
	}
	fun toView() : View {
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
	fun pushStack(viewGroup : ViewGroup) {
		stack.push(viewGroup)
	}
	/**
	 * remove top of stack
	 * A ViewGroup at top of stack, is using to add child view
	 * Usually, you don't need to call this
	 */
	fun popStack() {
		stack.pop()
	}

	/**
	 * returns stack top ViewGroup to use add child view.
	 * Usually, you don't need to call this
	 */
	fun currentViewGroup() : ViewGroup? {
		if (stack.size > 0) {
			return stack.peek()
		}
		return null
	}
	
	//re-binding view
	/**
	 * Update view properties with 'xxBind='
	 * Notice, all views will be updated in this method
	 */
	fun update() {
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
	fun <T : View> registerDefaultStyle(clazz : Class<T>, style : ViewStyle<T>) {
		defaultStyleMap.put(clazz, style)
	}
	/**
	 * Returns default style of view.
	 */
	fun <T : View> getDefaultStyle(view : T) : ViewStyle<T>? {
		var clazz : Class<*> = view.javaClass
		while (true) {
			val style = defaultStyleMap.get(clazz)
			if (style != null) {
				return style as ViewStyle<T>
			}
			if (clazz.equals(View::class.java)) {
				return null
			}
			try {
				clazz = clazz.genericSuperclass as Class<*>
			} catch (e : Throwable) {
				clazz = if (view is ViewGroup) {
					ViewGroup::class.java
				} else {
					View::class.java
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
		fun lazy(initializer: Reakt.() -> View): ReadOnlyProperty<Activity, Reakt> = ReaktLazy(initializer)

		/**
		 * shortcut for Reakt.current().registerDefaultStyle
		 */
		fun <T : View> registerDefaultStyle(clazz : Class<T>, style : ViewStyle<T>) {
			current().registerDefaultStyle(clazz, style)
		}
		/**
		 * shortcut for Reakt.current().getDefaultStyle
		 */
		fun <T : View> getDefaultStyle(view : T) : ViewStyle<T>? {
			return current().getDefaultStyle(view)
		}
	}
}

/**
 * inner class for lazy Reakt declaration
 */
private class ReaktLazy(private val initializer : Reakt.() -> View) : ReadOnlyProperty<Activity, Reakt> {
    var value : Reakt? = null
    override fun getValue(thisRef: Activity, property: KProperty<*>): Reakt {
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
