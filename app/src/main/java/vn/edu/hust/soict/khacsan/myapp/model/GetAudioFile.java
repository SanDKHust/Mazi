package vn.edu.hust.soict.khacsan.myapp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetAudioFile {
    public static final String URL_POST = "http://dws2.voicetext.jp/tomcat/servlet/vt";
    private AudioListener listener;

    public GetAudioFile(AudioListener listener) {
        this.listener = listener;
    }


    public void getFile(final String text){
        MediaType URL_ENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        OkHttpClient client = new OkHttpClient();
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/53.2.123 Chrome/47.2.2526.123 Safari/537.36";

        try {
            String query = "text=" + URLEncoder.encode(text, "UTF-8") + "&talkID=308&volume=100&speed=100&pitch=100&dict=3";
            RequestBody body = RequestBody.create(URL_ENCODED, query);

            Request request = new Request.Builder()
                    .url(URL_POST)
                    .post(body)
                    .header("User-Agent",USER_AGENT)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onGetFailure(e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    ResponseBody body = response.body();
                    if(body != null){
                        String nameAudio = body.string().replace("comp=","")
                                .replace("\r\n", "");
                        listener.onGetSuccess(nameAudio,text);
                    } else listener.onGetFailure("null");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public interface AudioListener{
        void onGetFailure(String msg);
        void onGetSuccess(String namAudio,String text);
    }
}
