package com.wipro.codingexcercise.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.wipro.codingexcercise.R;


public class CommonUtility {
    public static final long MAX_SIZE = 5 * 1024 * 1024L; // 4MiB
    public static final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    private static final int BUFFER = 80000;
    private static final Pattern WEB_URL_PATTERN = Patterns.WEB_URL;
    private static final Pattern PHONE_PATTERN = Patterns.PHONE;
    public static int responsecode;
    public static Boolean nullJson = false;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private static AlertDialog alertDialogV7;

    /*****************************
     * IS INTERNET AVAILABLE
     ***************************************/

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    /*****************************
     * IS EMAIL ID VALID
     ***************************************/
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /*****************************
     * IS MOBILE NUMBER VALID
     ***************************************/
    public static boolean isMobileValid(String mobileno) {
        return mobileno.length() == 10 && mobileno.substring(0, 2).contains("7") && mobileno.substring(0, 2).contains("8") && mobileno.substring(0, 2).contains("9");
    }

    /*****************************
     * IS WEB URL VALID
     ***************************************/
    public static boolean isValidUrl(String url) {

        return WEB_URL_PATTERN.matcher(url).matches();
    }

    /*****************************
     * IS PHONE VALID
     ***************************************/
    public static boolean isValidPhone(String phone) {

        return PHONE_PATTERN.matcher(phone).matches();
    }

    /*****************************
     * IS FILE EXIST
     ***************************************/
    public static boolean isFileExist(String filePath) {
        boolean exist;
        File file = new File(filePath);
        exist = file.exists();

        return exist;
    }

    /*****************************
     * CONVERT PATH TO BYTE ARRAY
     ***************************************/
//get the image in byte
    public static byte[] getImageInByte(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("length is " + imagefile.length());
        byte[] bFile = new byte[(int) imagefile.length()];
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    /*****************************
     * SCALLED BITMAP IMAGE
     ***************************************/
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width,
                height, filter);
    }

    /*****************************
     * SCALLED BITMAP IMAGE ANOTHER WAY
     ***************************************/
    public static Bitmap scaleDown(Bitmap yourBitmap, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(yourBitmap, newWidth, newHeight, true);
    }

    /***************************
     * get real path from image URI
     ****************************/
    public static String getPath(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /************
     * CALCULATE THE IMAGE SAMPLE SIZE to scale
     ***********/
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /***************
     * GET BITMAP FROM IMAHE URI
     *********************/
    public static Bitmap getBitMapFromUri(Context context, Uri uri) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp;
    }

    /**********************
     * GET BITMAP IMAGE FROM URL
     *************************/
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*****************************
     * SAVE IMAGE URL TO SD CARD
     ************************/
    public static String saveImageToSdcard(Context context, String image_url) {

        String imagepath = Environment.getExternalStorageDirectory() + "/" + "localFileName.jpg";

        URL wallpaperURL;
        try {
            wallpaperURL = new URL(image_url);
            URLConnection connection = wallpaperURL.openConnection();
            InputStream inputStream = new BufferedInputStream(wallpaperURL.openStream(), 10240);

            File cacheFile = new File(Environment.getExternalStorageDirectory(), "localFileName.jpg");

            FileOutputStream outputStream = new FileOutputStream(cacheFile);

            byte buffer[] = new byte[1024];

            int dataSize;

            int loadedSize = 0;

            while ((dataSize = inputStream.read(buffer)) != -1) {
                loadedSize += dataSize;
                outputStream.write(buffer, 0, dataSize);
            }
            outputStream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imagepath;
    }

    /****************************
     * DOWNLOAD FILE FROM SERVER URL
     ****************************/
    public static void getFileFromUrl(Context context, String file_url) {
        try {
            //set the download URL, a url that points to a file on the internet
            //this is the file to be downloaded
            URL url = new URL(file_url);

            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //and connect!
            urlConnection.connect();

            //set the path where we want to save the file
            //in this case, going to save it on the root directory of the
            //sd card.
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, specifying the path, and the filename
            //which we want to save the file as.
            File file = new File(SDCardRoot, "somefile.ext");

            //this will be used to write the downloaded data into the file we created
            FileOutputStream fileOutput = new FileOutputStream(file);

            //this will be used in reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file
            int totalSize = urlConnection.getContentLength();
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength; //used to store a temporary size of the buffer

            //now, read through the input buffer and write the contents to the file
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                //this is where you would do something to report the prgress, like this maybe
//		                updateProgress(downloadedSize, totalSize);

            }
            //close the output stream when done
            fileOutput.close();

            //catch some possible errors...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************
     * GET SCREEN TYPE
     ***************************************/
    public static String getScreenType(Activity context) {

        String screenType = "";

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; //320 dip
        int height = dm.heightPixels; //533 dip

        int widthPix = (int) Math.ceil(dm.widthPixels * (dm.densityDpi / 160.0));
        if (widthPix == 320)
            screenType = "small";
        else if (widthPix == 480)
            screenType = "normal";
        else if (widthPix == 800)
            screenType = "large";
        else if (widthPix == 1000)
            screenType = "xlarge";

        return screenType;
    }

    /*****************************
     * GET THE DEVICE VERSION
     ***************************************/
    public static int getDeviceVersion() {

        return Build.VERSION.SDK_INT;
    }

    /*****************************
     * OPEN YOUR PUBLISHED APP IN PLAYSTORE
     ***************************************/
    public static void openApplicationInPlaystore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /*****************************
     * GET UR PUBLISHED APP URL
     ***************************************/
    public static String getPlaystoreUrl(Context context) {

        String playstore_url;

        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        playstore_url = "market://details?id=" + appPackageName;

        return playstore_url;
    }

    /*****************************
     * GET APPLICATION VERSION
     ***************************************/
    public static String getApplicationVersion(Context context) {
        String version = "";
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }


    public static void setOpenSansRegularTypeFace(TextView view) {

    }

    public static void justifyListViewHeightBasedOnChildren(GridView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
//            listItem.measure(0, 0);
            listItem.measure(UNBOUNDED, UNBOUNDED);
            totalHeight += listItem.getMeasuredHeight();
//            totalHeight += listItem.getMeasuredHeightAndState();

        }
        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (adapter.getCount() - 1);
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    /*************
     * GET AVAILABLE MEMORY
     ******************/
    public static String getAvailableInternalMemory(Context context) {

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, availableBlocks * blockSize);
    }

    public static void showOrderPopUp(final Context context, String title, String message) {
        if ((alertDialogV7 != null && alertDialogV7.isShowing() == false)) {
            alertDialogV7 = new AlertDialog.Builder(
                    context).create();
            // Setting Dialog Title
            alertDialogV7.setTitle(title);
            // Setting Dialog Message
            //alertDialog.setMessage("Please login to add to WishList");

            alertDialogV7.setMessage(message);
            // Setting Icon to Dialog
            alertDialogV7.setIcon(R.mipmap.ic_launcher);

            // Setting OK Button
            alertDialogV7.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //((HomeActivity) context).showLoginScreen(Constants.FROM_ADAPTER_SCREEN);
                    alertDialogV7.dismiss();
                }
            });
            // Showing Alert Message
            alertDialogV7.show();
        } else if ((alertDialogV7 == null)) {
            alertDialogV7 = new AlertDialog.Builder(
                    context).create();
            // Setting Dialog Title
            alertDialogV7.setTitle(title);
            // Setting Dialog Message
            //alertDialog.setMessage("Please login to add to WishList");

            alertDialogV7.setMessage(message);
            // Setting Icon to Dialog
            alertDialogV7.setIcon(R.mipmap.ic_launcher);

            // Setting OK Button
            alertDialogV7.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //((HomeActivity) context).showLoginScreen(Constants.FROM_ADAPTER_SCREEN);
                    alertDialogV7.dismiss();
                }
            });
            // Showing Alert Message
            alertDialogV7.show();
        }
    }

    public static void showSnackBarMessage(View layout, String message) {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_SHORT);

        View snackbarView = snackbar.getView();
        snackbar.setActionTextColor(Color.WHITE);
        snackbarView.setBackgroundColor(Color.parseColor("#f19b00"));
        snackbar.show();
    }

    public static long getCurrentTimeMilli() {
        Date date = new Date();
        return date.getTime();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPassword(String target) {
        if (target == null) {
            return false;
        } else {
            return target.length() >= 6;
        }
    }

    public static String getPrimaryEmailID(Context context) {
        String emailId = "";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                emailId = account.name;
                Log.i("EMAIL", "getPrimaryEmailID: " + emailId);
                break;
            }
        }
        return emailId;
    }

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Activity context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public static void deleteCache(Context context) {
        try {

            long size = context.getCacheDir().getTotalSpace();
            boolean isCacheDeleted = false;
            File dir = context.getCacheDir();

            if (size >= MAX_SIZE) {
                Log.d("da", "cache deleted 1" + "\n" + size);
                isCacheDeleted = deleteDir(dir);
            }
            if (isCacheDeleted)
                Log.d("asd", "cache deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }

    public static boolean getSpecialCharacterCount(String s) {
        Pattern regex = Pattern.compile("[$+|]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public static boolean checkingSpecialCharacterCount(String s) {
        Pattern regex = Pattern.compile("[$+*!@#%^&()?/<>,.|]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public static boolean getFirstCharacterSpecialCharacter(String s) {
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        return specialChars.contains(s.substring(0, 1));
    }

    public static void downloadFileFromUrl(Context context, String file_url) {
        int count;
        try {

            URL url = new URL(file_url);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());

            String directory_name = null;

            String STORAGE_PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + directory_name;

            File cacheDirectory = context.getFilesDir();
            STORAGE_PATH = cacheDirectory.getAbsolutePath() + "/" + "name";

            System.out.println("storage path is " + STORAGE_PATH);

            OutputStream output = new FileOutputStream(STORAGE_PATH);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
//				publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************GET LIST OF ASSETS FILE*******************/
    public static void getAssetFiles(Context context) {

        AssetManager assetManager = context.getAssets();

        try {
            String[] files = assetManager.list("Files");

        } catch (IOException e1) {
            e1.printStackTrace();

        }
    }

    /******************GET SPECIAL STRING FROM TEXTFILE*****************/

    public static String getTextFromTextFile(Context context, String path) {
        String content = "";
        InputStream inputStream;
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open("helloworld.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            content = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**************     ENCRIPT  TEXT FILE      ****************/
    static void encrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        // Here you read the cleartext.
        FileInputStream fis = new FileInputStream("data/cleartext");
        // This stream write the encrypted text. This stream will be wrapped by another stream.
        FileOutputStream fos = new FileOutputStream("data/encrypted");

        // Length is 16 byte
        // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        int b;
        byte[] d = new byte[8];
        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        // Flush and close streams.
        cos.flush();
        cos.close();
        fis.close();
    }

    /**************     DECRIPT  TEXT FILE      ****************/
    static void decrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileInputStream fis = new FileInputStream("data/encrypted");

        FileOutputStream fos = new FileOutputStream("data/decrypted");
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while ((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }

    /*****************************
     * CONVERT BYTE ARRAY TO BITMAP
     ***************************************/
    public Bitmap ByteArrayToBitmap(byte[] byteArray) {

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
                byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 100, 80, true);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // Bitmap bitmap123 = BitmapFactory.decodeByteArray(byteArray, 0,
        // byteArray.length);

        return bitmapScaled;
    }


     /*private void showRatingDialog(boolean forceUpdate){
        isUpdateDialogOpen = true;
        RobotoSlabBoldTextView tvTitle, tvUpdate, tvNotnow;
        LinearLayout linearLayout;
        View lineVert;
        RobotoSlabBoldTextView tvForceUpdate;
        final RatingBar ratingBar;
        LayoutInflater factory = LayoutInflater.from(this);
        final View ratingDialogView = factory.inflate(
                R.layout.dialog_rating_app, null);

        tvTitle = (RobotoSlabBoldTextView) ratingDialogView.findViewById(R.id.tvUpdateAppTitle);
        tvUpdate = (RobotoSlabBoldTextView) ratingDialogView.findViewById(R.id.tvUpdateAppUpdate);
        tvNotnow = (RobotoSlabBoldTextView) ratingDialogView.findViewById(R.id.tvUpdateAppNotNow);
        linearLayout = (LinearLayout) ratingDialogView.findViewById(R.id.llUpdate);
        tvForceUpdate = (RobotoSlabBoldTextView) ratingDialogView.findViewById(R.id.tvForceUpdate);
        lineVert = (View) ratingDialogView.findViewById(R.id.lineVert);

        ratingBar = (RatingBar) ratingDialogView.findViewById(R.id.ratingbarApp);
        ratingBar.setNumStars(5);
       *//* Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);*//*


        ratingDialog = new AlertDialog.Builder(this*//*,R.style.AppCompatAlertDialogStyle*//*).create();
        ratingDialog.setView(ratingDialogView);

        if (true) {
            linearLayout.setVisibility(View.INVISIBLE);
            lineVert.setVisibility(View.INVISIBLE);
            tvForceUpdate.setVisibility(View.VISIBLE);
        }

        ratingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isUpdateDialogOpen = false;
            }
        });

        tvNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.dismiss();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.dismiss();
                openPlaystore();
            }
        });

        tvForceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ratingBar.getRating() >= 3) {
                    openPlaystore();
                    ratingDialog.dismiss();
                } else {
//                    showDialogFeedback();
                    ratingDialog.dismiss();
                }
            }
        });
        // Showing Alert Message
        ratingDialog.show();
    }*/

    /*****************************
     * GET THE DEVICE NAME
     ***************************************/
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**************  END OF   ENCRIPT  TEXT FILE      ****************/

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /****************DECRIPTION AES *************/
    public void decryptFile(View view) {
        try {

            // Create FileInputStream to read from the encrypted image file
            FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/enimg.png");
            // Save the decrypted image
            //   saveFile(decrypt(key, fis),"deimg.png");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /****************** ENCRIPT AES*******************/
    public void encryptFile(View view) {

        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/img.png");

        // Write image data to ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        // Encrypt and save the image
        // saveFile(encrypt(key,baos.toByteArray()),"enimg.png");
    }

}
