package com.nhapcs.math_tex;

import android.content.Context;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class MathTexFactory extends PlatformViewFactory {
    BinaryMessenger binaryMessenger;

    public MathTexFactory(BinaryMessenger createArgsCodec) {
        super(StandardMessageCodec.INSTANCE);
        this.binaryMessenger = createArgsCodec;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
        return new MyMathView(context, binaryMessenger, i);
    }
}
