package com.ruyue.aidl.person;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class IRemoteService extends Service {

    private ArrayList<Person> personList;

    private IImoocAIDL iImoocAIDL = new IImoocAIDL.Stub() {
        @Override
        public List<Person> add(Person person) throws RemoteException {
            personList.add(person);
            return personList;
        }
    };



    @Override
    public IBinder onBind(Intent intent) {
        personList = new ArrayList<Person>();
        return iImoocAIDL.asBinder();
    }
}