import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:math_tex/math_tex.dart';

void main() {
  const MethodChannel channel = MethodChannel('math_tex');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MathTex.platformVersion, '42');
  });
}
