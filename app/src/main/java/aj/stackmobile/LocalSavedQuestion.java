package aj.stackmobile;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class LocalSavedQuestion extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    public static MyAppDatabase myAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_saved_question);

        recyclerView = findViewById(R.id.rv3);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"questiondatadb").allowMainThreadQueries().build();


        List<itemsave> is =  myAppDatabase.myDao().getItems();
        LocalRecyclerAdapter localRecyclerAdapter = new LocalRecyclerAdapter(getApplicationContext(), is, new LocalRecyclerAdapter.LocalRecyclerAdapterListener() {
            @Override
            public void onClick(View v, int position, String title, String link) {
                Intent intent = new Intent(LocalSavedQuestion.this,WebViewPage.class);
                intent.putExtra("web",link);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(localRecyclerAdapter);


    }
}
