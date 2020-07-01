package com.maoye.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rs.clientdemo.R;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    PersionInterService persionInterService;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         et = findViewById(R.id.et);

    }



    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            persionInterService = PersionInterService.Stub.asInterface(service);
            if (persionInterService == null) {
                Log.e("TAG", "mStudentService == null");
                return;
            }
            try {
                persionInterService.register(callBack);
            } catch (RemoteException e) {
                Log.e("TAG", "onServiceConnected: "+e.getMessage() );
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "onServiceDisconnected: " );
        }
    };

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.onBind:
                Intent  intent = new Intent(getPackageName());
                intent.setClassName("com.maoye.demo5","com.maoye.demo5.PersionService");//服务包名与服务路径
                bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_bt:
                unbindService(mConnection);
                break;
            case R.id.getData:
                try {
                    List<PersionBean> persionList = persionInterService.getPersionList();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (PersionBean persionBean : persionList) {
                        stringBuilder.append(persionBean.getName()+persionBean.getAge());
                    }
                    et.setText(stringBuilder+"8ui" +
                            "");
                    Log.e("TAG", "onClick: " );

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.addData:
                try {
                    persionInterService.addPersion(new PersionBean(new Random().nextInt(20),"客户端："));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 设置死亡代理，当与服务端断开链接时（客户端与服务端还存活，则会走此方法），可以设置重新连接·1
     *
     *
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (persionInterService == null) {
                return;
            }
            //解除死亡代理
            persionInterService.asBinder().unlinkToDeath(mDeathRecipient, 0);
            persionInterService = null;
            //重新绑定服务
            Intent  intent = new Intent(getPackageName());
            intent.setClassName("com.maoye.demo5","com.maoye.demo5.PersionService");
            bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        }
    };

    ICallBack callBack = new ICallBack.Stub(){

        @Override
        public void onSuccess(String result) throws RemoteException {
            Log.e("TAG", "客户端结果onSuccess: "+result );
        }

        @Override
        public void onFailed(String errorMsg) throws RemoteException {
            Log.e("TAG", "客户端结果errorMsg: "+errorMsg );
        }
    };
}
