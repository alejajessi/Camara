package i2t.icesi.camara;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_foto;
    private EditText et_descripcion;
    private Button btn_takepic;
    private Button btn_save;

    private Bitmap image;

    public static final int ID_REQUEST_CAMERA = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_foto = findViewById(R.id.iv_foto);
        et_descripcion = findViewById(R.id.et_descripcion);
        btn_takepic = findViewById(R.id.btn_takepic);
        btn_save = findViewById(R.id.btn_save);

        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }
                , 11
        );

        btn_takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, ID_REQUEST_CAMERA);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String fotoname = UUID.randomUUID().toString() + ".png";
                    String path = Environment.getExternalStorageDirectory().toString() + "/" + fotoname;
                    FileOutputStream fos = new FileOutputStream(new File(path));
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID_REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            iv_foto.setImageBitmap(image);
        }
    }
}
