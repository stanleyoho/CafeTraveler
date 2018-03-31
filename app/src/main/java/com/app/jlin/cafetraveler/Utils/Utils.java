package com.app.jlin.cafetraveler.Utils;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.app.jlin.cafetraveler.Constants.Constants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * public utils
 */
public class Utils {

    /** Parse Unicode to UTF-8*/
    /**
     * unicode 转换成 utf-8
     * @author fanhui
     * 2007-3-15
     * @param theString
     * @return
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /** 判斷是哪個捷運站 */
//    public static String checkMRT(String mrt){
//
//    }

    /** 判斷文字是否為數字 */
    public static boolean isNumber(String str) {
        if(str == null || str.length() <= 0){
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /** 判斷密碼是否符合規範 */
    public static boolean isPasswordMatcher(String password) {
        if (isEmpty(password)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?!.*[\\W_])(?!.*([A-Za-z0-9])\\1{5}).{6,20}$");
        return pattern.matcher(password).matches();
    }

    /** 取得密碼強度 */
    public static int getPasswordLevel(String password) {
        if (isEmpty(password)) {
            return 0;
        } else if (password.length() < 6) {
            return 1;
        } else {
            int modes = 1;
            for (int i = 0; i < password.length(); i ++) {
                //測試每一個字符的類別並統計一共有多少種模式
                int mode = charMode(Character.codePointAt(password,i));
                if (mode != 0) {
                    modes |= mode;
                }
            }

            int modeCount = 0;
            for (int i = 0; i < 4 ; i++) {
                if ((modes & 1) == 1) {
                    modeCount++;
                }
                modes >>>= 1;
            }

            return modeCount;
        }
    }

    private static int charMode(int charCode) {
        if (charCode >= 48 && charCode <= 57) { //數字
            return 2;
        }
        if (charCode >= 65 && charCode <= 90) { //大寫字母
            return 4;
        }
        if (charCode >= 97 && charCode <= 122) { //小寫
            return 8;
        }
        return 0;
    }

    /** 簡單判斷9~15碼的電話(開頭不為0) */
    public static boolean isPhoneNumberMatcher(String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[1-9][0-9]{8,14}$");
        return pattern.matcher(phoneNumber).matches();
    }

    public static boolean isUserCodeMatcher(String userCode) {
        if (isEmpty(userCode)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?!.*[^a-zA-Z0-9]).{4,20}$");
        return pattern.matcher(userCode).matches();
    }

    /** 隱藏 鍵盤 */
    public static void hideSoftInput(Context context, View view) {
        // 隱藏鍵盤
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService (Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * is null or its length is 0
     *
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isConnectNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting() && !networkInfo.isFailover()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 將Dialog設定為全版
     * */
    public static void setFullScreenDialog(Dialog dialog) {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = metrics.widthPixels;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = metrics.heightPixels - (int)ViewUtils.convertDpToPixel(24,dialog.getContext());
            win.setAttributes(lp);

            win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                    WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE|
                    WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 判斷目前版本需不需要向使用者請求權限
     * */
    public static boolean isNeedRequestPermission() {
        // 檢查目前裝置的 Android SDK Version 是否比 Android6.0 更高
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 仿Home鍵功能
     **/
    public static void onHomePressed(Context activityContext) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
        intent.addCategory(Intent.CATEGORY_HOME);
        activityContext.startActivity(intent);
    }

    /** 判斷連線狀態 */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Constants.TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Constants.TYPE_MOBILE;
        }
        return Constants.TYPE_NOT_CONNECTED;
    }

    /** 判斷網路是否有連線的問題 */
    public static boolean isConnectionNetwork(Context context){
        // 判斷網路沒有連線
        return getConnectivityStatus(context) != Constants.TYPE_NOT_CONNECTED;
    }

    public static String encryptionByMD5(String text) {
        String encryptionText = text;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(text.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for( int i = 0; i < md5Bytes.length; i++) {
                int val = ((int)md5Bytes[i])&0xff;
                if(val < 16)
                {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encryptionText = hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptionText;
    }

    public static String decryptByMD5(String encryptionText) {
        char[] a = encryptionText.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'l');
        }
        return new String(a);
    }

    /** 取得 SDCard Path */
    public static String getSdCardPath() {

        String path = "";

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sdPath = Environment.getExternalStorageDirectory();
            path = sdPath.getPath();
        }

        return path;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getUriRealPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
