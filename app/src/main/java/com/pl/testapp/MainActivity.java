package com.pl.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<String> s = new ArrayList<>();
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s.add("1");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new Holde(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView textView = holder.itemView.findViewById(R.id.text);
                if (position % 2 == 0){
                    textView.setBackgroundColor(Color.parseColor("#ff0000"));
                }else{

                    textView.setBackgroundColor(Color.parseColor("#ff00ff"));
                }
                textView.setText(s.get(position));
            }


            @Override
            public int getItemCount() {
                return s.size();
            }
        }));
//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setAddDuration(1000);
//        defaultItemAnimator.setRemoveDuration(1000);
//        recyclerView.setItemAnimator(defaultItemAnimator);
        addData();
    }

    private void addData(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addItemView();
            }
        },3000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            addItemView();
        }
    };

    int g = 1;
    private void addItemView(){
        s.add(g++ + "");
        adapter.notifyItemChanged(s.size());
        recyclerView.scrollToPosition(s.size()-1);
        addData();
    }

    public void two(View view) {
        s.remove(s.size()-1);
        adapter.notifyItemChanged(s.size());
    }

    class Holde extends RecyclerView.ViewHolder{
        public Holde(@NonNull View itemView) {
            super(itemView);
        }
    }
    public int dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class AlphaInAnimationAdapter extends AnimationAdapter {

        public AlphaInAnimationAdapter(@NotNull RecyclerView.Adapter<? extends RecyclerView.ViewHolder> wrapped) {
            super(wrapped);
        }
        public Animator[] getAnimators(View view){
            float curTranslationX = view.getTranslationX();
            float curTranslationY = view.getTranslationY();
            Animator animator1 = ObjectAnimator.ofFloat(view, "translationY", dip2px(220),curTranslationY);
            Animator animator2 = ObjectAnimator.ofFloat(view, "translationX", -1080,curTranslationX);
            Animator animator3 = ObjectAnimator.ofFloat(view, "scaleX", 0.6f,1f);
            Animator animator4 = ObjectAnimator.ofFloat(view, "scaleY", 0.6f,1f);
            Animator animator5 = ObjectAnimator.ofFloat(view, "alpha", 0f,1f);
            setDuration(2000);
            return  new Animator[]{animator1,animator2,animator3,animator4,animator5};
        }
    }
}