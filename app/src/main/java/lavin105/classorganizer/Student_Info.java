package lavin105.classorganizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Student_Info extends Activity {

    ImageView iv;
    TextView name,idz;
    Button bck, delete;
    int pos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_layout);

        iv=findViewById(R.id.studentPicture);
        name=findViewById(R.id.student_name);
        idz=findViewById(R.id.student_id);
        bck=findViewById(R.id.toclassinfo);
        delete=findViewById(R.id.delStudent);

        Bundle bundle=getIntent().getExtras();

        if (bundle!=null){
           String thename= bundle.getString("nme");
           String theid= bundle.getString("idd");
           String p =bundle.getString("picc");
            pos=bundle.getInt("posit");
           name.setText(thename);
           idz.setText(theid);
            byte[] encodeByte = Base64.decode(p, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            iv.setImageBitmap(bitmap);
            System.out.println(pos);
        }
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("theposition", Integer.toString(pos));
            setResult(RESULT_OK, i);
            finish();
            }
        });


    }
}
