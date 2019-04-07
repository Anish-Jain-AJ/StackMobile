package aj.stackmobile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInterestActivity extends AppCompatActivity {

    public static String BaseUrl = "https://api.stackexchange.com";
    public ListView listView;
    public RecyclerView recyclerView;
    public Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);

        final ArrayList<String> tagname = new ArrayList<>();
        final ArrayList<String> selected = new ArrayList<>();

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null){
            if(networkInfo.isConnected()){
                Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
        }

        listView = findViewById(R.id.list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TagInterface tagInterface = retrofit.create(TagInterface.class);

        Call<TagItem> tagItemCall = tagInterface.getTag();

        tagItemCall.enqueue(new Callback<TagItem>() {
            @Override
            public void onResponse(Call<TagItem> call, Response<TagItem> response) {
                if(response.isSuccessful()){
                    TagItem tagItem = response.body();

                    ArrayList<tagdetails> tags = tagItem.getItems();


                    for(int i=0;i<tags.size();i++){
                        tagname.add(tags.get(i).getName());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,tagname);
                    listView.setAdapter(arrayAdapter);


                }
                else{
                    Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TagItem> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclelist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this,selected);
        recyclerView.setAdapter(recyclerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String some = tagname.get(position);
                Boolean present = false;
                for(int i=0;i<selected.size();i++){
                    if(selected.get(i).equals(some))
                      present = true;
                }
                if(present==false){
                selected.add(some);
                recyclerAdapter.notifyItemInserted(selected.size()-1);}
                else{
                    Toast.makeText(getApplicationContext(),"Already Added Tag",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn = findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.size()==4){
                    Intent intent = new Intent(UserInterestActivity.this,QuestionListActivity.class);
                    intent.putExtra("select1",selected.get(0));
                    intent.putExtra("select2",selected.get(1));
                    intent.putExtra("select3",selected.get(2));
                    intent.putExtra("select4",selected.get(3));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"You must select 4 tags compulsarily",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
