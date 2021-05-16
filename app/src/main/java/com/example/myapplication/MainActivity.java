package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users;
    private AppDatabase db;
    private UserDao userDao;
    private ListAdapter adapter;
    private ListView listView;

    private Button add;
    private Button remove;
    private Button cancel;

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-users3")
                .allowMainThreadQueries().build();
        userDao = db.userDao();


//        User u1 = new User("Trần Văn A");
//        User u2 = new User("Trần Văn B");
//        User u3 = new User("Trần Văn C");
//        User u4 = new User("Trần Văn D");
//        User u5 = new User("Trần Văn E");
//        User u6 = new User("Trần Văn F");
//        User u7 = new User("Trần Văn A");
//        User u8 = new User("Trần Văn B");
//        User u9 = new User("Trần Văn C");
//        User u10 = new User("Trần Văn D");
//        User u11 = new User("Trần Văn E");
//        User u12 = new User("Trần Văn F");
//
//
//        userDao.insertAll(u2, u3, u4, u5, u6, u7, u8, u9, u10, u11, u12);


        users = (ArrayList<User>) userDao.getAll();

        listView = findViewById(R.id.list);
        adapter = new ListAdapter(this, R.layout.item, users);
        listView.setAdapter(adapter);
        handleEvent();
        refresh();


    }

    public void refresh() {



        adapter = new ListAdapter(this, R.layout.item, (ArrayList<User>) userDao.getAll());
        listView.setAdapter(adapter);
    }

    public void handleEvent() {
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        cancel = findViewById(R.id.cancel);

        text = findViewById(R.id.input);
        listView = findViewById(R.id.list);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc = String.valueOf(text.getText());
                User user = new User(doc);
                userDao.insertAll(user);
                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                refresh();


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = userDao.getAll().get(position);
                System.out.println(user.toString());

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userDao.delete(user);
                        Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_LONG).show();
                        refresh();
                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });




    }

}