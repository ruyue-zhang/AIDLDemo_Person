package com.ruyue.aidl.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ruyue.aidl.person.IImoocAIDL;
import com.ruyue.aidl.person.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IImoocAIDL iImoocAIDL;

    private ServiceConnection conn = new ServiceConnection() {
        //当绑定上服务会执行
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            iImoocAIDL = IImoocAIDL.Stub.asInterface(service);

        }
        //断开服务时，执行此方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //资源回收
            iImoocAIDL = null;
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.ruyue.aidl.person", "com.ruyue.aidl.person.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            try {
                ArrayList<Person> personList = (ArrayList<Person>) iImoocAIDL.add(new Person("张三", 21));
                Log.d(TAG, personList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}