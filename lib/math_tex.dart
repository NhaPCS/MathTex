import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MathTex extends StatelessWidget {
  final String text;

  static const MethodChannel _channel =
      const MethodChannel('plugins.com.nhapcs.math_tex/math_tex');

  const MathTex({Key key, this.text}) : super(key: key);

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  @override
  Widget build(BuildContext context) {
    if (defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: 'plugins.com.nhapcs.math_tex/math_tex',
        onPlatformViewCreated: (int id) {
          _channel.invokeMethod('setText', text);
        },
      );
    }
    return Text(
        '$defaultTargetPlatform is not yet supported by the text_view plugin');
  }
}
