package com.dragster.android.information.system.my.android.utilities;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;

import com.dragster.android.information.system.my.android.classes.Permissions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetSomething{
    private static ArrayList<Integer> arrayListForWidth = new ArrayList<>();
    private static ArrayList<Integer> arrayListForHeight = new ArrayList<>();



    public static String getCurrentSsID(Context context)
    {
        String ssid = null;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
        if (networkInfo.isConnected())
        {
            final WifiManager wifiManager = (WifiManager)context.getSystemService( context.WIFI_SERVICE );
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo !=null && !TextUtils.isEmpty( wifiInfo.getSSID() ))
            {
                ssid = wifiInfo.getSSID();
            }
            else
            {
                ssid = "No wifi connected";
            }
        }
        return ssid;
    }

    public static float getTotalStorage(boolean isSdCard) {
       long totalStorage;
        if (isSdCard) {

            StatFs statFs = new StatFs( Environment.getExternalStorageDirectory(  ).getPath() );
            totalStorage = statFs.getBlockSizeLong();

        } else
        {
            StatFs statFs = new StatFs( Environment.getExternalStorageDirectory().getPath() );
            totalStorage =(statFs.getBlockSizeLong() * statFs.getBlockCountLong());
        }

        return (totalStorage/(1024*1024));//in Mbs
    }




    @SuppressLint("ObsoleteSdkInt")
    public static long getAvailableStorage(boolean isSdCard)
    {
       long megAvailable;

        if (isSdCard)
        {
             StatFs stat = new StatFs( Environment.getExternalStorageDirectory().getPath() );
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >=
                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();

             } else {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            }
            megAvailable = bytesAvailable / (1024 * 1024);

        }
        else {

            StatFs stat = new StatFs( Environment.getExternalStorageDirectory().getPath() );
            long bytesAvailable;

                 bytesAvailable =   stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
              megAvailable = bytesAvailable / (1024 * 1024);
        }
        return megAvailable;
    }
    public static long getBackCameraPixels()
    {
        long backPixel_int = 0;
        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
//            Log.i("Back max W :", String.valueOf( Collections.max(arrayListForWidth) ) );              // Gives Maximum Width
//            Log.i("Back max H :", String.valueOf( Collections.max(arrayListForHeight) ) );                 // Gives Maximum Height
//            Log.i( "Back Camera pixels", String.valueOf( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight)) / 1024000) ) );
            backPixel_int = ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight)) / 1024000);
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        return backPixel_int +1;
    }
    public static long getBackCameraHeightPixels()
    {
        long backHeightPixel_int = 0;
        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
//            Log.i("Back max W :", String.valueOf( Collections.max(arrayListForWidth) ) );              // Gives Maximum Width
            backHeightPixel_int =  Collections.max(arrayListForHeight) ;            // Gives Maximum Height
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        return backHeightPixel_int ;
    }
    public static long getBackCameraWidthPixels()
    {
        long backWidthPixel_int = 0;
        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            backWidthPixel_int =  Collections.max(arrayListForWidth);              // Gives Maximum Width
        }
        camera.release();

        arrayListForWidth.clear();
        return backWidthPixel_int;
    }
    public static long getFrontCameraPixels()
    {
        long frontPixel_int = 0;
        Camera camera=Camera.open(1);       //  For Front Camera
        android.hardware.Camera.Parameters params1 = camera.getParameters();
        List sizes1 = params1.getSupportedPictureSizes();
        Camera.Size  result1 = null;
        for (int i=0;i<sizes1.size();i++){
            result1 = (Camera.Size) sizes1.get(i);
            arrayListForWidth.add(result1.width);
            arrayListForHeight.add(result1.height);
//            Log.debug("PictureSize", "Supported Size: " + result1.width + "height : " + result1.height);
//            System.out.println("FRONT PictureSize Supported Size: " + result1.width + "height : " + result1.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
//            Log.i("FRONT max W :", String.valueOf( Collections.max(arrayListForWidth) ) );
//            Log.i("FRONT max H :", String.valueOf( Collections.max(arrayListForHeight) ) );
//            Log.i("FRONT Megapixel :", String.valueOf( ( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 ) ) );
            frontPixel_int =   ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight)) / 1024000  );
        }

        camera.release();

        return frontPixel_int+1;
    }
    public static long getFrontHeightCameraPixels()
    {
        long frontHeightPixel_int = 0;
        Camera camera=Camera.open(1);       //  For Front Camera
        android.hardware.Camera.Parameters params1 = camera.getParameters();
        List sizes1 = params1.getSupportedPictureSizes();
        Camera.Size  result1 = null;
        for (int i=0;i<sizes1.size();i++){
            result1 = (Camera.Size) sizes1.get(i);
            arrayListForWidth.add(result1.width);
            arrayListForHeight.add(result1.height);
//            Log.debug("PictureSize", "Supported Size: " + result1.width + "height : " + result1.height);
//            System.out.println("FRONT PictureSize Supported Size: " + result1.width + "height : " + result1.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            frontHeightPixel_int = Collections.max(arrayListForHeight);
        }
        camera.release();

        return frontHeightPixel_int;
    }
    public static long getFrontCameraWidthPixels()
    {
        long frontWidthPixel_int = 0;
        Camera camera=Camera.open(1);       //  For Front Camera
        android.hardware.Camera.Parameters params1 = camera.getParameters();
        List sizes1 = params1.getSupportedPictureSizes();
        Camera.Size  result1 = null;
        for (int i=0;i<sizes1.size();i++){
            result1 = (Camera.Size) sizes1.get(i);
            arrayListForWidth.add(result1.width);
            arrayListForHeight.add(result1.height);
//            Log.debug("PictureSize", "Supported Size: " + result1.width + "height : " + result1.height);
//            System.out.println("FRONT PictureSize Supported Size: " + result1.width + "height : " + result1.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            frontWidthPixel_int  = Collections.max(arrayListForWidth);
        }

        camera.release();
        return frontWidthPixel_int;
    }

    // return left string from first ,
    public static String getLeftStringToThePoint(String s, String pointString) {
        String d = s;
        String ssDate = d.substring( d.lastIndexOf( pointString ) );
        d = d.replace( ssDate, "" );
        return d;
    }


    // return left string from first ,
    public static String getRightStringToThePoint(String s, String pointString) {
        String d = s;
        String ssDate = d.substring( d.lastIndexOf( pointString ) );
        return d;
    }
    // return left string from first ,
    public static String getStringLastPart(String s, int characters) {
        String d = s;
        if (s.length() > characters) {
            d = s.substring( s.length() - characters );
        }
        else
        {
            d = s;
        }
        return d;
    }
    public static float getPercentage(float totalData, float usedData)
    {

        return (usedData*100/totalData);
    }
    public static int getCelsiusToFahrenheit(int celsius)
    {
        return (celsius * 9)/5 + 32;
    }
    public static boolean getAllEmptyFoldersOfDir(File current){
        if(current.isDirectory()){
            File[] files = current.listFiles();
            if(files.length == 0){ //There is no file in this folder - safe to delete
//                System.out.println("Safe to delete - empty folder: " + current.getAbsolutePath());
                return true;
            } else {
                int totalFolderCount = 0;
                int emptyFolderCount = 0;
                for(File f : files){
                    if(f.isDirectory()){
                        totalFolderCount++;
                        if(getAllEmptyFoldersOfDir(f)){ //safe to delete
                            emptyFolderCount++;
                        }
                    }

                }
                if(totalFolderCount == files.length && emptyFolderCount == totalFolderCount){ //only if all folders are safe to delete then this folder is also safe to delete
//                    System.out.println("Safe to delete - all subfolders are empty: " + current.getAbsolutePath());
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean deleteAllEmptyFoldersOfDir(File current){
        if(current.isDirectory()){
            File[] files = current.listFiles();
            if(files.length == 0){ //There is no file in this folder - safe to delete
//                System.out.println("Safe to delete - empty folder: " + current.getAbsolutePath());
                current.delete();
                return true;
            } else {
                int totalFolderCount = 0;
                int emptyFolderCount = 0;
                for(File f : files){
                    if(f.isDirectory()){
                        totalFolderCount++;
                        if(getAllEmptyFoldersOfDir(f)){ //safe to delete
                            emptyFolderCount++;
                        }
                    }

                }
                if(totalFolderCount == files.length && emptyFolderCount == totalFolderCount){ //only if all folders are safe to delete then this folder is also safe to delete
//                    System.out.println("Safe to delete - all subfolders are empty: " + current.getAbsolutePath());
                    current.delete();

                    return true;
                }
            }
        }
        return false;
    }

    public void getEmptyFolder(File dir, List<File> emptyFolders) {

        // Populate an array with files/folders in the directory
        File[] folderContents = dir.listFiles();
        if (folderContents.length == 0) {
            // we are empty, add us.
            emptyFolders.add(dir);
        }

        // Iterate through every file/folder
        for (File content : folderContents) {
            // Disregard files, acquire folders
            if (content.isDirectory()) {
                // check if this folder is empty
                getEmptyFolder(content, emptyFolders);
            }
        }
    }
    public static long deleteFolder(String dir) {

        File f = new File(dir);
        String listFiles[] = f.list();
        long totalSize = 0;
        for (String file : listFiles) {

            File folder = new File(dir + "/" + file);
            if (folder.isDirectory()) {
                totalSize += deleteFolder(folder.getAbsolutePath());
            } else {
                totalSize += folder.length();
            }
        }

        if (totalSize ==0) {
            f.delete();
        }

        return totalSize;
    }
    public static long getFolderSize(String dir) {

        File f = new File(dir);

        String listFiles[] = f.list();
        long totalSize = 0;
        long folderAmount=0;
        for (String file : listFiles) {

            File folder = new File(dir + "/" + file);
            if (folder.isDirectory()) {
                totalSize += getFolderSize(folder.getAbsolutePath());
                if (totalSize == 0) {
                    folderAmount++;
                }
            } else {
                totalSize += folder.length();
            }
            if (totalSize == 0) {
                folderAmount++;
            }
        }


        return folderAmount;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public static void killBackgroundProcesses(Context context)
    {
        Permissions permissions = new Permissions( context );
        if (permissions.permission()) {


            List<ApplicationInfo> packages;
            PackageManager pm;
            pm = context.getPackageManager();
            //get a list of installed apps.
            packages = pm.getInstalledApplications(0);

            ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            String myPackage = context.getApplicationContext().getPackageName();
            for (ApplicationInfo packageInfo : packages) {
                if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)continue;
                if(packageInfo.packageName.equals(myPackage)) continue;
                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
            }


        }
    }

    public void bannerAD(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd( adRequest );
        mAdView.setAdListener( new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility( View.VISIBLE );
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdView.setVisibility( View.GONE );
            }
        } );
    }

}
