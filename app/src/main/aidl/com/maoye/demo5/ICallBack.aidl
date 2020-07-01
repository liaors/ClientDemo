// ICallBack.aidl
package com.maoye.demo5;

// Declare any non-default types here with import statements
/**
* 回调
*/
interface ICallBack {
       void onSuccess(String result);
       void onFailed(String errorMsg);
}
