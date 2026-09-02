package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.helper.model.AppInfo;


import java.util.List;

public class ListViewAdapter  extends ArrayAdapter<AppInfo> {
    public ListViewAdapter(@NonNull Context context, List<AppInfo> arrayList) {
        super(context,0,arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_layout, parent, false);
        }
        AppInfo currentViewItemPosition = getItem(position);
        if (convertView != null) {
            TextView textView = convertView.findViewById(R.id.text_list);
            textView.setText(currentViewItemPosition.getApplicationName() + " : " + currentViewItemPosition.getApplicationUsageTime());
        }
        return convertView;
    }
}











