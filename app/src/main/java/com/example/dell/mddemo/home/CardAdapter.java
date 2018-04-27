package com.example.dell.mddemo.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.mddemo.R;
import com.example.dell.mddemo.motion.MotionActivity;

import java.util.List;

/**
 * Description：
 * <p>
 * Created by Flower.G on 2018/3/27.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<CardEntity> cardEntityList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView cardImage;
        TextView cardDesc;
        int imageId;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            cardImage = itemView.findViewById(R.id.card_image);
            cardDesc = itemView.findViewById(R.id.card_desc);
        }
    }

    public CardAdapter(List<CardEntity> cardEntityList) {
        this.cardEntityList = cardEntityList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.my_card_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ViewHolder h = holder;
        CardEntity card = cardEntityList.get(position);
        h.cardDesc.setText(card.getCardName());
//        Glide.with(context).load(card.getCardImageId()).into(holder.cardImage);
        h.cardImage.setImageResource(card.getCardImageId());
        h.imageId = card.getCardImageId();
        h.itemView.setTransitionName("pic1");
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotionActivity.actionStart((Activity) context, h.cardView, "pic1",
                        h.imageId);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cardEntityList.size();
    }
}
