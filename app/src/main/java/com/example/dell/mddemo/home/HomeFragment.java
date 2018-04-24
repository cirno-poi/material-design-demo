package com.example.dell.mddemo.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/21.
 */

public class HomeFragment extends BaseFragment {

    private CardEntity[] cards = {new CardEntity("buduuuuuuuuu", R.mipmap.buduuuuuuuuu),
            new CardEntity("flandre", R.mipmap.flandre),
            new CardEntity("ha", R.mipmap.ha),
            new CardEntity("lian", R.mipmap.lian),
            new CardEntity("marisa", R.mipmap.marisa),
            new CardEntity("reimu", R.mipmap.reimu),
            new CardEntity("xiaowu", R.mipmap.xiaowu),
            new CardEntity("yuka", R.mipmap.yuka)
    };

    private List<CardEntity> cardEntityList = new ArrayList<>();

    private CardAdapter cardAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.content_main2;
    }

    @Override
    protected void setupView() {
//        Log.d("233333", "------HomeFragment: " + getPageName());
        initCard();
        RecyclerView recyclerView = getActivity().findViewById(R.id.home_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(cardEntityList);
        recyclerView.setAdapter(cardAdapter);

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    private void initCard() {
        cardEntityList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(cards.length);
            cardEntityList.add(cards[index]);
        }
    }
}
