package com.slipkprojects.sockshttp.util;

import android.content.Context;
import android.os.AsyncTask;

public class Worker2 extends AsyncTask<Void, Integer, String> {
    private WorkerAction workerAction;

    protected /* bridge */ Void doInBackground(Void objArr) {

        return doInBackground((Void) objArr);
    }

//    protected /* bridge */ void onPostExecute(Object obj) {
//        onPostExecute((String) obj);
//    }

    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Integer... numArr) {
    }

//    protected /* bridge */ void onProgressUpdate(Object[] objArr) {
//        onProgressUpdate((Object[]) objArr);
//    }

    public Worker2(WorkerAction workerAction, Context context) {
        this.workerAction = workerAction;
    }

    protected String doInBackground(Void... voidArr) {
        this.workerAction.runFirst();
        return (String) null;
    }

    protected void onPostExecute(String str) {
        this.workerAction.runLast();
    }
}
