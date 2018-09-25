/*Brandon Lavinsky
 * lavin105@mail.chapman.edu
 * Class_Info.java*/


package lavin105.classorganizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/*This class displays the classes information and allows the user to edit the class name and number as well as add students
 * students can be deleted by clicking on the student in the list they are also able to be sorted alphabetically
 * Classes can also be deleted from this page by clicking delete class,clicking add student from this page will allow you to add students
 * to the list*/

public class Class_Info extends Activity {
    TextView theclass,theclassnumber;
    Button toClassList, editCName, editCNumber, delete, newStu;
    ListView students;
    ArrayAdapter<String> allStudents;
    String cName,cNumber;
    EditText alert1input;
    EditText alert2input;
    ArrayList<String> studentLst, studentIds;
    ArrayList<String> pictureList;
    int pos;
    final int REQUEST_CODE_4=4;
    final int REQUEST_CODE_5=5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info_layout);

        delete=findViewById(R.id.deleteClass);
        editCName=findViewById(R.id.editName);
        editCNumber=findViewById(R.id.editNumber);
        students=findViewById(R.id.students_list);
        toClassList=findViewById(R.id.toClasses);
         theclass =findViewById(R.id.className);
         theclassnumber=findViewById(R.id.classNumber);
         newStu=findViewById(R.id.new_student);
         pictureList=new ArrayList<>();

        AlertDialog.Builder alert= new AlertDialog.Builder(Class_Info.this);
        alert.setTitle("Edit Class Name");
        alert.setMessage("Please enter the new class name");
        alert1input=new EditText(Class_Info.this);
        alert2input=new EditText(Class_Info.this);

        alert.setView(alert1input);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newClassName=alert1input.getText().toString();
                theclass.setText(newClassName);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog theAlert=alert.create();



        AlertDialog.Builder alert2= new AlertDialog.Builder(Class_Info.this);
        alert2.setTitle("Edit Class Number");
        alert2.setMessage("Please enter the new class number");
        alert2input=new EditText(Class_Info.this);
        alert2.setView(alert2input);
        alert2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newClassNumber=alert2input.getText().toString();
                theclassnumber.setText(newClassNumber);
            }
        });
        alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog theAlert2=alert2.create();

         editCName.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                theAlert.show();

             }
         });

         editCNumber.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                theAlert2.show();
             }
         });







       Bundle bundle=getIntent().getExtras();
       if (bundle!=null){
           theclass.setText(bundle.getString("cName"));
           theclassnumber.setText(bundle.getString("cNumber"));
          pos=bundle.getInt("item_position");
          studentLst=bundle.getStringArrayList("stuList");
           allStudents=new ArrayAdapter<>(Class_Info.this,android.R.layout.simple_list_item_1,studentLst);
           students.setAdapter(allStudents);
           pictureList=bundle.getStringArrayList("pics");
           studentIds=bundle.getStringArrayList("ids");
            System.out.println(pictureList);
       }

       toClassList.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cName=theclass.getText().toString();
               cNumber=theclassnumber.getText().toString();
               Intent i = new Intent();
               i.putExtra("classNme",cName);
               i.putExtra("classNum",cNumber);
               i.putExtra("key",Integer.toString(pos));
               i.putStringArrayListExtra("listy",studentLst);
               i.putStringArrayListExtra("idlisty",studentIds);
               i.putStringArrayListExtra("piclisty",pictureList);
               setResult(RESULT_OK,i);
               finish();
           }
       });
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i =new Intent();
               i.putExtra("deleteKey", Integer.toString(pos));
               setResult(RESULT_CANCELED,i);
               finish();
           }
       });


       newStu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent anotherStudent= new Intent(Class_Info.this,Add_Student.class);
               startActivityForResult(anotherStudent,REQUEST_CODE_4);

           }
       });

       students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                       Intent studentInfo = new Intent(Class_Info.this,Student_Info.class);
                       studentInfo.putExtra("nme", studentLst.get(position));
                       studentInfo.putExtra("idd",studentIds.get(position));
                       studentInfo.putExtra("picc",pictureList.get(position));
                       studentInfo.putExtra("posit", position);
                       startActivityForResult(studentInfo,REQUEST_CODE_5);

           }
       });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_4){
            if(resultCode==RESULT_OK){

                studentLst.add(data.getStringExtra("name"));
                students.setAdapter(allStudents);
                allStudents.notifyDataSetChanged();
                studentIds.add(data.getStringExtra("id"));
                System.out.println(data.getParcelableExtra("pic"));
               pictureList.add(data.getStringExtra("pic"));
              System.out.println(pictureList);
            }
        }
        if (requestCode==REQUEST_CODE_5){
            if (resultCode==RESULT_OK){
                String pp = data.getStringExtra("theposition");
                int delkey=Integer.parseInt(pp);
                studentLst.remove(delkey);
                studentIds.remove(delkey);
                pictureList.remove(delkey);
                allStudents.notifyDataSetChanged();
            }
        }
    }
}
