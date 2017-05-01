package com.example.library;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by joginderpal on 01-05-2017.
 */
public class StaggeredView extends ScrollView {

    BaseAdapter baseAdapter;
     int mDeviceWidth;
     int mRowWidth;
     LinearLayout mRow;
    LinearLayout mParent;;
    Context context;

    public StaggeredView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public StaggeredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public StaggeredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StaggeredView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init();
    }

    public void init(){

        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mDeviceWidth=displayMetrics.widthPixels;

        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        mParent=new LinearLayout(context);
        mParent.setOrientation(LinearLayout.VERTICAL);
        mParent.setLayoutParams(params);
        addView(mParent);
    }

   public void setmAdapter(BaseAdapter baseAdapter){

       this.baseAdapter=baseAdapter;
       generateSpannableTextViewGrid();
   }

    private void generateSpannableTextViewGrid() {

            for (int i=0;i<baseAdapter.getCount();i++){

                TextView tx= (TextView) baseAdapter.getView(i,null,this);
                int padding = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    padding=tx.getPaddingEnd()+tx.getPaddingStart();
                }
                String item = (String) baseAdapter.getItem(i);
                int itemWidth= (int) (tx.getPaint().measureText(item)+padding);
                if (i==0){
                    mRow=getRow();
                    addChildView(tx,itemWidth);
                }else{

                    if(mRowWidth + itemWidth <= mDeviceWidth){
                        addChildView(tx,itemWidth);
                    }
                    else{
                         setFullWidthRow();
                         mParent.addView(mRow);
                         mRow = getRow();
                         addChildView(tx, itemWidth);
                    }

                    if(i == (baseAdapter.getCount() - 1)){
                        mParent.addView(mRow);
                    }

                         // DONE ON THIS SIDE.........
                }
            }

    }

    private void setFullWidthRow(){
        // Difference between row with child and device width
        int remainWidth = mDeviceWidth - mRowWidth;
        // Distributes equally remaining space between child
        int childSpace = remainWidth / mRow.getChildCount();
        int spaceReminder = remainWidth % mRow.getChildCount();

        // reset width of all child
        for (int i = 0; i < mRow.getChildCount(); i++) {

            if(spaceReminder > 0 && i == (mRow.getChildCount() - 1)){
                childSpace = childSpace + spaceReminder;
            }

            View view = mRow.getChildAt(i);
            resetChildWidth(view, childSpace);
        }

        mRowWidth = 0;
    }

    private void resetChildWidth(final View view, final int childSpace) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                int childWidth = view.getWidth();

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.width = childWidth + childSpace + 10;
                view.setLayoutParams(params);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }



    public void addChildView(View v,int newWidth){

       mRow.addView(v);
       resizeRow(newWidth,v);
   }

    private void resizeRow(int width, View view){
        // LinearLayout row params
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRow
                .getLayoutParams();
        mRowWidth = mRowWidth + width;
        params.weight = mRow.getChildCount();
        mRow.setLayoutParams(params);
    }



    public LinearLayout getRow(){

        LinearLayout l= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.row_item_spanneble,null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        l.setLayoutParams(layoutParams);
        l.setOrientation(LinearLayout.HORIZONTAL);
        return l;
    }

}
