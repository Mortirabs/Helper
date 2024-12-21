package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter  extends ArrayAdapter<ListViewData> {
    public ListViewAdapter(@NonNull Context context, ArrayList<ListViewData> arrayList) {
        super(context,0,arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_layout, parent, false);

        }
        ListViewData currentViewItemPosition = getItem(position);
        TextView textView = null;
        if (currentItemView != null) {
            textView = currentItemView.findViewById(R.id.text_list);
            textView.setText(currentViewItemPosition.getApplicationNameList() + " : " + currentViewItemPosition.getUsageTimeString());
        }
        return currentItemView;
    }
}











