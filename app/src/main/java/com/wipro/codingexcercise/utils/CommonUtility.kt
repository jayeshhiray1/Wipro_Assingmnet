package com.wipro.codingexcercise.utils

import android.accounts.AccountManager
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.GridView
import android.widget.TextView
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class CommonUtility {
    /*****************************
     * CONVERT BYTE ARRAY TO BITMAP
     */
    fun ByteArrayToBitmap(byteArray: ByteArray?): Bitmap {
        val arrayInputStream = ByteArrayInputStream(
                byteArray)
        val bitmap = BitmapFactory.decodeStream(arrayInputStream)
        val bitmapScaled = Bitmap.createScaledBitmap(bitmap, 100, 80, true)
        val options = BitmapFactory.Options()
        // Bitmap bitmap123 = BitmapFactory.decodeByteArray(byteArray, 0,
        // byteArray.length);
        return bitmapScaled
    }
    /*****************************
     * GET THE DEVICE NAME
     */
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    /**************  END OF   ENCRIPT  TEXT FILE       */
    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    /****************DECRIPTION AES  */
    fun decryptFile(view: View?) {
        try {

            // Create FileInputStream to read from the encrypted image file
            val fis = FileInputStream(Environment.getExternalStorageDirectory().toString() + "/enimg.png")
            // Save the decrypted image
            //   saveFile(decrypt(key, fis),"deimg.png");
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    /****************** ENCRIPT AES */
    fun encryptFile(view: View?) {
        val bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + "/img.png")

        // Write image data to ByteArrayOutputStream
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        // Encrypt and save the image
        // saveFile(encrypt(key,baos.toByteArray()),"enimg.png");
    }

    companion object {
        const val MAX_SIZE = 5 * 1024 * 1024L // 4MiB
        val UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        private const val BUFFER = 80000
        private val WEB_URL_PATTERN = Patterns.WEB_URL
        private val PHONE_PATTERN = Patterns.PHONE
        var responsecode = 0
        var nullJson = false
        var `is`: InputStream? = null
        var jObj: JSONObject? = null
        var json = ""
        private var alertDialogV7: AlertDialog? = null

        /*****************************
         * IS INTERNET AVAILABLE
         */

        fun isInternetAvailable(activity: AppCompatActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        /*****************************
         * IS EMAIL ID VALID
         */
        fun isEmailValid(email: String?): Boolean {
            var isValid = false
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        /*****************************
         * IS MOBILE NUMBER VALID
         */
        fun isMobileValid(mobileno: String): Boolean {
            return mobileno.length == 10 && mobileno.substring(0, 2).contains("7") && mobileno.substring(0, 2).contains("8") && mobileno.substring(0, 2).contains("9")
        }

        /*****************************
         * IS WEB URL VALID
         */
        fun isValidUrl(url: String?): Boolean {
            return WEB_URL_PATTERN.matcher(url).matches()
        }

        /*****************************
         * IS PHONE VALID
         */
        fun isValidPhone(phone: String?): Boolean {
            return PHONE_PATTERN.matcher(phone).matches()
        }

        /*****************************
         * IS FILE EXIST
         */
        fun isFileExist(filePath: String?): Boolean {
            val exist: Boolean
            val file = File(filePath)
            exist = file.exists()
            return exist
        }

        /*****************************
         * CONVERT PATH TO BYTE ARRAY
         */
        //get the image in byte
        fun getImageInByte(path: String?): ByteArray {
            val imagefile = File(path)
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(imagefile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            println("length is " + imagefile.length())
            val bFile = ByteArray(imagefile.length().toInt())
            val bm = BitmapFactory.decodeStream(fis)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            return baos.toByteArray()
        }

        /*****************************
         * SCALLED BITMAP IMAGE
         */
        fun scaleDown(realImage: Bitmap, maxImageSize: Float,
                      filter: Boolean): Bitmap {
            val ratio = Math.min(
                    maxImageSize / realImage.width,
                    maxImageSize / realImage.height)
            val width = Math.round(ratio * realImage.width)
            val height = Math.round(ratio * realImage.height)
            return Bitmap.createScaledBitmap(realImage, width,
                    height, filter)
        }

        /*****************************
         * SCALLED BITMAP IMAGE ANOTHER WAY
         */
        fun scaleDown(yourBitmap: Bitmap?, newWidth: Int, newHeight: Int): Bitmap {
            return Bitmap.createScaledBitmap(yourBitmap, newWidth, newHeight, true)
        }

        /***************************
         * get real path from image URI
         */
        fun getPath(context: Context, uri: Uri?): String {
            val cursor = context.contentResolver.query(uri, null, null, null, null)!!
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }

        /************
         * CALCULATE THE IMAGE SAMPLE SIZE to scale
         */
        fun calculateInSampleSize(
                options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 2
            if (height > reqHeight || width > reqWidth) {
                inSampleSize = if (width > height) {
                    Math.round(height.toFloat() / reqHeight.toFloat())
                } else {
                    Math.round(width.toFloat() / reqWidth.toFloat())
                }
            }
            return inSampleSize
        }

        /***************
         * GET BITMAP FROM IMAHE URI
         */
        fun getBitMapFromUri(context: Context, uri: Uri?): Bitmap? {
            var bmp: Bitmap? = null
            try {
                bmp = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            } catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return bmp
        }

        /**********************
         * GET BITMAP IMAGE FROM URL
         */
        fun getBitmapFromURL(src: String?): Bitmap? {
            return try {
                val url = URL(src)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        /*****************************
         * SAVE IMAGE URL TO SD CARD
         */
        fun saveImageToSdcard(context: Context?, image_url: String?): String {
            val imagepath = Environment.getExternalStorageDirectory().toString() + "/" + "localFileName.jpg"
            val wallpaperURL: URL
            try {
                wallpaperURL = URL(image_url)
                val connection = wallpaperURL.openConnection()
                val inputStream: InputStream = BufferedInputStream(wallpaperURL.openStream(), 10240)
                val cacheFile = File(Environment.getExternalStorageDirectory(), "localFileName.jpg")
                val outputStream = FileOutputStream(cacheFile)
                val buffer = ByteArray(1024)
                var dataSize: Int
                var loadedSize = 0
                while (inputStream.read(buffer).also { dataSize = it } != -1) {
                    loadedSize += dataSize
                    outputStream.write(buffer, 0, dataSize)
                }
                outputStream.close()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return imagepath
        }

        /****************************
         * DOWNLOAD FILE FROM SERVER URL
         */
        fun getFileFromUrl(context: Context?, file_url: String?) {
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                val url = URL(file_url)

                //create the new connection
                val urlConnection = url.openConnection() as HttpURLConnection

                //set up some things on the connection
                urlConnection.requestMethod = "GET"
                urlConnection.doOutput = true

                //and connect!
                urlConnection.connect()

                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.
                val SDCardRoot = Environment.getExternalStorageDirectory()
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                val file = File(SDCardRoot, "somefile.ext")

                //this will be used to write the downloaded data into the file we created
                val fileOutput = FileOutputStream(file)

                //this will be used in reading the data from the internet
                val inputStream = urlConnection.inputStream

                //this is the total size of the file
                val totalSize = urlConnection.contentLength
                //variable to store total downloaded bytes
                var downloadedSize = 0

                //create a buffer...
                val buffer = ByteArray(1024)
                var bufferLength: Int //used to store a temporary size of the buffer

                //now, read through the input buffer and write the contents to the file
                while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength)
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength
                    //this is where you would do something to report the prgress, like this maybe
//		                updateProgress(downloadedSize, totalSize);
                }
                //close the output stream when done
                fileOutput.close()

                //catch some possible errors...
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /*****************************
         * GET SCREEN TYPE
         */
        fun getScreenType(context: Activity): String {
            var screenType = ""
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels //320 dip
            val height = dm.heightPixels //533 dip
            val widthPix = Math.ceil(dm.widthPixels * (dm.densityDpi / 160.0)).toInt()
            if (widthPix == 320) screenType = "small" else if (widthPix == 480) screenType = "normal" else if (widthPix == 800) screenType = "large" else if (widthPix == 1000) screenType = "xlarge"
            return screenType
        }

        /*****************************
         * GET THE DEVICE VERSION
         */
        val deviceVersion: Int
            get() = Build.VERSION.SDK_INT

        /*****************************
         * OPEN YOUR PUBLISHED APP IN PLAYSTORE
         */
        fun openApplicationInPlaystore(context: Context) {
            val appPackageName = context.packageName // getPackageName() from Context or Activity object
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: ActivityNotFoundException) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }

        /*****************************
         * GET UR PUBLISHED APP URL
         */
        fun getPlaystoreUrl(context: Context): String {
            val playstore_url: String
            val appPackageName = context.packageName // getPackageName() from Context or Activity object
            playstore_url = "market://details?id=$appPackageName"
            return playstore_url
        }

        /*****************************
         * GET APPLICATION VERSION
         */
        fun getApplicationVersion(context: Context): String {
            var version = ""
            val pInfo: PackageInfo
            try {
                pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                version = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return version
        }

        fun setOpenSansRegularTypeFace(view: TextView?) {}
        fun justifyListViewHeightBasedOnChildren(listView: GridView) {
            val adapter = listView.adapter ?: return
            var totalHeight = 0
            for (i in 0 until adapter.count) {
                val listItem = adapter.getView(i, null, listView)
                //            listItem.measure(0, 0);
                listItem.measure(UNBOUNDED, UNBOUNDED)
                totalHeight += listItem.measuredHeight
                //            totalHeight += listItem.getMeasuredHeightAndState();
            }
            val par = listView.layoutParams
            par.height = totalHeight + (adapter.count - 1)
            listView.layoutParams = par
            listView.requestLayout()
        }

        /*************
         * GET AVAILABLE MEMORY
         */
        fun getAvailableInternalMemory(context: Context?): String {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            return Formatter.formatFileSize(context, availableBlocks * blockSize)
        }


        }

        fun showSnackBarMessage(layout: View?, message: String?) {
            val snackbar = Snackbar
                    .make(layout!!, message!!, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbar.setActionTextColor(Color.WHITE)
            snackbarView.setBackgroundColor(Color.parseColor("#f19b00"))
            snackbar.show()
        }

        val currentTimeMilli: Long
            get() {
                val date = Date()
                return date.time
            }

        fun isValidEmail(target: CharSequence?): Boolean {
            return if (target == null) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun isValidPassword(target: String?): Boolean {
            return if (target == null) {
                false
            } else {
                target.length >= 6
            }
        }

        fun getPrimaryEmailID(context: Context?): String {
            var emailId = ""
            val emailPattern = Patterns.EMAIL_ADDRESS // API level 8+
            val accounts = AccountManager.get(context).accounts
            for (account in accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    emailId = account.name
                    Log.i("EMAIL", "getPrimaryEmailID: $emailId")
                    break
                }
            }
            return emailId
        }

        fun convertDpToPixels(dp: Float, context: Context): Int {
            val resources = context.resources
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    resources.displayMetrics
            ).toInt()
        }

        fun getScreenWidth(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            return display.width
        }

        fun getScreenHeight(context: Activity): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            return display.height
        }

        fun deleteCache(context: Context) {
            try {
                val size = context.cacheDir.totalSpace
                var isCacheDeleted = false
                val dir = context.cacheDir
                if (size >= MAX_SIZE) {
                    Log.d("da", "cache deleted 1\n$size")
                    isCacheDeleted = deleteDir(dir)
                }
                if (isCacheDeleted) Log.d("asd", "cache deleted")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun deleteDir(dir: File?): Boolean {
            return if (dir != null && dir.isDirectory) {
                val children = dir.list()
                for (aChildren in children) {
                    val success = deleteDir(File(dir, aChildren))
                    if (!success) {
                        return false
                    }
                }
                dir.delete()
            } else if (dir != null && dir.isFile) dir.delete() else {
                false
            }
        }

        fun getSpecialCharacterCount(s: String?): Boolean {
            val regex = Pattern.compile("[$+|]")
            val matcher = regex.matcher(s)
            return matcher.find()
        }

        fun checkingSpecialCharacterCount(s: String?): Boolean {
            val regex = Pattern.compile("[$+*!@#%^&()?/<>,.|]")
            val matcher = regex.matcher(s)
            return matcher.find()
        }

        fun getFirstCharacterSpecialCharacter(s: String): Boolean {
            val specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,."
            return specialChars.contains(s.substring(0, 1))
        }

        fun downloadFileFromUrl(context: Context, file_url: String?) {
            var count: Int
            try {
                val url = URL(file_url)
                val conexion = url.openConnection()
                conexion.connect()
                val lenghtOfFile = conexion.contentLength
                Log.d("ANDRO_ASYNC", "Lenght of file: $lenghtOfFile")
                val input: InputStream = BufferedInputStream(url.openStream())
                val directory_name: String? = null
                var STORAGE_PATH = Environment.getExternalStorageDirectory()
                        .absolutePath + "/" + directory_name
                val cacheDirectory = context.filesDir
                STORAGE_PATH = cacheDirectory.absolutePath + "/" + "name"
                println("storage path is $STORAGE_PATH")
                val output: OutputStream = FileOutputStream(STORAGE_PATH)
                val data = ByteArray(1024)
                var total: Long = 0
                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    //				publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count)
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /***************GET LIST OF ASSETS FILE */
        fun getAssetFiles(context: Context) {
            val assetManager = context.assets
            try {
                val files = assetManager.list("Files")
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }

        /******************GET SPECIAL STRING FROM TEXTFILE */
        fun getTextFromTextFile(context: Context, path: String?): String {
            var content = ""
            val inputStream: InputStream
            val assetManager = context.assets
            try {
                inputStream = assetManager.open("helloworld.txt")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                content = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return content
        }

        /**************     ENCRIPT  TEXT FILE       */
        @Throws(IOException::class, NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidKeyException::class)
        fun encrypt() {
            // Here you read the cleartext.
            val fis = FileInputStream("data/cleartext")
            // This stream write the encrypted text. This stream will be wrapped by another stream.
            val fos = FileOutputStream("data/encrypted")

            // Length is 16 byte
            // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
            val sks = SecretKeySpec("MyDifficultPassw".toByteArray(), "AES")
            // Create cipher
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, sks)
            // Wrap the output stream
            val cos = CipherOutputStream(fos, cipher)
            // Write bytes
            var b: Int
            val d = ByteArray(8)
            while (fis.read(d).also { b = it } != -1) {
                cos.write(d, 0, b)
            }
            // Flush and close streams.
            cos.flush()
            cos.close()
            fis.close()
        }

        /**************     DECRIPT  TEXT FILE       */
        @Throws(IOException::class, NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidKeyException::class)
        fun decrypt() {
            val fis = FileInputStream("data/encrypted")
            val fos = FileOutputStream("data/decrypted")
            val sks = SecretKeySpec("MyDifficultPassw".toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, sks)
            val cis = CipherInputStream(fis, cipher)
            var b: Int
            val d = ByteArray(8)
            while (cis.read(d).also { b = it } != -1) {
                fos.write(d, 0, b)
            }
            fos.flush()
            fos.close()
            cis.close()
        }
    }
