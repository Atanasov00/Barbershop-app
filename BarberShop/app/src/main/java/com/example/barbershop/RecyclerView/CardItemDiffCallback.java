package com.example.barbershop.RecyclerView;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.barbershop.CardItem;
import com.example.barbershop.Tables.Recension;

import java.util.List;

public class CardItemDiffCallback extends DiffUtil.Callback {

    private final List<Recension> oldCardList;
    private final List<Recension> newCardList;

    public CardItemDiffCallback(List<Recension> oldCardList, List<Recension> newCardList) {
        this.oldCardList = oldCardList;
        this.newCardList = newCardList;
    }

    @Override
    public int getOldListSize() {
        return oldCardList.size();
    }

    @Override
    public int getNewListSize() {
        return newCardList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCardList.get(oldItemPosition) == newCardList.get(
                newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Recension oldItem = oldCardList.get(oldItemPosition);
        final Recension newItem = newCardList.get(newItemPosition);

        return oldItem.getName().equals(newItem.getName()) &&
                oldItem.getDate().equals(newItem.getDate()) &&
                oldItem.getRating() == newItem.getRating();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}