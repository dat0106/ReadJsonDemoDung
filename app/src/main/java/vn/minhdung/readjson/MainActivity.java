package vn.minhdung.readjson;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {
    TextView monhoc, noihoc, website, fanpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monhoc = (TextView)findViewById(R.id.textViewMonHoc);
        noihoc = (TextView)findViewById(R.id.textViewNoiHoc);
        website = (TextView)findViewById(R.id.textViewWebsite);
        fanpage = (TextView)findViewById(R.id.textViewFanpage);
        // vii du ong muon debugger nhe
        Log.v(this.toString(), "bien string nhe ong");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://khoapham.vn/KhoaPhamTraining/android/json/demo3.json");
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String chuoi = getXmlFromUrl(params[0]);
            Log.v(MainActivity.this.toString(), "chuoi");
            Log.v(MainActivity.this.toString(), chuoi);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }

    private String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            // defaultHttpClient lấy toàn bộ dữ liệu trong http đổ vào 1 chuỗi String
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            // set UTF-8 cho ra chữ unikey
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
