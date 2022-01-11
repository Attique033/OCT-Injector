package com.slipkprojects.sockshttp.util;

import android.os.AsyncTask;

public class Worker extends AsyncTask<Void, Integer, String> {
    private WorkerAction workerAction;

    protected Void doInBackground(Void objArr) {

        return doInBackground((Void) objArr);
    }
//    protected /* bridge */ void onPostExecute(Object obj) {
//        onPostExecute((String) obj);
//    }

    public Worker(WorkerAction workerAction) {
        this.workerAction = workerAction;
    }

    @Override
    protected String doInBackground(Void... voidArr) {
        this.workerAction.runFirst();
        return (String) null;
    }

    protected void onPostExecute(String str) {
        this.workerAction.runLast();
    }
}
