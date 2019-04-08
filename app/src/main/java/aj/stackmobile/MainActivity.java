package aj.stackmobile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String client_id = "ADD YOUR ID";
    private String scope = "no_expiry";
    private String redirect_uri = "https://stackexchange.com/oauth/login_success";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://stackoverflow.com/oauth/dialog" + "?client_id=" + client_id + "&scope=" + scope + "&redirect_uri=" + redirect_uri));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if(uri!=null && uri.toString().startsWith(redirect_uri)){
            Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,UserInterestActivity.class);
            startActivity(intent);
        }

    }
}
