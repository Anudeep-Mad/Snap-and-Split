
        package com.admin.snapandsplit;

        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.ResolveInfo;
        import android.content.res.AssetManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Matrix;
        import android.graphics.drawable.Drawable;
        import android.media.ExifInterface;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.app.TaskStackBuilder;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.admin.snapandsplit.contact.ContactsPicker;
        import com.admin.snapandsplit.login.login.LoginActivity;
        import com.admin.snapandsplit.login.login.email.interactor.EmailLoginInteractorImpl;
        import com.admin.snapandsplit.login.login.email.presenter.EmailLoginPresenter;
        import com.admin.snapandsplit.login.login.email.presenter.EmailLoginPresenterImpl;
        import com.admin.snapandsplit.login.login.email.view.LoginActivityFragment;
        import com.admin.snapandsplit.notifications.NotificationActivity;
        import com.admin.snapandsplit.notifications.NotificationsPicker;
        //import com.admin.snapandsplit.notifications.User;
        import com.admin.snapandsplit.notifications.PaymentActivity;
        import com.admin.snapandsplit.utils.Constants;
        import com.admin.snapandsplit.utils.UpdateFirebaseLogin;
        import com.admin.snapandsplit.user.User;
        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.client.ValueEventListener;
        import com.googlecode.tesseract.android.TessBaseAPI;


        import com.facebook.login.LoginManager;

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.ArrayList;
        import java.util.List;

@SuppressWarnings("ALL")
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PACKAGE_NAME = "com.admin.snapandsplit";

    int GALLERY_IMAGE_REQUEST;

    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/";


    Uri imageUri = null;
    Uri outputFileUri = null;
    static TextView imageDetails = null;
    public static ImageView showImg = null;
    HomeActivity CameraActivity = null;
    static TextView cropDetails = null;
    String Path = null;
    protected boolean _taken;
    protected EditText _field;
    protected String _path;
    public static final String lang = "eng";

    protected static final String PHOTO_TAKEN = "photo_taken";

    private static final String TAG = "SimpleAndroidOCR.java";

    public static final int PICK_FROM_CAMERA = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private static final int PICK_FROM_FILE = 4;
    public String recognizedText;
    final Firebase myFirebaseRef = new Firebase("https://snapnsplit.firebaseio.com");
    final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    public static final int NOTIFICATION_ID = 1;

    public NotificationActivity noti = new NotificationActivity();

    String ss = noti.NotificationPicker();
    public static String us;
    public static String usid;

  //  public EmailLoginPresenterImpl us = new EmailLoginPresenterImpl();
    public TextView userid;
    public TextView recvid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] paths = new String[]{
                DATA_PATH, DATA_PATH + "tessdata/"
        };

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        // lang.traineddata file with the app (in assets folder)


        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                //gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }


       // super.onCreate(savedInstanceState);
        new PetitionValueEventListener ().execute();
        setContentView(R.layout.activity_home);
        CameraActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showImg = (ImageView) findViewById(R.id.showImg);
       // userid = (TextView) findViewById(R.id.imageDetails);
       // recvid = (TextView) findViewById(R.id.cropDetails);
       // userid.setText(user.getUid());
      //  recvid.setText(ss);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new ButtonClickHandler());

        _field = (EditText) findViewById(R.id.field);

        _path = DATA_PATH + "/ocr.jpg";



           /*     imageDetails = (TextView) findViewById(R.id.imageDetails);

                showImg = (ImageView) findViewById(R.id.showImg);


                /*************************** Camera Intent Start ************************/

        // Define the file-name to save photo taken by Camera activity

        //     String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

             /*   ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE, fileName);

                values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

                // imageUri is the current activity attribute, define and save it for later usage

                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

             /*   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                try {
                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, PICK_FROM_CAMERA);
                }
                catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }


            }



           // extractImage.start();

                /*************************** Camera Intent End ************************/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    //public File getCameraFile() {
    //  File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //return new File(dir, FILE_NAME);
    // }


    public class ButtonClickHandler implements View.OnClickListener {
        public void onClick(View view) {
            Log.v(TAG, "Starting Camera app");
            startCameraActivity();
        }
    }


    protected void startCameraActivity() {
        File file = new File(_path);
        Uri outputFileUri = Uri.fromFile(file);

        final Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, PICK_FROM_CAMERA);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "resultCode: " + resultCode);


        if (resultCode == -1) {

            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    /**
                     * After taking a picture, do the crop
                     */
                    doCrop();

                    break;


                case CROP_FROM_CAMERA:
                    Bundle extras = data.getExtras();
                    /**
                     * After cropping the image, get the bitmap of the cropped image and
                     * display it on imageview.
                     */
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");

                        showImg.setImageBitmap(photo);
                        outputFileUri = getImageUri(
                                HomeActivity.this, photo);
                    }
                    outputFileUri.getPath();


                    File f = new File(outputFileUri.getPath());
                    /**
                     * Delete the temporary image
                     */
                    if (f.exists())
                        f.delete();

                    break;

            }


            onPhotoTaken();
        } else {
            Log.v(TAG, "User cancelled");
        }

        openSelectPage();

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(HomeActivity.PHOTO_TAKEN, _taken);
    }

    @
            Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.getBoolean(HomeActivity.PHOTO_TAKEN)) {
            onPhotoTaken();

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            int GALLERY_IMAGE_REQUEST = 1;
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                UpdateFirebaseLogin.unauth();
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.remove ("uid");
                //editor.remove ("provider");
                editor.remove("user");
                editor.commit();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.settings:
                //  startActivity (new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /************
     * Convert Image Uri path to physical path
     **************/


    /////////////////
    protected void onPhotoTaken() {
        _taken = true;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        Log.v(TAG, "Orient: " + exifOrientation);

        int rotate = 0;

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
        }

        Log.v(TAG, "Rotation: " + rotate);

        if (rotate != 0) {

            // Getting width & height of the given image.
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            // Setting pre rotate
            Matrix mtx = new Matrix();
            mtx.preRotate(rotate);

            // Rotating Bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
        }

        // Convert to ARGB_8888, required by tess
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        // _image.setImageBitmap( bitmap );

        Log.v(TAG, "Before baseApi");

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(bitmap);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        Log.v(TAG, "OCRED TEXT: " + recognizedText);

        if (lang.equalsIgnoreCase("eng")) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        }

        recognizedText = recognizedText.trim();

        if (recognizedText.length() != 0) {
            _field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
            _field.setSelection(_field.getText().toString().length());
        }


    }
////


    ////////////////


    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(outputFileUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (outputFileUri != null) {
                            getContentResolver().delete(outputFileUri, null,
                                    null);
                            outputFileUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }


    public void openContactsPage() {
        Log.i(TAG, "Opening Contacts Page");
        startActivity(new Intent(HomeActivity.this, ContactsPicker.class));
        finish();

    }

    public void openNotificationsPage() {
        Log.i(TAG, "Opening Notifications Page");
        startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
        finish();

    }

    public void openSelectPage() {
        Log.i(TAG, "Opening Select Page");
        startActivity(new Intent(HomeActivity.this, SelectActivity.class));
        finish();

    }

    private class PetitionValueEventListener
            extends AsyncTask<Void, Void, Void> {
        public NotificationActivity noti = new NotificationActivity();
public LoginActivityFragment user = new LoginActivityFragment();


//String us = EmailLoginPresenterImpl.us;
String usid = EmailLoginInteractorImpl.usid;



        String ss = noti.NotificationPicker();

        @Override
        protected Void doInBackground(Void... params) {

            myFirebaseRef.child("users").child(usid).addChildEventListener(new ChildEventListener() {
                final User user = new User();
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    // System.out.println(dataSnapshot.getKey() + " was " + dataSnapshot.getValue());
                    // Log.d("obj", dataSnapshot.getKey() + " , " + dataSnapshot.getValue());
                    final String str = dataSnapshot.getValue().toString();

                    //String us = user.getUid();
                    System.out.println("the user id is " + usid + "the receiver id is " + ss);
                   if (usid == usid) {
                       sendNotification(str);
                    } else {

                       System.out.println(usid + "the receiver id is " + ss);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        return null;
        }
        public void sendNotification(String str) {

            // Use NotificationCompat.Builder to set up our notification.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this);

            //icon appears in device notification bar and right hand corner of notification
            builder.setSmallIcon(R.drawable.ic_stat_ic_notification);

            // This intent is fired when notification is clicked
       /*     Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
            PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this, 0, intent, 0);*/

            Intent resultIntent = new Intent (HomeActivity.this, PaymentActivity.class);
           // resultIntent.putExtra("petition", petition);



            builder.setSound(defaultSoundUri);
            // Set the intent that will fire when the user taps the notification.

            builder.setAutoCancel(true);

            // Large icon appears on the left of the notification
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

            // Content title, which appears in large type at the top of the notification
            builder.setContentTitle("Snap 'N' Split");
            // Content text, which appears in smaller text below the title
            builder.setContentText(str);

            // The subtext, which appears under the text on newer devices.
            // This will show-up in the devices with Android 4.2 and above only
            builder.setSubText("Tap to view documentation about notifications.");

            TaskStackBuilder stackBuilder = TaskStackBuilder.create (HomeActivity.this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack (HomeActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent (resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent (
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
           // mBuilder.setContentIntent(resultPendingIntent);
            builder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // Will display the notification in the notification bar
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

    }

    public static String sendUserId(User user){
         us = user.getUid();
        return us;
    }


}
