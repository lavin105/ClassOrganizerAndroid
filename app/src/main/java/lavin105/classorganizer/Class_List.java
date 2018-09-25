/*Brandon Lavinsky
 * lavin105@mail.chapman.edu
 * Class_List.java*/

package lavin105.classorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


/*this class displays the list of classes as well as a button to add new classes
an array adapter and array list is used to store and display classes into the listview
If the user clicks on a class it will direct them to the Class info activity where they can interact with the class*/

public class Class_List extends Activity {
    ListView classList;
    ArrayList<String> listOfClasses, listOfClassNumbers;
    ArrayList<ArrayList<String>>listOfStudentsList, listOfStudentsIdList;
    ArrayList<ArrayList<String>> listOfstdentsPictureslist;
    ArrayAdapter<String> adapter2;
    final int REQUEST_CODE_1=1;
    final int REQUEST_CODE_2=2;


    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list_layout);
            listOfStudentsList=new ArrayList<>();
            listOfStudentsIdList=new ArrayList<>();
            listOfstdentsPictureslist= new ArrayList<>();
            listOfClasses=new ArrayList<String>();
            listOfClassNumbers=new ArrayList<String>();
            classList=findViewById(R.id.class_list);
            adapter2=new ArrayAdapter<>(Class_List.this,android.R.layout.simple_list_item_1,listOfClasses);
            classList.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();

        Button newClass=findViewById(R.id.new_class);
        newClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Class_List.this,Add_Class.class);
                startActivityForResult(i,REQUEST_CODE_1);
            }
        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_1){
            if(resultCode==RESULT_OK){
                String classToAdd=data.getStringExtra("className");
                String classNumber=data.getStringExtra("classNumber");
                ArrayList<String> studentList=data.getStringArrayListExtra("studentList");
                ArrayList<String> studentIdlist=data.getStringArrayListExtra("studentIdlist");
                ArrayList<String> studentPicturelist;
                studentPicturelist=data.getStringArrayListExtra("studentPictures");
                listOfStudentsList.add(studentList);
                listOfClasses.add(classToAdd);
                listOfClassNumbers.add(classNumber);
                classList.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
                listOfStudentsIdList.add(studentIdlist);
                System.out.println("--------------------------");
                System.out.println(studentPicturelist);
                listOfstdentsPictureslist.add(studentPicturelist);
                System.out.println(listOfstdentsPictureslist);


            }
        }
        if(requestCode==REQUEST_CODE_2){
            if(resultCode==RESULT_OK){
               System.out.println(data.getStringExtra("classNme"));
               System.out.println(data.getStringExtra("classNum"));
               System.out.println(data.getStringExtra("key"));
               String p = data.getStringExtra("key");
               int key=Integer.parseInt(p);
               listOfClasses.set(key,data.getStringExtra("classNme"));
               classList.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
                listOfClassNumbers.set(key,data.getStringExtra("classNum"));
                listOfStudentsList.set(key,data.getStringArrayListExtra("listy"));
                listOfStudentsIdList.set(key,data.getStringArrayListExtra("idlisty"));
                listOfstdentsPictureslist.set(key,data.getStringArrayListExtra("piclisty"));


                System.out.println(listOfClassNumbers);
                System.out.println(listOfStudentsIdList);
                System.out.println(listOfstdentsPictureslist);


            }
            if (resultCode==RESULT_CANCELED && data!=null){
                String pp = data.getStringExtra("deleteKey");
                int delkey=Integer.parseInt(pp);
                listOfClasses.remove(delkey);
                listOfClassNumbers.remove(delkey);
                listOfStudentsList.remove(delkey);
                classList.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
                listOfStudentsIdList.remove(delkey);
                listOfstdentsPictureslist.remove(delkey);
            }
        }
        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classNameInfo=classList.getItemAtPosition(position).toString();
                String classNumberInfo=listOfClassNumbers.get(position);
                ArrayList<String> theStudents=listOfStudentsList.get(position);
                ArrayList<String> theStudentsIDs = listOfStudentsIdList.get(position);
                ArrayList<String> theStudentPictures=listOfstdentsPictureslist.get(position);
                System.out.println(theStudents);
                System.out.println(theStudentPictures);
                Intent infoIntent = new Intent(Class_List.this,Class_Info.class);
                infoIntent.putExtra("cName",classNameInfo);
                infoIntent.putExtra("cNumber",classNumberInfo);
                infoIntent.putStringArrayListExtra("stuList", theStudents);
                infoIntent.putExtra("item_position", position);
                infoIntent.putStringArrayListExtra("ids",theStudentsIDs);
                infoIntent.putExtra("pics", theStudentPictures);
                startActivityForResult(infoIntent,REQUEST_CODE_2);
            }
        });
    }
}
