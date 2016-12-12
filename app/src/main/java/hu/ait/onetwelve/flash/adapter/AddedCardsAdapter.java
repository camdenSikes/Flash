package hu.ait.onetwelve.flash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.onetwelve.flash.AddDeckActivity;
import hu.ait.onetwelve.flash.R;

public class AddedCardsAdapter extends RecyclerView.Adapter<AddedCardsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCardFront)
        TextView tvCardFront;
        @BindView(R.id.tvCardBack)
        TextView tvCardBack;
        @BindView(R.id.btnDeleteCard)
        Button btnDeleteCard;
        @BindView(R.id.btnEditCard)
        Button btnEditCard;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private List<String> frontList;
    private List<String> backList;

    private Context context;

    public AddedCardsAdapter(Context context){
        frontList = new ArrayList<>();
        backList = new ArrayList<>();
        this.context = context;
    }

    public AddedCardsAdapter(Context context, List<String> frontList, List<String> backList){
        this.context = context;
        if(frontList != null){
            this.frontList = frontList;
        }else {
            this.frontList = new ArrayList<>();
        }
        if(backList != null){
            this.backList = backList;
        }else {
            this.backList = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.add_card_row,parent,false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(AddedCardsAdapter.ViewHolder holder, final int position) {
        holder.tvCardFront.setText(frontList.get(position));
        holder.tvCardBack.setText(backList.get(position));

        holder.btnDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frontList.remove(position);
                backList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.btnEditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddDeckActivity) context).setCardFrontText(frontList.get(position));
                ((AddDeckActivity) context).setCardBackText(backList.get(position));
                frontList.remove(position);
                backList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return frontList.size();
    }

    public void addCard(String front, String back) {
        frontList.add(0, front);
        backList.add(0, back);
        notifyItemInserted(0);
    }

    public List<String> getFrontList() {
        Collections.reverse(frontList);
        return frontList;
    }

    public List<String> getBackList() {
        Collections.reverse(backList);
        return backList;
    }
}
