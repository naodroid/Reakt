
## Reakt

Reakt is a library to create layout and binding easier, inspired by [Anko](https://github.com/JetBrains/anko) and [React Native](https://facebook.github.io/react-native/).


Simple example

```kotlin
override fun onCreate(bundle : Bundle) {
  super.onCreate(bundle)

  var count = 0
  val reakt = Reakt(this) {
    verticalLayout {
    textView {
      textBind = {"COUNT:" + count}
    }
    button {
      onClick = {count++;update()}
    }
  }
  setContentView(reakt.toView())
}
```
<img src="images/counter.png"/>

When the button pressed, textview will change text auto.

## Use Reakt

Sorry, currently not uploaded to jcenter.

1. download zip
2. add `library` module to your project


## ViewStyle

Reakt supports simple view styles, written in Kotlin (Not XML). It's similar to Reakt Native.


Ex:

```swift
//define style
val textStyle = ViewStyle<TextView>() {
  layoutWidth = fill
  layoutHeight = dp(40)
  textColor = Color.RED
  textSize = dp(20)
  gravily = Gravity.CENTER
}
//
override fun onCreate(bundle : Bundle) {
  super.onCreate(bundle)
  val reakt = Reakt(this) {
  verticalLayout {
      textView(textStyle) {
        text="TEST"
      }
      textView(textStyle) {
        textColor=Color.BLACK //overwrite
        text="TEST2"
      }
    }
  }
}
```

Advantage of this

* Styles has SCOPE. private, package-private, public
* Declare styles in any location as you like

### parent style

Currently, styles not support parent style, but you can do same thing by calling other style.


```kotlin
val textStyle = ViewStyle<TextView>() {
  //...
}
val buttonStyle = ViewStyle<Button>() {
  apply(textStyle)  //call other
  //...
}
```

## Binding

Reakt supports One-Way Data Binding with `xxxBind` parameter.

```Kotlin
textBind = {mText}
backgroundColorBind = {mBackgroundColor}
visibilityBind = { if (mText.length() == 0) View.INVISIBLE else View.VISIBLE }
```

To update binding parameter, **you need to call Reakt#update() method**.

This is similar to `notifyChanged()` in Android-Data-Binding (`setState()` in React Native).


## Adapt your own views

To define reakt parameters for your custom view, write code similar to [ext folder](https://github.com/naodroid/Reakt/tree/master/library/src/main/kotlin/com/gmail/naodroid/reakt/ext).

Step by step

1. Create `Reakt.yourView` method for view creation
2. Create YourViewStyle if needed
3. Add view properties,  `public var YourView.parameter`

example

```kotlin
//view creation method
public fun Reakt.myView(style : ViewStyle<MyView>, block: () -> MyView) -> MyView {
  val view = MyView(this.context)
  commonProcess(view, style, block)  //must call this method
  return view
}

//create custom properies
public var MyView.text : String
  get() = ""
  set(value) = myView.setText(text)

```






