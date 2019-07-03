package com.example.subirproductosamitienda.vista;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.MyAdapter;
import com.example.subirproductosamitienda.Recursos.RecursoRecogerDatos;
import com.example.subirproductosamitienda.Recursos.SpaceItemDecoration;
import com.example.subirproductosamitienda.model.MyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<MyItem> itemList;
    
    


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_main_menu, container, false);
        initData();

        initView(view);
        setData();
        return view;
    }

    private void initView(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recicleViewMainMenu);
    }

    private void setData() {
        adapter = new MyAdapter(this.getContext(),itemList);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), RecursoRecogerDatos.NUM_OF_COLUMNS);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter!=null){
                    switch (adapter.getItemViewType(position)){
                        case 1: return  1;
                        case 0: return RecursoRecogerDatos.NUM_OF_COLUMNS;
                        default: return -1;
                    }
                }else{
                    return -1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(8));
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        itemList = new ArrayList<>();
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar de Alta Productos"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Prova Posts"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar"));
        itemList.add(new MyItem(R.drawable.dar_de_alta_producto,"Dar"));
    }

}
