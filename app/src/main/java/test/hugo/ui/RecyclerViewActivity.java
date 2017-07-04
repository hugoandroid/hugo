package test.hugo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import test.hugo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView rv_data = (RecyclerView) findViewById(R.id.rv_data);
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        rv_data.setAdapter(new MyAdapter(this));

    }

    class MyAdapter extends RecyclerView.Adapter {
        private RecyclerViewActivity activity;

        public MyAdapter(RecyclerViewActivity activity) {
            this.activity = activity;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater from = LayoutInflater.from(activity);
            if (viewType == 0)
                return new MyHolder(from.inflate(R.layout.item_recyclerview, parent, false));
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WebView mView = new WebView(parent.getContext());
            mView.setLayoutParams(lp);
            return new MyHolder(mView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == 2) {
                WebView webView = (WebView) holder.itemView;
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setLoadWithOverviewMode(false);
                try {
                    String data = getFromRaw();
                    webView.loadData(data, "text/html; charset=UTF-8", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                webView.loadUrl("http://blog.csdn.net/u013200864/article/details/51766931");
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 2)
                return 1;
            else return 0;
        }
    }
    public String getFromRaw() throws IOException {
        InputStreamReader inputReader = new InputStreamReader( getResources().openRawResource(R.raw.demo));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line="";
        String Result="";
        while((line = bufReader.readLine()) != null)
            Result += line;
        return Result;

    }
    static class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
            if (itemView instanceof WebView)
                initWebView((WebView) itemView);
        }

        private void initWebView(WebView itemView) {
            WebSettings mSetting = itemView.getSettings();
            mSetting.setJavaScriptEnabled(true);
//                mWebView.setWebChromeClient(new WebChromeClient());
//                mWebView.setWebViewClient(new WebViewClient(){
//                });
        }
    }
}
