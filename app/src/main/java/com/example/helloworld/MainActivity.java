package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
//        import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.camerabar.ImageFragment;
import com.example.helloworld.camerabar.PhotoFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener, PhotoFragment.OnFragmentInteractionListener {

    // Define the pic id
    private static final int pic_id = 123;
    private static final int pic_id2 = 124;
    private static final int pic_id2_manual = 125;
    private boolean is_value = false;
    // Define the button and imageview type variable
    Button camera_open_id;
    ImageView click_image_id;

    LineView line_view;
    OutputStruct resultData;
    Context context;

    private ViewGroup mainLayout;

    TextView status_id, ratio_id;
    TextView grading_direction_id, horizontal_ratio_id, vertical_ratio_id, furry_id, corner_id, nof_cards_id, furry_percentage_id, bad_corners_id;
//    private Bitmap bitmap;

    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_READ_PERMISSION_CODE = 101;
    private static final int STORAGE_WRITE_PERMISSION_CODE = 102;

    Uri imageUri_1;
    Uri imageUri_2;

    int[] pixels_1, pixels_2;


    Canvas canvas;
    Paint paint;
    int drawerWidth, drawerHeight;

    float mScaleX, mScaleY;


    int line_width, line_height;
    int outer_line_width, outer_line_height;

    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // By ID we can get each component
        // which id is assigned in XML file
        // get Buttons and imageview.
        camera_open_id = (Button) findViewById(R.id.camera_button);
        click_image_id = (ImageView) findViewById(R.id.click_image);

        status_id = (TextView) findViewById(R.id.status);
        ratio_id = (TextView) findViewById(R.id.ratio);
//        ratio_exact_id = (TextView) findViewById(R.id.ratio_exact);
//        left_id = (TextView) findViewById(R.id.left);
//        right_id = (TextView) findViewById(R.id.right);
//        top_id= (TextView) findViewById(R.id.top);
//        bottom_id = (TextView) findViewById(R.id.bottom);

        grading_direction_id = (TextView) findViewById(R.id.grading_direction);
        horizontal_ratio_id = (TextView) findViewById(R.id.horizontal_ratio);
        vertical_ratio_id = (TextView) findViewById(R.id.vertical_ratio);

        furry_id = (TextView) findViewById(R.id.furry);
        furry_percentage_id = (TextView) findViewById(R.id.furry_percentage);
        corner_id = (TextView) findViewById(R.id.corner);
        bad_corners_id = (TextView) findViewById(R.id.bad_corner);
        nof_cards_id = (TextView) findViewById(R.id.nof_cards);

        mainLayout = (RelativeLayout) findViewById(R.id.LineLayout);
        line_view = (LineView) findViewById(R.id.LineView2);
//        line_view.setPointA(new PointF(0, 0));
//        line_view.setPointB(new PointF(0, 0));
        line_view.setLeftLine(new PointF(0, 0), new PointF(0, 0));
        line_view.setRightLine(new PointF(0, 0), new PointF(0, 0));

        line_view.setLeftOuterLine(new PointF(0, 0), new PointF(0, 0));
        line_view.setRightOuterLine(new PointF(0, 0), new PointF(0, 0));

        line_view.setTopLine(new PointF(0, 0), new PointF(0, 0));
        line_view.setBottomLine(new PointF(0, 0), new PointF(0, 0));
        line_view.setTopOuterLine(new PointF(0, 0), new PointF(0, 0));
        line_view.setBottomOuterLine(new PointF(0, 0), new PointF(0, 0));


        line_view.draw();

        line_width = (int) (0.25 * (double) 790);

        line_height = (int) (0.25 * (double) 1090);

        outer_line_width = (int) (0.35 * (double) 790);

        outer_line_height = (int) (0.35 * (double) 1090);


//        line_view.setOnTouchListener(onTouchListener());

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(790, 1090, conf); // this creates a MUTABLE bitmap
        click_image_id.setImageBitmap(bmp);
        // Camera_open button is for open the camera
        // and add the setOnClickListener in this button
       /* camera_open_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //shortCut();
                //return;

                if (!checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_READ_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "STORAGE PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                if (!checkPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        STORAGE_WRITE_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "STORAGE PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                if (!checkPermission(
                        Manifest.permission.CAMERA,
                        CAMERA_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "CAMERA PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }


                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri_1 = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_1);
                startActivityForResult(intent, pic_id);

//                ContentValues values_2 = new ContentValues();
//                values_2.put(MediaStore.Images.Media.TITLE, "New Picture 2");
//                values_2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera 2");
//                imageUri_2 = getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_2);
//                Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
//                startActivityForResult(intent_2, pic_id2);


//                // Create the camera_intent ACTION_IMAGE_CAPTURE
//                // it will open the camera for capture the image
//                Intent camera_intent
//                        = new Intent(MediaStore
//                        .ACTION_IMAGE_CAPTURE);
//
//                // Start the activity with camera_intent,
//                // and request pic id
//                startActivityForResult(camera_intent, pic_id);

            }
        });*/
        camera_open_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_READ_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "STORAGE PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                if (!checkPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        STORAGE_WRITE_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "STORAGE PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                if (!checkPermission(
                        Manifest.permission.CAMERA,
                        CAMERA_PERMISSION_CODE)) {

                    Toast.makeText(MainActivity.this,
                            "CAMERA PERMISSION REQUIRED",
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }
                //start photo fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.res_lay, new PhotoFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void checkPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS,
                        PERMISSION_ALL);
            }
            flagPermissions = false;
        }
        flagPermissions = true;

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Start pick image activity with chooser.
     */
    public void onSelectImageClick(View view) {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
        is_value = true;
    }

    // Function to check and request permission.
    public boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    requestCode);
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                    == PackageManager.PERMISSION_DENIED) {
                return false;
            } else {
                return true;
            }
        } else {
//            Toast.makeText(MainActivity.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT)
//                    .show();
            return true;
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this,
//                        "Camera Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_READ_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this,
//                        "Storage Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_WRITE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this,
//                        "Storage Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    // This method will help to retrieve the image
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        // Match the request 'pic id with requestCode
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && is_value == true) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            try {

                original_photo = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), result.getUri());
                //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i0);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_1 = new int[w * h];
            photo.getPixels(pixels_1, 0, w, 0, 0, w, h);


            Toast.makeText(MainActivity.this,
                    "Take Second Image",
                    Toast.LENGTH_SHORT);
            if (is_value == true) {
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
                is_value = false;
            }

            /*ContentValues values_2 = new ContentValues();
            values_2.put(MediaStore.Images.Media.TITLE, "New Picture 2");
            values_2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera 2");
            imageUri_2 = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_2);
            Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
            startActivityForResult(intent_2, pic_id2);*/

        } else if (requestCode == pic_id) {

            // BitMap is data structure of image file
            // which stor the image in memory
//            Bitmap photo = (Bitmap) data.getExtras()
//                    .get("data");

//            Bitmap photo = BitmapFactory.decodeResource(getResources(),R.mipmap.custom_rotated);

            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            try {
                original_photo = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri_1);
                //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i0);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_1 = new int[w * h];
            photo.getPixels(pixels_1, 0, w, 0, 0, w, h);


            Toast.makeText(MainActivity.this,
                    "Take Second Image",
                    Toast.LENGTH_SHORT);

            ContentValues values_2 = new ContentValues();
            values_2.put(MediaStore.Images.Media.TITLE, "New Picture 2");
            values_2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera 2");
            imageUri_2 = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_2);
            Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
            startActivityForResult(intent_2, pic_id2);

        } else if (requestCode == pic_id2) {


            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            try {
                original_photo = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri_2);
                //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i180);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_2 = new int[w * h];
            photo.getPixels(pixels_2, 0, w, 0, 0, w, h);


            if ((pixels_1 == null) || (pixels_2 == null)) {
                return;
            }

            //            int[] resultData = com.example.helloworld.NativeLib.Bitmap2Grey(piexls,w,h);
            resultData = NativeLib.startGrading(pixels_1, pixels_2, w, h);


//            Context context = getApplicationContext();
////            CharSequence text = "Hello toast!";
//            String text = String.valueOf(w)+" "+String.valueOf(h);
//
////            double left_x, right_x, top_y, bottom_y;
////            double x_percentage, y_percentage;
////            double x_percentage_exact, y_percentage_exact;
////            boolean status;
//
//
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();


            if (resultData.status != 0) {

                if (resultData.status == 1) {
                    status_id.setText("Not Graded");
                } else {
                    status_id.setText("Graded");
                }

                try {

                    Bitmap resultImage = Bitmap.createBitmap(resultData.output_width, resultData.output_height, Bitmap.Config.ARGB_8888);
                    resultImage.setPixels(resultData.output_image, 0, resultData.output_width, 0, 0, resultData.output_width, resultData.output_height);

                    // Set the image in imageview for display
                    click_image_id.setImageBitmap(resultImage);


                    ratio_id.setText(String.valueOf(resultData.x_percentage) + " : " + String.valueOf(resultData.y_percentage));


                    resultData.left_percentage = (double) Math.round(resultData.left_percentage * 100) / 100;
                    resultData.right_percentage = (double) Math.round(resultData.right_percentage * 100) / 100;
                    resultData.top_percentage = (double) Math.round(resultData.top_percentage * 100) / 100;
                    resultData.bottom_percentage = (double) Math.round(resultData.bottom_percentage * 100) / 100;


                    if (resultData.status == 1) {
                        grading_direction_id.setText("NA");
                    } else if (resultData.status == 2) {
                        grading_direction_id.setText("Horizontal");
                    } else if (resultData.status == 3) {
                        grading_direction_id.setText("Vertical");
                    }


                    horizontal_ratio_id.setText(String.valueOf(resultData.left_percentage) + " : " + String.valueOf(resultData.right_percentage));
                    vertical_ratio_id.setText(String.valueOf(resultData.top_percentage) + " : " + String.valueOf(resultData.bottom_percentage));

                    float[] mMatrixValue = new float[9];

                    //click_image_id.postInvalidate();
                    //click_image_id.refreshDrawableState();
                    //mainLayout.invalidate();
                    //mainLayout.requestLayout();

                    click_image_id.getImageMatrix().getValues(mMatrixValue);
                    mScaleX = mMatrixValue[Matrix.MSCALE_X];
                    mScaleY = mMatrixValue[Matrix.MSCALE_Y];

//                float convert_x = resultData.left_line * mScaleX;

//                Log.d("ADebugTag", "Value: " + Float.toString(mScaleX) + " " + Float.toString(mScaleY) +" " + String.valueOf(resultData.left_line) +" " + convert_x);


/*
                canvas = new Canvas(resultImage);
                //imageView.setImageBitmap(bitmap);

                paint = new Paint();
                paint.setColor(Color.BLACK);

                canvas.drawLine(resultData.left_line, 0+100, resultData.left_line, resultData.output_height-100, paint);
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawLine(resultData.right_line, 0+100, resultData.right_line, resultData.output_height-100, paint);
                //https://github.com/ahmadarif/AndroidDrawLine/blob/5bb34787ac3f9f9331e0808e9ee19293fc6cf5b9/app/src/main/java/com/ahmadarif/drawline/MainActivity.java#L68
                //https://github.com/dangziming/NEOGallery/blob/ad6103ce46f069812922f9e1c0b65d757b999cd2/src/com/android/gallery3d/filtershow/crop/CropDrawingUtils.java

                click_image_id.postInvalidate();

                // Set the image in imageview for display
               // click_image_id.setImageBitmap(resultImage); */

//                line_view.setPointA(new PointF((float)resultData.left_line * mScaleX, 100 * mScaleY));
//                line_view.setPointB(new PointF((float)resultData.left_line * mScaleX, 990 * mScaleY));

                    line_view.setLeftLine(new PointF((float) resultData.left_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.left_line * mScaleX, (1090 - line_height) * mScaleY));
                    line_view.setRightLine(new PointF((float) resultData.right_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.right_line * mScaleX, (1090 - line_height) * mScaleY));

                    line_view.setLeftOuterLine(new PointF((float) resultData.outer_left * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_left * mScaleX, (1090 - outer_line_height) * mScaleY));
                    line_view.setRightOuterLine(new PointF((float) resultData.outer_right * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_right * mScaleX, (1090 - outer_line_height) * mScaleY));

                    line_view.setTopLine(new PointF(line_width * mScaleX, (float) resultData.top_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.top_line * mScaleY));
                    line_view.setBottomLine(new PointF(line_width * mScaleX, (float) resultData.bottom_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.bottom_line * mScaleY));

                    line_view.setTopOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_top * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_top * mScaleY));
                    line_view.setBottomOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_bottom * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_bottom * mScaleY));

                    line_view.draw();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultData.status == 0) {
                status_id.setText("Not Detected");
                Bitmap resultImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                resultImage.setPixels(resultData.image_data, 0, w, 0, 0, w, h);

                // Set the image in imageview for display
                click_image_id.setImageBitmap(resultImage);
                ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                grading_direction_id.setText("NA");
                horizontal_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                vertical_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
            }

            if (resultData.furry) {
                furry_id.setText("Pass");
            } else {
                furry_id.setText("Fail");
            }

            if (resultData.corner) {
                corner_id.setText("Pass");
            } else {
                corner_id.setText("Fail");
            }

            resultData.furry_percentage = (double) Math.round(resultData.furry_percentage * 100) / 100;

            furry_percentage_id.setText(String.valueOf(resultData.furry_percentage) + " %");
            bad_corners_id.setText(String.valueOf(resultData.bad_corners));
            nof_cards_id.setText(String.valueOf(resultData.nof_cards));
//            ratio_exact_id.setText(String.valueOf(resultData.x_percentage_exact)+" : "+String.valueOf(resultData.y_percentage_exact));
//
//            left_id.setText(String.valueOf(resultData.left_x));
//            right_id.setText(String.valueOf(resultData.right_x));
//            top_id.setText(String.valueOf(resultData.top_y));
//            bottom_id.setText(String.valueOf(resultData.bottom_y));

//            CenteringResult manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
//                    resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);


            line_view.setOnTouchListener(onTouchListener());


        } else if (is_value == false && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);


            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            try {
                original_photo = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), result.getUri());
                //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i180);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_2 = new int[w * h];
            photo.getPixels(pixels_2, 0, w, 0, 0, w, h);


            if ((pixels_1 == null) || (pixels_2 == null)) {
                return;
            }

            //            int[] resultData = com.example.helloworld.NativeLib.Bitmap2Grey(piexls,w,h);
            resultData = NativeLib.startGrading(pixels_1, pixels_2, w, h);


//            Context context = getApplicationContext();
////            CharSequence text = "Hello toast!";
//            String text = String.valueOf(w)+" "+String.valueOf(h);
//
////            double left_x, right_x, top_y, bottom_y;
////            double x_percentage, y_percentage;
////            double x_percentage_exact, y_percentage_exact;
////            boolean status;
//
//
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();


            if (resultData.status != 0) {

                if (resultData.status == 1) {
                    status_id.setText("Not Graded");
                } else {
                    status_id.setText("Graded");
                }

                try {
                    Bitmap resultImage = Bitmap.createBitmap(resultData.output_width, resultData.output_height, Bitmap.Config.ARGB_8888);
                    resultImage.setPixels(resultData.output_image, 0, resultData.output_width, 0, 0, resultData.output_width, resultData.output_height);

                    // Set the image in imageview for display
                    click_image_id.setImageBitmap(resultImage);


                    ratio_id.setText(String.valueOf(resultData.x_percentage) + " : " + String.valueOf(resultData.y_percentage));


                    resultData.left_percentage = (double) Math.round(resultData.left_percentage * 100) / 100;
                    resultData.right_percentage = (double) Math.round(resultData.right_percentage * 100) / 100;
                    resultData.top_percentage = (double) Math.round(resultData.top_percentage * 100) / 100;
                    resultData.bottom_percentage = (double) Math.round(resultData.bottom_percentage * 100) / 100;


                    if (resultData.status == 1) {
                        grading_direction_id.setText("NA");
                    } else if (resultData.status == 2) {
                        grading_direction_id.setText("Horizontal");
                    } else if (resultData.status == 3) {
                        grading_direction_id.setText("Vertical");
                    }


                    horizontal_ratio_id.setText(String.valueOf(resultData.left_percentage) + " : " + String.valueOf(resultData.right_percentage));
                    vertical_ratio_id.setText(String.valueOf(resultData.top_percentage) + " : " + String.valueOf(resultData.bottom_percentage));

                    float[] mMatrixValue = new float[9];

                    //click_image_id.postInvalidate();
                    //click_image_id.refreshDrawableState();
                    //mainLayout.invalidate();
                    //mainLayout.requestLayout();

                    click_image_id.getImageMatrix().getValues(mMatrixValue);
                    mScaleX = mMatrixValue[Matrix.MSCALE_X];
                    mScaleY = mMatrixValue[Matrix.MSCALE_Y];

//                float convert_x = resultData.left_line * mScaleX;

//                Log.d("ADebugTag", "Value: " + Float.toString(mScaleX) + " " + Float.toString(mScaleY) +" " + String.valueOf(resultData.left_line) +" " + convert_x);


/*
                canvas = new Canvas(resultImage);
                //imageView.setImageBitmap(bitmap);

                paint = new Paint();
                paint.setColor(Color.BLACK);

                canvas.drawLine(resultData.left_line, 0+100, resultData.left_line, resultData.output_height-100, paint);
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawLine(resultData.right_line, 0+100, resultData.right_line, resultData.output_height-100, paint);
                //https://github.com/ahmadarif/AndroidDrawLine/blob/5bb34787ac3f9f9331e0808e9ee19293fc6cf5b9/app/src/main/java/com/ahmadarif/drawline/MainActivity.java#L68
                //https://github.com/dangziming/NEOGallery/blob/ad6103ce46f069812922f9e1c0b65d757b999cd2/src/com/android/gallery3d/filtershow/crop/CropDrawingUtils.java

                click_image_id.postInvalidate();

                // Set the image in imageview for display
               // click_image_id.setImageBitmap(resultImage); */

//                line_view.setPointA(new PointF((float)resultData.left_line * mScaleX, 100 * mScaleY));
//                line_view.setPointB(new PointF((float)resultData.left_line * mScaleX, 990 * mScaleY));

                    line_view.setLeftLine(new PointF((float) resultData.left_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.left_line * mScaleX, (1090 - line_height) * mScaleY));
                    line_view.setRightLine(new PointF((float) resultData.right_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.right_line * mScaleX, (1090 - line_height) * mScaleY));

                    line_view.setLeftOuterLine(new PointF((float) resultData.outer_left * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_left * mScaleX, (1090 - outer_line_height) * mScaleY));
                    line_view.setRightOuterLine(new PointF((float) resultData.outer_right * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_right * mScaleX, (1090 - outer_line_height) * mScaleY));

                    line_view.setTopLine(new PointF(line_width * mScaleX, (float) resultData.top_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.top_line * mScaleY));
                    line_view.setBottomLine(new PointF(line_width * mScaleX, (float) resultData.bottom_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.bottom_line * mScaleY));

                    line_view.setTopOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_top * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_top * mScaleY));
                    line_view.setBottomOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_bottom * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_bottom * mScaleY));

                    line_view.draw();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultData.status == 0) {
                status_id.setText("Not Detected");
                Bitmap resultImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                resultImage.setPixels(resultData.image_data, 0, w, 0, 0, w, h);

                // Set the image in imageview for display
                click_image_id.setImageBitmap(resultImage);
                ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                grading_direction_id.setText("NA");
                horizontal_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                vertical_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
            }

            if (resultData.furry) {
                furry_id.setText("Pass");
            } else {
                furry_id.setText("Fail");
            }

            if (resultData.corner) {
                corner_id.setText("Pass");
            } else {
                corner_id.setText("Fail");
            }

            resultData.furry_percentage = (double) Math.round(resultData.furry_percentage * 100) / 100;

            furry_percentage_id.setText(String.valueOf(resultData.furry_percentage) + " %");
            bad_corners_id.setText(String.valueOf(resultData.bad_corners));
            nof_cards_id.setText(String.valueOf(resultData.nof_cards));
//            ratio_exact_id.setText(String.valueOf(resultData.x_percentage_exact)+" : "+String.valueOf(resultData.y_percentage_exact));
//
//            left_id.setText(String.valueOf(resultData.left_x));
//            right_id.setText(String.valueOf(resultData.right_x));
//            top_id.setText(String.valueOf(resultData.top_y));
//            bottom_id.setText(String.valueOf(resultData.bottom_y));

//            CenteringResult manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
//                    resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);


            line_view.setOnTouchListener(onTouchListener());


        }

    }

    protected void shortCut() {

        {

            // BitMap is data structure of image file
            // which stor the image in memory
//            Bitmap photo = (Bitmap) data.getExtras()
//                    .get("data");

//            Bitmap photo = BitmapFactory.decodeResource(getResources(),R.mipmap.custom_rotated);

            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            {
                //original_photo = MediaStore.Images.Media.getBitmap(
                //        getContentResolver(), imageUri_1);
                original_photo = BitmapFactory.decodeResource(getResources(), R.mipmap.i0);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_1 = new int[w * h];
            photo.getPixels(pixels_1, 0, w, 0, 0, w, h);

/*
            Toast.makeText(MainActivity.this,
                    "Take Second Image",
                    Toast.LENGTH_SHORT);

            ContentValues values_2 = new ContentValues();
            values_2.put(MediaStore.Images.Media.TITLE, "New Picture 2");
            values_2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera 2");
            imageUri_2 = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_2);
            Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
            startActivityForResult(intent_2, pic_id2);
*/
        }

        {


            int w, h;

            Bitmap original_photo = null;
            Bitmap photo = null;
            Bitmap rotated_photo = null;
            {
                //original_photo = MediaStore.Images.Media.getBitmap(
                //        getContentResolver(), imageUri_2);
                original_photo = BitmapFactory.decodeResource(getResources(), R.mipmap.i180);

                int old_w = original_photo.getWidth();
                int old_h = original_photo.getHeight();
                if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                    int resized_width = 2080;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                    photo = rotated_photo;
                } else {

                    int resized_width = 1560;
                    double factor = (double) original_photo.getWidth() / (double) resized_width;
                    int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                    photo = Bitmap.createScaledBitmap(
                            original_photo, resized_width, resized_height, false);
                }
            }
            w = photo.getWidth();
            h = photo.getHeight();

//                int[] piexls = new int[w * h];
            pixels_2 = new int[w * h];
            photo.getPixels(pixels_2, 0, w, 0, 0, w, h);


            if ((pixels_1 == null) || (pixels_2 == null)) {
                return;
            }

//            int[] resultData = com.example.helloworld.NativeLib.Bitmap2Grey(piexls,w,h);
            resultData = NativeLib.startGrading(pixels_1, pixels_2, w, h);


//            Context context = getApplicationContext();
////            CharSequence text = "Hello toast!";
//            String text = String.valueOf(w)+" "+String.valueOf(h);
//
////            double left_x, right_x, top_y, bottom_y;
////            double x_percentage, y_percentage;
////            double x_percentage_exact, y_percentage_exact;
////            boolean status;
//
//
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();


            if (resultData.status != 0) {

                if (resultData.status == 1) {
                    status_id.setText("Not Graded");
                } else {
                    status_id.setText("Graded");
                }


                Bitmap resultImage = Bitmap.createBitmap(resultData.output_width, resultData.output_height, Bitmap.Config.ARGB_8888);
                resultImage.setPixels(resultData.output_image, 0, resultData.output_width, 0, 0, resultData.output_width, resultData.output_height);

                // Set the image in imageview for display
                click_image_id.setImageBitmap(resultImage);


                ratio_id.setText(String.valueOf(resultData.x_percentage) + " : " + String.valueOf(resultData.y_percentage));


                resultData.left_percentage = (double) Math.round(resultData.left_percentage * 100) / 100;
                resultData.right_percentage = (double) Math.round(resultData.right_percentage * 100) / 100;
                resultData.top_percentage = (double) Math.round(resultData.top_percentage * 100) / 100;
                resultData.bottom_percentage = (double) Math.round(resultData.bottom_percentage * 100) / 100;


                if (resultData.status == 1) {
                    grading_direction_id.setText("NA");
                } else if (resultData.status == 2) {
                    grading_direction_id.setText("Horizontal");
                } else if (resultData.status == 3) {
                    grading_direction_id.setText("Vertical");
                }


                horizontal_ratio_id.setText(String.valueOf(resultData.left_percentage) + " : " + String.valueOf(resultData.right_percentage));
                vertical_ratio_id.setText(String.valueOf(resultData.top_percentage) + " : " + String.valueOf(resultData.bottom_percentage));

                float[] mMatrixValue = new float[9];

                //click_image_id.postInvalidate();
                //click_image_id.refreshDrawableState();
                //mainLayout.invalidate();
                //mainLayout.requestLayout();

                click_image_id.getImageMatrix().getValues(mMatrixValue);
                mScaleX = mMatrixValue[Matrix.MSCALE_X];
                mScaleY = mMatrixValue[Matrix.MSCALE_Y];

//                float convert_x = resultData.left_line * mScaleX;

//                Log.d("ADebugTag", "Value: " + Float.toString(mScaleX) + " " + Float.toString(mScaleY) +" " + String.valueOf(resultData.left_line) +" " + convert_x);


/*
                canvas = new Canvas(resultImage);
                //imageView.setImageBitmap(bitmap);

                paint = new Paint();
                paint.setColor(Color.BLACK);

                canvas.drawLine(resultData.left_line, 0+100, resultData.left_line, resultData.output_height-100, paint);
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawLine(resultData.right_line, 0+100, resultData.right_line, resultData.output_height-100, paint);
                //https://github.com/ahmadarif/AndroidDrawLine/blob/5bb34787ac3f9f9331e0808e9ee19293fc6cf5b9/app/src/main/java/com/ahmadarif/drawline/MainActivity.java#L68
                //https://github.com/dangziming/NEOGallery/blob/ad6103ce46f069812922f9e1c0b65d757b999cd2/src/com/android/gallery3d/filtershow/crop/CropDrawingUtils.java

                click_image_id.postInvalidate();

                // Set the image in imageview for display
               // click_image_id.setImageBitmap(resultImage); */

//                line_view.setPointA(new PointF((float)resultData.left_line * mScaleX, 100 * mScaleY));
//                line_view.setPointB(new PointF((float)resultData.left_line * mScaleX, 990 * mScaleY));

                line_view.setLeftLine(new PointF((float) resultData.left_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.left_line * mScaleX, (1090 - line_height) * mScaleY));
                line_view.setRightLine(new PointF((float) resultData.right_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.right_line * mScaleX, (1090 - line_height) * mScaleY));

                line_view.setLeftOuterLine(new PointF((float) resultData.outer_left * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_left * mScaleX, (1090 - outer_line_height) * mScaleY));
                line_view.setRightOuterLine(new PointF((float) resultData.outer_right * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_right * mScaleX, (1090 - outer_line_height) * mScaleY));

                line_view.setTopLine(new PointF(line_width * mScaleX, (float) resultData.top_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.top_line * mScaleY));
                line_view.setBottomLine(new PointF(line_width * mScaleX, (float) resultData.bottom_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.bottom_line * mScaleY));

                line_view.setTopOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_top * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_top * mScaleY));
                line_view.setBottomOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_bottom * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_bottom * mScaleY));

                line_view.draw();

            } else if (resultData.status == 0) {
                status_id.setText("Not Detected");
                Bitmap resultImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                resultImage.setPixels(resultData.image_data, 0, w, 0, 0, w, h);

                // Set the image in imageview for display
                click_image_id.setImageBitmap(resultImage);
                ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                grading_direction_id.setText("NA");
                horizontal_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                vertical_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
            }

            if (resultData.furry) {
                furry_id.setText("Pass");
            } else {
                furry_id.setText("Fail");
            }

            if (resultData.corner) {
                corner_id.setText("Pass");
            } else {
                corner_id.setText("Fail");
            }

            resultData.furry_percentage = (double) Math.round(resultData.furry_percentage * 100) / 100;

            furry_percentage_id.setText(String.valueOf(resultData.furry_percentage) + " %");
            bad_corners_id.setText(String.valueOf(resultData.bad_corners));
            nof_cards_id.setText(String.valueOf(resultData.nof_cards));
//            ratio_exact_id.setText(String.valueOf(resultData.x_percentage_exact)+" : "+String.valueOf(resultData.y_percentage_exact));
//
//            left_id.setText(String.valueOf(resultData.left_x));
//            right_id.setText(String.valueOf(resultData.right_x));
//            top_id.setText(String.valueOf(resultData.top_y));
//            bottom_id.setText(String.valueOf(resultData.bottom_y));

//            CenteringResult manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
//                    resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);


            line_view.setOnTouchListener(onTouchListener());


        }
    }

    @Override
    public void onGlobalLayout() {
        click_image_id.getViewTreeObserver().removeOnGlobalLayoutListener(this);

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
//                Toast.makeText(MainActivity.this,
//                        String.valueOf((int)((float)x/mScaleX))+" "+String.valueOf((int)((float)y/mScaleY)), Toast.LENGTH_SHORT)
//                        .show();
//                Toast.makeText(MainActivity.this,
//                        x+" "+(int)((float)x/mScaleX)+ " "+y+" "+(int)((float)y/mScaleY), Toast.LENGTH_SHORT)
//                        .show();

                int[] coordinates = new int[2];
                click_image_id.getLocationOnScreen(coordinates);

                x = x - coordinates[0];
                y = y - coordinates[1];

//                                Log.d("ADebugTag", "Value: " + x + " " + y + " " + Float.toString((int)((float)x/mScaleX)) + " " + Float.toString((int)((float)y/mScaleY))  +" ");


//                                Toast.makeText(MainActivity.this,
//                                        coordinates[0]+" "+ coordinates[1], Toast.LENGTH_SHORT)
//                        .show();

                CenteringResult manual_result = null;

                if ((x > (0.20 * line_view.getWidth())) && (x <= (0.80 * line_view.getWidth()))) {
                    //vertical
                    if (y <= (0.04 * line_view.getHeight())) {
                        //outer top
                        line_view.setTopOuterLine(new PointF(outer_line_width * mScaleX, (float) y), new PointF((790 - outer_line_width) * mScaleX, (float) y));
                        line_view.draw();
                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
                                resultData.outer_left, resultData.outer_right, (int) ((float) y / mScaleY), resultData.outer_bottom);
                        resultData.outer_top = (int) ((float) y / mScaleY);

                    } else if ((y > (0.04 * line_view.getHeight())) && (y <= (0.15 * line_view.getHeight()))) {
                        //top
                        line_view.setTopLine(new PointF(line_width * mScaleX, (float) y), new PointF((790 - line_width) * mScaleX, (float) y));
                        line_view.draw();
                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, (int) ((float) y / mScaleY), resultData.bottom_line,
                                resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);
                        resultData.top_line = (int) ((float) y / mScaleY);

                    } else if ((y > (0.85 * line_view.getHeight())) && (y <= (0.96 * line_view.getHeight()))) {
                        //bottom
                        line_view.setBottomLine(new PointF(line_width * mScaleX, (float) y), new PointF((790 - line_width) * mScaleX, (float) y));
                        line_view.draw();
                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, (int) ((float) y / mScaleY),
                                resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);
                        resultData.bottom_line = (int) ((float) y / mScaleY);

                    } else if (y > (0.96 * line_view.getHeight())) {
                        //bottom outer
                        line_view.setBottomOuterLine(new PointF(outer_line_width * mScaleX, (float) y), new PointF((790 - outer_line_width) * mScaleX, (float) y));
                        line_view.draw();
                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
                                resultData.outer_left, resultData.outer_right, resultData.outer_top, (int) ((float) y / mScaleY));
                        resultData.outer_bottom = (int) ((float) y / mScaleY);
                    } else {
                        return false;
                    }
                } else if ((y > (0.15 * line_view.getWidth())) && (y <= (0.85 * line_view.getWidth()))) {


                    if (x <= (0.06 * line_view.getWidth())) {
                        //outer left
                        line_view.setLeftOuterLine(new PointF((float) x, outer_line_height * mScaleY), new PointF((float) x, (1090 - outer_line_height) * mScaleY));
                        line_view.draw();

                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
                                (int) ((float) x / mScaleX), resultData.outer_right, resultData.outer_top, resultData.outer_bottom);
                        resultData.outer_left = (int) ((float) x / mScaleX);
                    } else if ((x > (0.06 * line_view.getWidth())) && (x <= (0.20 * line_view.getWidth()))) {
                        //left
                        line_view.setLeftLine(new PointF((float) x, line_height * mScaleY), new PointF((float) x, (1090 - line_height) * mScaleY));
                        line_view.draw();

                        manual_result = NativeLib.manualGrade((int) ((float) x / mScaleX), resultData.right_line, resultData.top_line, resultData.bottom_line,
                                resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);
                        resultData.left_line = (int) ((float) x / mScaleX);
                    } else if ((x > (0.20 * line_view.getWidth())) && (x <= (0.94 * line_view.getWidth()))) {
                        //right
                        line_view.setRightLine(new PointF((float) x, line_height * mScaleY), new PointF((float) x, (1090 - line_height) * mScaleY));
                        line_view.draw();

                        manual_result = NativeLib.manualGrade(resultData.left_line, (int) ((float) x / mScaleX), resultData.top_line, resultData.bottom_line,
                                resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);
                        resultData.right_line = (int) ((float) x / mScaleX);
                    } else if (x > (0.94 * line_view.getWidth())) {
                        //outer right
                        line_view.setRightOuterLine(new PointF((float) x, outer_line_height * mScaleY), new PointF((float) x, (1090 - outer_line_height) * mScaleY));
                        line_view.draw();

                        manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
                                resultData.outer_left, (int) ((float) x / mScaleX), resultData.outer_top, resultData.outer_bottom);
                        resultData.outer_right = (int) ((float) x / mScaleX);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }


//                line_view.setPointA(new PointF((float)x, 100 * mScaleY));
//                line_view.setPointB(new PointF((float)x, 990 * mScaleY));
//                
//                line_view.draw();

//                CenteringResult manual_result = NativeLib.manualGrade((int)((float)x/mScaleX), resultData.right_line, resultData.top_line, resultData.bottom_line,
//                        resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);


                status_id.setText("Manual");

//                Bitmap resultImage = Bitmap.createBitmap(resultData.output_width, resultData.output_height, Bitmap.Config.ARGB_8888);
//                resultImage.setPixels(resultData.output_image, 0, resultData.output_width, 0, 0, resultData.output_width, resultData.output_height);
//
//                // Set the image in imageview for display
//                click_image_id.setImageBitmap(resultImage);


                ratio_id.setText(String.valueOf(manual_result.x_percentage) + " : " + String.valueOf(manual_result.y_percentage));


                manual_result.left_percentage = (double) Math.round(manual_result.left_percentage * 100) / 100;
                manual_result.right_percentage = (double) Math.round(manual_result.right_percentage * 100) / 100;
                manual_result.top_percentage = (double) Math.round(manual_result.top_percentage * 100) / 100;
                manual_result.bottom_percentage = (double) Math.round(manual_result.bottom_percentage * 100) / 100;


                if (manual_result.status == 1) {
                    grading_direction_id.setText("NA");
                } else if (manual_result.status == 2) {
                    grading_direction_id.setText("Horizontal");
                } else if (manual_result.status == 3) {
                    grading_direction_id.setText("Vertical");
                }


                horizontal_ratio_id.setText(String.valueOf(manual_result.left_percentage) + " : " + String.valueOf(manual_result.right_percentage));
                vertical_ratio_id.setText(String.valueOf(manual_result.top_percentage) + " : " + String.valueOf(manual_result.bottom_percentage));


/*
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
//                        xDelta = y - lParams.rightMargin;
//                        yDelta = y - lParams.topMargin;
//                        Toast.makeText(MainActivity.this,
//                                "First", Toast.LENGTH_SHORT)
//                                .show();
                        break;

                    case MotionEvent.ACTION_UP:


//                        Toast.makeText(MainActivity.this,
//                                "I'm here!", Toast.LENGTH_SHORT)
//                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x + xDelta - yDelta  ;
//                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = x  ;
//                        layoutParams.bottomMargin = y - yDelta;
                        view.setLayoutParams(layoutParams);
//                        Toast.makeText(MainActivity.this,
//                                "Third", Toast.LENGTH_SHORT)
//                                .show();
                        break;
                }

 */

                mainLayout.invalidate();
                return true;
            }
        };
    }

    @Override
    public void onFragmentInteraction(Bitmap bitmap, int value) {
        if (bitmap != null) {
            Uri imageUri = getImageUri(bitmap);
            /*ImageFragment imageFragment = new ImageFragment();
            imageFragment.imageSetupFragment(bitmap);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.res_photo_layout, imageFragment)
                    .addToBackStack(null)
                    .commit();*/

            if (value == 10) {

                // BitMap is data structure of image file
                // which stor the image in memory
//            Bitmap photo = (Bitmap) data.getExtras()
//                    .get("data");

//            Bitmap photo = BitmapFactory.decodeResource(getResources(),R.mipmap.custom_rotated);

                int w, h;

                Bitmap original_photo = null;
                Bitmap photo = null;
                Bitmap rotated_photo = null;
                try {
                    original_photo = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i0);

                    int old_w = original_photo.getWidth();
                    int old_h = original_photo.getHeight();
                    if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                        int resized_width = 2080;
                        double factor = (double) original_photo.getWidth() / (double) resized_width;
                        int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                        photo = Bitmap.createScaledBitmap(
                                original_photo, resized_width, resized_height, false);


                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                        photo = rotated_photo;
                    } else {

                        int resized_width = 1560;
                        double factor = (double) original_photo.getWidth() / (double) resized_width;
                        int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                        photo = Bitmap.createScaledBitmap(
                                original_photo, resized_width, resized_height, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                w = photo.getWidth();
                h = photo.getHeight();

//                int[] piexls = new int[w * h];
                pixels_1 = new int[w * h];
                photo.getPixels(pixels_1, 0, w, 0, 0, w, h);


                Toast.makeText(MainActivity.this,
                        "Take Second Image",
                        Toast.LENGTH_SHORT);

               /* ContentValues values_2 = new ContentValues();
                values_2.put(MediaStore.Images.Media.TITLE, "New Picture 2");
                values_2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera 2");
                imageUri_2 = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values_2);
                Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
                startActivityForResult(intent_2, pic_id2);*/

             /*   if (!flagPermissions) {
                    checkPermissions();
                    return;
                }
                //start photo fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.res_photo_layout, new PhotoFragment())
                        .addToBackStack(null)
                        .commit();*/

                // } else if (value == pic_id2) {


                // int w, h;

                //  Bitmap original_photo = photo;
                //Bitmap photo = null;
                // Bitmap rotated_photo = null;
                try {
                    original_photo = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    //original_photo = BitmapFactory.decodeResource(getResources(),R.mipmap.i180);

                    int old_w = original_photo.getWidth();
                    int old_h = original_photo.getHeight();
                    if (old_w > old_h) {
//                    int temp = old_w;
//                    old_w = old_h;
//                    old_h = temp;

                        int resized_width = 2080;
                        double factor = (double) original_photo.getWidth() / (double) resized_width;
                        int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

                        photo = Bitmap.createScaledBitmap(
                                original_photo, resized_width, resized_height, false);


                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        rotated_photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
//                    original_photo = rotated_photo;
                        photo = rotated_photo;
                    } else {

                        int resized_width = 1560;
                        double factor = (double) original_photo.getWidth() / (double) resized_width;
                        int resized_height = (int) ((double) original_photo.getHeight() / factor); //2080 for redmi

//                photo = Bitmap.createScaledBitmap(
//                        original_photo, 1560, 2080, false);
                        photo = Bitmap.createScaledBitmap(
                                original_photo, resized_width, resized_height, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                w = photo.getWidth();
                h = photo.getHeight();

//                int[] piexls = new int[w * h];
                pixels_2 = new int[w * h];
                photo.getPixels(pixels_2, 0, w, 0, 0, w, h);


                if ((pixels_1 == null) || (pixels_2 == null)) {
                    return;
                }

                //            int[] resultData = com.example.helloworld.NativeLib.Bitmap2Grey(piexls,w,h);
                resultData = NativeLib.startGrading(pixels_1, pixels_2, w, h);


//            Context context = getApplicationContext();
////            CharSequence text = "Hello toast!";
//            String text = String.valueOf(w)+" "+String.valueOf(h);
//
////            double left_x, right_x, top_y, bottom_y;
////            double x_percentage, y_percentage;
////            double x_percentage_exact, y_percentage_exact;
////            boolean status;
//
//
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();


                if (resultData.status != 0) {

                    if (resultData.status == 1) {
                        status_id.setText("Not Graded");
                    } else {
                        status_id.setText("Graded");
                    }

                    try {
                        Bitmap resultImage = Bitmap.createBitmap(resultData.output_width, resultData.output_height, Bitmap.Config.ARGB_8888);
                        resultImage.setPixels(resultData.output_image, 0, resultData.output_width, 0, 0, resultData.output_width, resultData.output_height);

                        // Set the image in imageview for display
                        click_image_id.setImageBitmap(resultImage);


                        ratio_id.setText(String.valueOf(resultData.x_percentage) + " : " + String.valueOf(resultData.y_percentage));


                        resultData.left_percentage = (double) Math.round(resultData.left_percentage * 100) / 100;
                        resultData.right_percentage = (double) Math.round(resultData.right_percentage * 100) / 100;
                        resultData.top_percentage = (double) Math.round(resultData.top_percentage * 100) / 100;
                        resultData.bottom_percentage = (double) Math.round(resultData.bottom_percentage * 100) / 100;


                        if (resultData.status == 1) {
                            grading_direction_id.setText("NA");
                        } else if (resultData.status == 2) {
                            grading_direction_id.setText("Horizontal");
                        } else if (resultData.status == 3) {
                            grading_direction_id.setText("Vertical");
                        }


                        horizontal_ratio_id.setText(String.valueOf(resultData.left_percentage) + " : " + String.valueOf(resultData.right_percentage));
                        vertical_ratio_id.setText(String.valueOf(resultData.top_percentage) + " : " + String.valueOf(resultData.bottom_percentage));

                        float[] mMatrixValue = new float[9];

                        //click_image_id.postInvalidate();
                        //click_image_id.refreshDrawableState();
                        //mainLayout.invalidate();
                        //mainLayout.requestLayout();

                        click_image_id.getImageMatrix().getValues(mMatrixValue);
                        mScaleX = mMatrixValue[Matrix.MSCALE_X];
                        mScaleY = mMatrixValue[Matrix.MSCALE_Y];

//                float convert_x = resultData.left_line * mScaleX;

//                Log.d("ADebugTag", "Value: " + Float.toString(mScaleX) + " " + Float.toString(mScaleY) +" " + String.valueOf(resultData.left_line) +" " + convert_x);


/*
                canvas = new Canvas(resultImage);
                //imageView.setImageBitmap(bitmap);

                paint = new Paint();
                paint.setColor(Color.BLACK);

                canvas.drawLine(resultData.left_line, 0+100, resultData.left_line, resultData.output_height-100, paint);
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawLine(resultData.right_line, 0+100, resultData.right_line, resultData.output_height-100, paint);
                //https://github.com/ahmadarif/AndroidDrawLine/blob/5bb34787ac3f9f9331e0808e9ee19293fc6cf5b9/app/src/main/java/com/ahmadarif/drawline/MainActivity.java#L68
                //https://github.com/dangziming/NEOGallery/blob/ad6103ce46f069812922f9e1c0b65d757b999cd2/src/com/android/gallery3d/filtershow/crop/CropDrawingUtils.java

                click_image_id.postInvalidate();

                // Set the image in imageview for display
               // click_image_id.setImageBitmap(resultImage); */

//                line_view.setPointA(new PointF((float)resultData.left_line * mScaleX, 100 * mScaleY));
//                line_view.setPointB(new PointF((float)resultData.left_line * mScaleX, 990 * mScaleY));

                        line_view.setLeftLine(new PointF((float) resultData.left_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.left_line * mScaleX, (1090 - line_height) * mScaleY));
                        line_view.setRightLine(new PointF((float) resultData.right_line * mScaleX, line_height * mScaleY), new PointF((float) resultData.right_line * mScaleX, (1090 - line_height) * mScaleY));

                        line_view.setLeftOuterLine(new PointF((float) resultData.outer_left * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_left * mScaleX, (1090 - outer_line_height) * mScaleY));
                        line_view.setRightOuterLine(new PointF((float) resultData.outer_right * mScaleX, outer_line_height * mScaleY), new PointF((float) resultData.outer_right * mScaleX, (1090 - outer_line_height) * mScaleY));

                        line_view.setTopLine(new PointF(line_width * mScaleX, (float) resultData.top_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.top_line * mScaleY));
                        line_view.setBottomLine(new PointF(line_width * mScaleX, (float) resultData.bottom_line * mScaleY), new PointF((790 - line_width) * mScaleX, (float) resultData.bottom_line * mScaleY));

                        line_view.setTopOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_top * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_top * mScaleY));
                        line_view.setBottomOuterLine(new PointF(outer_line_width * mScaleX, (float) resultData.outer_bottom * mScaleY), new PointF((790 - outer_line_width) * mScaleX, (float) resultData.outer_bottom * mScaleY));

                        line_view.draw();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultData.status == 0) {
                    status_id.setText("Not Detected");
                    Bitmap resultImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                    resultImage.setPixels(resultData.image_data, 0, w, 0, 0, w, h);

                    // Set the image in imageview for display
                    click_image_id.setImageBitmap(resultImage);
                    ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                    grading_direction_id.setText("NA");
                    horizontal_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                    vertical_ratio_id.setText(String.valueOf(0) + " : " + String.valueOf(0));
                }

                if (resultData.furry) {
                    furry_id.setText("Pass");
                } else {
                    furry_id.setText("Fail");
                }

                if (resultData.corner) {
                    corner_id.setText("Pass");
                } else {
                    corner_id.setText("Fail");
                }

                resultData.furry_percentage = (double) Math.round(resultData.furry_percentage * 100) / 100;

                furry_percentage_id.setText(String.valueOf(resultData.furry_percentage) + " %");
                bad_corners_id.setText(String.valueOf(resultData.bad_corners));
                nof_cards_id.setText(String.valueOf(resultData.nof_cards));
//            ratio_exact_id.setText(String.valueOf(resultData.x_percentage_exact)+" : "+String.valueOf(resultData.y_percentage_exact));
//
//            left_id.setText(String.valueOf(resultData.left_x));
//            right_id.setText(String.valueOf(resultData.right_x));
//            top_id.setText(String.valueOf(resultData.top_y));
//            bottom_id.setText(String.valueOf(resultData.bottom_y));

//            CenteringResult manual_result = NativeLib.manualGrade(resultData.left_line, resultData.right_line, resultData.top_line, resultData.bottom_line,
//                    resultData.outer_left, resultData.outer_right, resultData.outer_top, resultData.outer_bottom);


                line_view.setOnTouchListener(onTouchListener());


            }
        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}