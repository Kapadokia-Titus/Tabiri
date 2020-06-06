package kapadokia.nyandoro.tabiri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import kapadokia.nyandoro.tabiri.data.TabiriPrefference;
import kapadokia.nyandoro.tabiri.utilities.NetworkUtils;
import kapadokia.nyandoro.tabiri.utilities.OpenWeatherJsonUtils;

public class MainActivity extends AppCompatActivity {

    private TextView  error_message;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        error_message = findViewById(R.id.error_teext);
        progressBar = findViewById(R.id.refresh_progress_bar);
        recyclerView = findViewById(R.id.recycler_view_forecast);
        forecastAdapter = new ForecastAdapter();

        //layout manager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(forecastAdapter);

        //setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        recyclerView.setHasFixedSize(true);
        /* Once all of our views are setup, we can load the weather data. */
        loadWeatherData();

        forecastAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnclickListener(int tabiriItem) {
               Intent intent  = new Intent(MainActivity.this, DetailsActivity.class);
               startActivity(intent);
            }
        });

    }

    // menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            // COMPLETED (46) Instead of setting the text to "", set the adapter to null before refreshing
            forecastAdapter.setWeatherData(null);
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */

    private void loadWeatherData(){
        showWeatherDataView();
        String location = TabiriPrefference.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

//    method to hide error message and show weather data
    public void showWeatherDataView(){
        error_message.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

//    will hide the weather data and show the error message

    public void showErrorMessage(){
        error_message.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
           progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            /* If there's no zip code, there's nothing to look up. */
            if(strings.length ==0){
                return null;
            }

            String location = strings[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHTTPUrl(weatherRequestUrl);
                String [] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] s) {
            // As soon as the data is finished loading, hide the loading indicator
                progressBar.setVisibility(View.GONE);

            if (s !=null){
                showWeatherDataView();
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : s) {
                    forecastAdapter.setWeatherData(s);
                }
            }


            //error message if it is null
            if (s==null){
                showErrorMessage();
            }
        }
    }
}

