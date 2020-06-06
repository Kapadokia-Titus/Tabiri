package kapadokia.nyandoro.tabiri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private String text;
    private TextView single_forecast_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent tabriData = getIntent();


        single_forecast_item = findViewById(R.id.details_activity_tv);
        //get data only if it is not empty
        if (tabriData !=null){
        if (tabriData.hasExtra(Intent.EXTRA_TEXT)){

            text = tabriData.getStringExtra(Intent.EXTRA_TEXT);
            single_forecast_item.setText(text);
        }

        }
    }
}
