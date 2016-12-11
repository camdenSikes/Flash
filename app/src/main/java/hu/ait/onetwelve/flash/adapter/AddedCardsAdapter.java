package hu.ait.onetwelve.flash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.onetwelve.flash.R;

/**
 * Created by Camden Sikes on 12/11/2016.
 */

public class AddedCardsAdapter extends RecyclerView.Adapter<AddedCardsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCardFront)
        TextView tvCardFront;
        @BindView(R.id.tvCardBack)
        TextView tvCardBack;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    List<String> frontList;
    List<String> backList;

    public AddedCardsAdapter(){
        frontList = new ArrayList<>();
        backList = new ArrayList<>();
        //TODO: if editing, add existing cards
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.add_card_row,parent,false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(AddedCardsAdapter.ViewHolder holder, int position) {
        holder.tvCardFront.setText(frontList.get(position));
        holder.tvCardBack.setText(backList.get(position));
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
        return frontList;
    }

    public List<String> getBackList() {
        return backList;
    }
}
