package com.app.jlin.cafetraveler.Builder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Android 6.0 以上有些權限需要 User 確認可以使用
 */
public class PermissionBuilder {

    private ArrayList<String> permissionList;

    public static class Builder {

        private Context context;
        private ArrayList<String> permissionList;

        public Builder(Context context){

            this.context = context;
            permissionList = new ArrayList<>();

        }

        /**
         * 確認相機權限
         * */
        public Builder checkCameraPermission(){

            // 判斷是否沒有 Camera 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.CAMERA);
            }

            return this;
        }

        /** 確認讀取外部儲存權限 */
        public Builder checkReadExternalStorage(){

            // 判斷是否沒有 read external storage (讀取外部儲存) 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            return this;
        }

        /** 確認寫入外部儲存權限 */
        public Builder checkWriteExternalStorage(){

            // 判斷是否沒有 write external storage (寫入外部儲存) 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            return this;
        }

        /** 確認讀取電話狀態權限 */
        public Builder checkReadPhoneState(){

            // 判斷是否沒有 read phone state (讀取電話狀態) 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }

            return this;
        }

        /** 確認MIC權限 */
        public Builder checkRecordAudioState(){

            // 判斷是否沒有 read phone state (讀取電話狀態) 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.RECORD_AUDIO);
            }

            return this;
        }

        /** 確認Location權限 */
        public Builder checkLocationState(){

            // 判斷是否沒有 read phone state (讀取電話狀態) 權限
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED){

                // 確定沒有權限 加入要求權限 list
                this.permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            return this;
        }

        public PermissionBuilder builder(){ return new PermissionBuilder(this);}

    }

    public PermissionBuilder(Builder builder){ this.permissionList = builder.permissionList; }

    public String[] getPermissions(){
        // 將 permissionList 轉為 string[]
        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
        return permissions;
    }
}
