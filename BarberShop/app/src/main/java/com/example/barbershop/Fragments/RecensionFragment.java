package com.example.barbershop.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.CardItem;
import com.example.barbershop.R;
import com.example.barbershop.RecyclerView.CardAdapter;
import com.example.barbershop.RecyclerView.OnItemListener;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.ViewModel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import Utils.Utilities;

public class RecensionFragment extends Fragment implements OnItemListener {

    private CardAdapter adapter;

    private RecyclerView recyclerView;

    ListViewModel listViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.reviews));
            List<Recension> list = new ArrayList<>();


            listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            listViewModel.getRecensionInfo().observe(activity, new Observer<List<Recension>>() {
                @Override
                public void onChanged(List<Recension> recensions) {
                    list.addAll(recensions);
                }
            });

            setRecyclerView(activity, list);


        }
    }

    public void setRecyclerView(final Activity activity, List<Recension> list) {
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        /*List<CardItem> list = new ArrayList<>();
        list.add(new CardItem("ic_haircut2", "Matteo Rossi",
                "Description", "25/01/2024", 3));
        list.add(new CardItem("ic_haircut1", "Atanas Atanasov",
                "Description", "24/01/2024", 5));*/
        final OnItemListener listener =  this;
        if(list.size() == 0){
            adapter = new CardAdapter(listener, Utilities.getRecensionList(), activity);
        } else {
            adapter = new CardAdapter(listener, list, activity);
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if (activity != null){
            Utilities.insertHomeActivityFragment((AppCompatActivity) activity, new DetailsFragment(), DetailsFragment.class.getSimpleName());
            listViewModel.setItemSelected(adapter.getItemSelected(position));
        }
    }
}
