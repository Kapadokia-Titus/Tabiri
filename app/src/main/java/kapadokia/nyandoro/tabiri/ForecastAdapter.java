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

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */

    private OnItemClickListener listener;


    public class TabiriViewHolder extends RecyclerView.ViewHolder{
        public final  TextView weatherText;

        public TabiriViewHolder(@NonNull View view) {
            super(view);
            weatherText = view.findViewById(R.id.tv_weather_data);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.setOnclickListener(position);
                }
            });

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
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
