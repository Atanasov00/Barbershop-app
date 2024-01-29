package com.example.barbershop.RecyclerView.Appointments;

import androidx.annotation.Nullable;

import com.example.barbershop.Tables.Appointments;

import java.util.List;
import androidx.recyclerview.widget.DiffUtil;

public class CardItemDiffCallback extends DiffUtil.Callback {

    private final List<Appointments> oldCardList;
    private final List<Appointments> newCardList;

    public CardItemDiffCallback(List<Appointments> oldCardList, List<Appointments> newCardList) {
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
        return oldCardList.get(oldItemPosition) == newCardList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Appointments oldItem = oldCardList.get(oldItemPosition);
        final Appointments newItem = newCardList.get(newItemPosition);
        return oldItem.getDate() == newItem.getDate() && oldItem.getService_id() == newItem.getService_id();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
