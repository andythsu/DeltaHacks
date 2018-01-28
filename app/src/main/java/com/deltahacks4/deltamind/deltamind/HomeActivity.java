package com.deltahacks4.deltamind.deltamind;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

//        import okhttp3.Call;
//        import okhttp3.Callback;
//        import okhttp3.OkHttpClient;
//        import okhttp3.Request;
//        import okhttp3.Response;


public class HomeActivity extends AppCompatActivity {

    List<String> reminders = new ArrayList<String>();

    private ListView mListView;
    private List<String> items = Arrays.asList("cat","dog","parrot","goldfish");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure UI Binding
        setContentView(R.layout.activity_home);
        mListView = (ListView) findViewById(R.id.string_arraylist);


        // Construct data storage vars
        ArrayList<String> data = new ArrayList<String>(items);

        // Adaptor for list view from array data
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);


        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, CreateReminder.class);
                intent.putExtra("crypto", items.get(position));
                startActivity(intent);
//
////        mListView.setOnClickListener(new View.OnClickListener());
//        String[] reminderArray = new String[reminders.size()];
//// 3
//        for (int i = 0; i < reminders.size(); i++) {
//            String items = reminders.get(i);
//            reminderArray[i] = reminders.get(i);
//        }
    }

//    private void setupListView() {
//        mListView = (ListView) findViewById(R.id.string_arraylist);
//        ArrayList<String> items = new ArrayList<>();
            });
    }
}