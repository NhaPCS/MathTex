import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

typedef void MathTexCreatedCallback(MathTexController controller);

class MathTex extends StatefulWidget {
  MathTex({
    Key key,
    this.fontSize,
    this.text,
  }) : super(key: key);

  final int fontSize;
  final String text;

  @override
  State<StatefulWidget> createState() => _MathTexState();
}

class _MathTexState extends State<MathTex> {
  MathTexController _mathTexController;

  MathTexController get mathTexController => _mathTexController;

  @override
  Widget build(BuildContext context) {
    if (defaultTargetPlatform == TargetPlatform.android) {
      if (_mathTexController != null && widget.text != null) {
        _mathTexController.setText(widget.text);
      }
      return AndroidView(
        viewType: 'plugins.com.nhapcs.math_tex/math_tex',
        onPlatformViewCreated: (id) {
          _mathTexController = MathTexController._(id);
          _mathTexController.setText(widget.text);
        },
        creationParams: <String, dynamic>{
          "fontSize": widget.fontSize,
        },
        creationParamsCodec: new StandardMessageCodec(),
      );
    }
//    if (defaultTargetPlatform == TargetPlatform.iOS) {
//      return UiKitView(
//        viewType: 'plugins.com.nhapcs.math_tex/math_tex',
//        onPlatformViewCreated: _onPlatformViewCreated,
//        creationParams: <String, dynamic>{
//          "fontSize": widget.fontSize,
//        },
//        creationParamsCodec: new StandardMessageCodec(),
//      );
//    }

    return Text(
        '$defaultTargetPlatform is not yet supported by the mathjax_view plugin');
  }

  @override
  void dispose() {
    _mathTexController = null;
    super.dispose();
  }
}

class MathTexController {
  MathTexController._(int id)
      : _channel =
            new MethodChannel('plugins.com.nhapcs.math_tex/math_tex_$id');

  final MethodChannel _channel;

  Future<void> setText(String text) async {
    assert(text != null);
    return _channel.invokeMethod('setText', text);
  }

  void dispose() {
    _channel.invokeMethod('setText', "");
  }
}
