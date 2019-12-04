package com.example.asynchronousexecution;

import android.os.AsyncTask;

public class CountTask extends AsyncTask<Integer, Integer, Integer> {

    private Listener listener;

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(int count);
    }

    // 非同期処理
    @Override
    protected Integer doInBackground(Integer... params) {
        // 10秒数える処理
        do {
            if (isCancelled()) {
                return 0;
            }
            try {
                // 1秒間スリープさせる
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            params[0]++;
            // 途中経過を返す
            publishProgress(params[0]);
        } while (params[0] < 10);

        return params[0];
    }

    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (listener != null) {
            listener.onSuccess(progress[0]);
        }
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(Integer result) {
        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    void cancelTask() {
        this.cancel(true);
    }
}
