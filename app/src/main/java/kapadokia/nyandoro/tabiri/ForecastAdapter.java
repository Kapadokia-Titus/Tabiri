package kapadokia.nyandoro.tabiri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.TabiriViewHolder> {

    private String[] weatherData;
    Context context;

    public class TabiriViewHolder extends RecyclerView.ViewHolder {
        public final  TextView weatherText;
        
        public TabiriViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherText = itemView.findViewById(R.id.tv_weather_data);
        }
    }




    @NonNull
    @Override
    public TabiriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TabiriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabiriViewHolder holder, int position) {
        String weatherForThisDay = weatherData[position];
        holder.weatherText.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {

        if (weatherData==null){
            return 0;
        }
       else return weatherData.length;
    }


    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param weatherData The new weather data to be displayed.
     */
    public void setWeatherData(String[] weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

}
