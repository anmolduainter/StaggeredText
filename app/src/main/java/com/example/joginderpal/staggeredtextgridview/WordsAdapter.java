package com.example.joginderpal.staggeredtextgridview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.library.ColorGen;

import java.util.List;

/**
 * Created by joginderpal on 01-05-2017.
 */
public class WordsAdapter extends BaseAdapter {

    private Context context;
    private List<String> li;

    public WordsAdapter(Context context, List<String> li) {
        this.context = context;
        this.li = li;
    }

    @Override
    public int getCount() {
        return li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView view= (TextView) LayoutInflater.from(context).inflate(R.layout.row_item_word,null);

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.selector_items);
        drawable.setColorFilter(ColorGen.MATERIAL.getRandomColor(), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }
        view.setText(li.get(position));
        return view;
    }
}
