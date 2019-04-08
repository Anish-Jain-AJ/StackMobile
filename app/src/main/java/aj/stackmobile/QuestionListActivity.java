package aj.stackmobile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionListActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private RecyclerView recyclerView;
    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";

    private int page_number = 1;
    private int count = 10;
    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";
    RecyclerNewAdapter rcn;
    private GridLayoutManager layoutManager;

    private boolean isLoading = true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total = 0;
    private int view_threshold = 10;

    Retrofit retrofit;
    Api api;

    public static MyAppDatabase myAppDatabase;
    private ProgressBar pb;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        String s1 = getIntent().getStringExtra("select1");
        String s2 = getIntent().getStringExtra("select2");
        String s3 = getIntent().getStringExtra("select3");
        String s4 = getIntent().getStringExtra("select4");

         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"questiondatadb").allowMainThreadQueries().build();

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


        toolbar = (Toolbar)findViewById(R.id.toolbr);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawerlyt);
        nvDrawer = findViewById(R.id.nview);
        recyclerView = findViewById(R.id.rv);

        pb = findViewById(R.id.pb);


        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        nvDrawer.getMenu().findItem(R.id.nav_first).setTitle(s1);
        nvDrawer.getMenu().findItem(R.id.nav_second).setTitle(s2);
        nvDrawer.getMenu().findItem(R.id.nav_third).setTitle(s3);
        nvDrawer.getMenu().findItem(R.id.nav_fourth).setTitle(s4);

        pb.setVisibility(View.VISIBLE);

        onDisplay(nvDrawer.getMenu().findItem(R.id.nav_first).toString());



        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_first:{
                        onDisplay(item.toString());
                        item.setChecked(true);
                        break;
                    }
                    case R.id.nav_second:{
                        onDisplay(item.toString());
                        item.setChecked(true);
                        break;
                    }
                    case R.id.nav_third:{
                        onDisplay(item.toString());
                        item.setChecked(true);
                        break;
                    }
                    case R.id.nav_fourth:{
                        onDisplay(item.toString());
                        item.setChecked(true);
                        break;
                    }
                    case R.id.local:{
                        Intent intent = new Intent(QuestionListActivity.this,LocalSavedQuestion.class);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });



        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);




    }

    public void onDisplay(final String tag){

        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Call<QuestionObject> callq = api.getAnswers(FIRST_PAGE,PAGE_SIZE,SITE_NAME,tag);
        callq.enqueue(new Callback<QuestionObject>() {
            @Override
            public void onResponse(Call<QuestionObject> call, Response<QuestionObject> response) {
                ArrayList<item> i = response.body().items;
                rcn = new RecyclerNewAdapter(getApplicationContext(), i, new RecyclerNewAdapter.RecyclerNewAdapterListener() {
                    @Override
                    public void onClick(View v, int position, String title, String link) {
                        Intent intent = new Intent(QuestionListActivity.this,WebViewPage.class);
                        intent.putExtra("web",link);
                        startActivity(intent);
                    }

                    @Override
                    public void onShare(View v, int position, String title, String link) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "See this link: ");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, link);
                        startActivity(Intent.createChooser(intent,"Share link using: "));
                    }

                    @Override
                    public void onCheck(View v, int position, item i1) {
                        List<itemsave> ii = myAppDatabase.myDao().check(i1.question_id);
                        if(ii.size()>0){
                            Toast.makeText(getApplicationContext(),"Already Saved",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            itemsave isa = new itemsave(i1.question_id,i1.link,i1.title);
                            myAppDatabase.myDao().addItem(isa);
                            Toast.makeText(getApplicationContext(),"Saved Locally",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                pb.setVisibility(View.GONE);
                recyclerView.setAdapter(rcn);
                Toast.makeText(getApplicationContext(),"First Page",Toast.LENGTH_SHORT).show();
                page_number=1;
                isLoading = true;
                pastVisibleItems=0;visibleItemCount=0;totalItemCount=0;previous_total = 0;
                view_threshold = 10;
            }

            @Override
            public void onFailure(Call<QuestionObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if(dy>0){
                    if(isLoading){
                    if(totalItemCount>previous_total){
                        isLoading = false;
                        previous_total = totalItemCount;
                    }
                    }
                    if(!isLoading&&(totalItemCount-visibleItemCount)<=pastVisibleItems+view_threshold){
                        page_number++;
                        performPagination(tag);
                        isLoading = true;
                    }
                }
            }
        });






    }

    private void performPagination(final String tag){
        pb.setVisibility(View.VISIBLE);


        Call<QuestionObject> callq = api.getAnswers(page_number,PAGE_SIZE,SITE_NAME,tag);
        callq.enqueue(new Callback<QuestionObject>() {
            @Override
            public void onResponse(Call<QuestionObject> call, Response<QuestionObject> response) {

                if(response.body().has_more==true) {

                    ArrayList<item> i = response.body().items;
                    rcn.addItem(i);

                    Toast.makeText(getApplicationContext(), "Page" + page_number, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No more items", Toast.LENGTH_LONG).show();
                }

                pb.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<QuestionObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
            }
        });

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
