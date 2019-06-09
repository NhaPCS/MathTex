# math_tex

A Flutter Package to render Latex text by Katex Lib: [online documentation](https://katex.org/). It is used for Android. For IOS you can use lib [plug-in package](https://pub.dev/packages/flutter_tex)

## Example

```
 Widget buildText(String text) {
    print("TEXT  $text");
    return SizedBox(
      child: MathTex( // MathTex init here
        text: text,
        fontSize: 15,
      ),
      height: 35,
    );
  }
```