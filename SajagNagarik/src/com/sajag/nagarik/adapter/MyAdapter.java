package com.sajag.nagarik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sajag.nagarik.R;

public class MyAdapter extends BaseAdapter {
	 
    private Context mContext;

    public MyAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
//        return mThumbIds.length;
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
//        return mThumbIds[arg0];
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;

        if(convertView==null){
            grid = new View(mContext);
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            grid=inflater.inflate(R.layout.mygrid_layout, parent, false);
        }else{
            grid = (View)convertView;
        }

        ImageView imageView = (ImageView)grid.findViewById(R.id.image);
//        imageView.setImageResource(mThumbIds[position]);

        return grid;
    }

}