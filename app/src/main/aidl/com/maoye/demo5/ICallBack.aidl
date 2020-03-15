// ICallBack.aidl
package com.maoye.demo5;

// Declare any non-default types here with import statements

interface ICallBack {
       void onSuccess(String result);
       void onFailed(String errorMsg);
}
