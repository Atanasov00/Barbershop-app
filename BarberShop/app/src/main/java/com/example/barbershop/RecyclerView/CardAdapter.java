package com.example.barbershop.RecyclerView;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.CardItem;
import com.example.barbershop.R;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.ViewModel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter linked to the RecyclerView of the homePage
 */
public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Recension> recensionsList;

    private Activity activity;

    private OnItemListener listener;

    private List<Recension> cardItemListNotFiltered;

    ListViewModel listViewModel;

    public CardAdapter(OnItemListener listener, List<Recension> recensionsList, Activity activity) {
        this.listener = listener;
        this.recensionsList = new ArrayList<>(recensionsList);
        this.cardItemListNotFiltered = new ArrayList<>(recensionsList);
        this.activity = activity;

    }

    /**
     *
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,
                parent,false);
        return new CardViewHolder(layoutView, listener);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect
     * the item at the given position.
     *
     * @param holder ViewHolder which should be updated to represent the contents of the item at
     *               the given position in the data set.
     * @param position position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Recension currentRecension = recensionsList.get(position);
        String imagePath = currentRecension.getImage();

        if (imagePath.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                    .getIdentifier(imagePath, "drawable", activity.getPackageName()));
            holder.placeImageView.setImageDrawable(drawable);
        }

        holder.ratingBarView.setRating(currentRecension.getRating());
        holder.placeTextView.setText(currentRecension.getName());
        holder.dateTextView.setText(currentRecension.getDate());
    }

    @Override
    public int getItemCount() {
        return recensionsList.size();
    }





    public void updateCardListItems(List<Recension> filteredList) {
        final CardItemDiffCallback diffCallback =
                new CardItemDiffCallback(this.recensionsList, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.recensionsList.clear();
        this.recensionsList.addAll(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }

    public Recension getItemSelected(int position) { return recensionsList.get(position); }

}
