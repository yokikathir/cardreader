package com.example.helloworld;


public class NativeLib {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib-furry");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native OutputStruct startGrading(int[] pixels_1, int[] pixels_2, int w, int h);

    public static native CenteringResult manualGrade(int left_line, int right_line, int top_line, int bottom_line, int outer_left, int outer_right, int outer_top, int outer_bottom);
}
