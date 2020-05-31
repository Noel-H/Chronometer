package fr.noelproject.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private int seriesNumber;
    private int selectedSeriePosition;
    private boolean isRestingTime;

    SeriesAdapter(int seriesNumber){
        this.seriesNumber = seriesNumber;
        this.selectedSeriePosition = 0;
        this.isRestingTime = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position.setText(String.valueOf(position+1));
        setStartedSerie(holder.itemView, position <= selectedSeriePosition);
        if(isRestingTime){
            setEndSerie(holder.itemView, position <= selectedSeriePosition );
        } else {
            setEndSerie(holder.itemView, position < selectedSeriePosition );
        }
    }

    @Override
    public int getItemCount() {
        return seriesNumber;
    }

    private void setEndSerie(View v, boolean isEnded){
        v.setActivated(isEnded);
    }

    private void setStartedSerie(View v, boolean isStarted){
        v.setSelected(isStarted);
    }

    public void nextSerie() {
        selectedSeriePosition++;
        notifyDataSetChanged();
    }

    public void setRestingTime(boolean restingTime) {
        isRestingTime = restingTime;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView position;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            position=itemView.findViewById(R.id.serie_position);
        }
    }
}
