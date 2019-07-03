package com.example.subirproductosamitienda.Recursos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.model.MyItem;
import com.example.subirproductosamitienda.Interface.onCardItemClickListener;
import com.example.subirproductosamitienda.vista.ProvaPost;
import com.example.subirproductosamitienda.vista.UploadProduct;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public MyAdapter(Context context, List<MyItem> myItemList) {
        this.context = context;
        this.myItemList = myItemList;
    }

    Context context;
    List <MyItem> myItemList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img_icon.setImageResource(myItemList.get(position).getIcon());
        holder.text_description.setText(myItemList.get(position).getText());
        // implement click
        holder.setOnCardItemClickListener(new onCardItemClickListener() {
            @Override
            public void onCardItemViewListener(View view, int position) {
                //start activity
                switch (position){
                    case 0:  RecursoRecogerDatos.getInstance().getFragmentManager().beginTransaction().replace(R.id.frame_layout, new UploadProduct(),"upload").addToBackStack(null).commit();
                        break;
                    case 1:  RecursoRecogerDatos.getInstance().getFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProvaPost(),"provaPost").addToBackStack(null).commit();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_icon;
        TextView text_description;
        onCardItemClickListener onCardItemClickListener;

        public void setOnCardItemClickListener(com.example.subirproductosamitienda.Interface.onCardItemClickListener onCardItemClickListener) {
            this.onCardItemClickListener = onCardItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_icon=(ImageView)itemView.findViewById(R.id.imagen_nuevo_porucot);
            text_description=(TextView)itemView.findViewById(R.id.tvDarDeAltaProductos);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
                onCardItemClickListener.onCardItemViewListener(view,getAdapterPosition());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(myItemList.size()==1){
            return 0;
        }else{
            if(myItemList.size()% RecursoRecogerDatos.NUM_OF_COLUMNS ==0){
                return  1;
            }
            else {
                return(position >1 && position == myItemList.size()-1)?0:1;
            }
        }
    }
}
