import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:math_tex/math_tex.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  int index = 0;

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
//    try {
//      platformVersion = await MathTex.platformVersion;
//    } on PlatformException {
//      platformVersion = 'Failed to get platform version.';
//    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
//    if (!mounted) return;
//
//    setState(() {
//      _platformVersion = platformVersion;
//    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: _getBody(),
        floatingActionButton: FloatingActionButton(onPressed: () {
          setState(() {
            index++;
          });
        }),
      ),
    );
  }

  Widget _getBody() {
    print('ID WTF $index');
    return Wrap(
      spacing: 20,
      children: <Widget>[
        buildText("\\(x^${index}\\)"),
      ],
    );
  }

  MathTexController _mathTexController;

  Widget buildText(String text) {
    print("TEXT  $text");
    if (_mathTexController != null) _mathTexController.setText(text);
    return SizedBox(
      child: MathTex(
        onMathjaxViewCreated: (controller) {
          _mathTexController = controller;
          controller.setText(text);
        },
        fontSize: 15,
      ),
      height: 35,
    );
  }
}
