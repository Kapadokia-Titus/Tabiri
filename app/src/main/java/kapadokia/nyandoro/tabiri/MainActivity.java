package kapadokia.nyandoro.tabiri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView weatherText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherText = findViewById(R.id.tv_weather_data);

        // Hardcoded weather information
        String [] weatherData = TabiriList.getTabiriInfo();


        for (String weatherInfo:weatherData){
            weatherText.append(weatherInfo + "\n\n");
        }







    }
}
