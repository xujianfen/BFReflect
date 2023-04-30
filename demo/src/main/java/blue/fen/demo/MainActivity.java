package blue.fen.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void test(View view) {
        new Thread(() -> {
            File dexFile = getDexFile();
            if (dexFile != null) {
                try {
                    Hotfix.loadDex(getApplicationContext(), dexFile);
                    Object a = Class.forName("blue.fen.demo.a.A").newInstance();
                    Method method = a.getClass().getDeclaredMethod("demo");
                    Object result = method.invoke(a);
                    toast(result.toString());
                } catch (Exception e) {
                    toast("加载失败");
                    e.printStackTrace();
                }
            } else {
                toast("文件创建失败");
            }
        }).start();
    }

    void toast(String msg) {
        runOnUiThread(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    File getDexFile() {
        File dexFile = new File(this.getFilesDir(), "A.dex");

        InputStream in = null;
        OutputStream out = null;


        if (!dexFile.exists()) {
            try {
                AssetManager assetManager = this.getAssets();
                in = assetManager.open("A.dex");

                if (dexFile.createNewFile()) {
                    out = new FileOutputStream(dexFile);

                    byte[] buffer = new byte[1024];

                    for (int len; (len = in.read(buffer)) != -1; ) {
                        out.write(buffer, 0, len);
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return dexFile;
    }
}