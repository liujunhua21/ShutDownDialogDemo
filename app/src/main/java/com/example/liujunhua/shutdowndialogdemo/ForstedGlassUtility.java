package com.example.liujunhua.shutdowndialogdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 *
 */

public class ForstedGlassUtility {
    @SuppressLint("NewApi")
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        try {
            if (Build.VERSION.SDK_INT > 16) {
                Bitmap bitmap = sentBitmap;

                final RenderScript rs = RenderScript.create(context);
                final Allocation input = Allocation.createFromBitmap(rs,
                        sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs, input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                script.setRadius(radius /* e.g. 3.f */);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);

                rs.destroy();
                return bitmap;
            } else {
                // TODO: JNI to get blur
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
