// PersionInterService.aidl
package com.maoye.demo5;

// Declare any non-default types here with import statements
import com.maoye.demo5.PersionBean;
import com.maoye.demo5.ICallback;

interface PersionInterService {

List <PersionBean> getPersionList();

void addPersion(inout PersionBean persion);

void register(ICallBack callBack);

void unRegister(ICallBack callBack);
}
