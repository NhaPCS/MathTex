package com.nhapcs.math_tex;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;


/**
 * Created by Nha Nha on 12/30/2016.
 */


public class MyMathView extends WebView implements PlatformView, MethodChannel.MethodCallHandler {
    private static final float default_text_size = 15;
    private String display_text;
    private int text_color;
    private int text_size;
    private final MethodChannel methodChannel;


    public MyMathView(Context context, BinaryMessenger messenger, int id) {
        super(context);
        methodChannel = new MethodChannel(messenger, "plugins.com.nhapcs.math_tex/math_tex_" + id);
        methodChannel.setMethodCallHandler(this);
        configurationSettingWebView();
        setDefaultTextColor(context);
        setDefaultTextSize();
    }

    public void setViewBackgroundColor(int color) {
        setBackgroundColor(color);
        this.invalidate();
    }

    private void pixelSizeConversion(float dimension) {
        if (dimension == default_text_size) {
            setTextSize((int) default_text_size);
        } else {
            int pixel_dimen_equivalent_size = (int) ((double) dimension / 1.6);
            setTextSize(pixel_dimen_equivalent_size);
        }
    }


    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void configurationSettingWebView() {
        setScrollContainer(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalFadingEdgeEnabled(false);
        getSettings().setDomStorageEnabled(true);
        setWebViewClient(new WebViewClient());
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setDisplayZoomControls(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setSupportZoom(false);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setNeedInitialFocus(false);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }


    public void setText(String formula_text) {
        this.display_text = formula_text;
        loadData();
    }

    public String getDisplay_text() {
        return display_text;
    }

    private String getOfflineKatexConfig() {
        String offline_config = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Auto-render test</title>\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/katex/katex.min.css\">\n" +
                "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/katex.min.js\"></script>\n" +
                "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/contrib/auto-render.min.js\"></script>\n" +
                "<style>img{display: inline;height: auto;max-width:   100%;}</style>" +
                " <style type='text/css'>" +
                "body {" +
                "margin: 0px;" +
                "padding: 0px;" +
                "font-size:" + this.text_size + "px;" +
                "color:" + getHexColor(this.text_color) + ";" +
                " }" +
                " </style>" +
                "    </head>\n" +
                "    <body>\n" +
                "        {formula}\n" +
                "        <script>\n" +
                "          renderMathInElement(\n" +
                "              document.body\n" +
                "          );\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
        String start = "<html><head><meta http-equiv='Content-Type' content='text/html' charset='UTF-8' /><style> body {" +
                " white-space: nowrap;}</style></head><body>";

        String end = "</body></html>";
        //return   start+offline_config.replace("{formula}",this.display_text)+end;
        return offline_config.replace("{formula}", this.display_text);


    }

    public void setTextSize(int size) {
        this.text_size = size;
        loadData();

    }

    public void setTextColorPre(int color) {
        this.text_color = color;
    }

    public void setTextColor(int color) {

        this.text_color = color;
        loadData();
    }

    private String getHexColor(int intColor) {
        //Android and javascript color format differ javascript support Hex color, so the android color which user sets is converted to hexcolor to replicate the same in javascript.
        String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
        return hexColor;
    }


    private void setDefaultTextColor(Context context) {
        //sets default text color to black
//        this.text_color = ContextCompat.getColor(context, android.R.color.black);

    }

    private void setDefaultTextSize() {
        //sets view default text size to 18
        this.text_size = (int) default_text_size;
    }

    private void loadData() {
        if (this.display_text != null) {
            this.loadDataWithBaseURL("null", getOfflineKatexConfig(), "text/html", "UTF-8", "about:blank");
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "setText":
                setText((String) methodCall.arguments);
                break;
            default:
                result.notImplemented();
        }

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void dispose() {

    }
}