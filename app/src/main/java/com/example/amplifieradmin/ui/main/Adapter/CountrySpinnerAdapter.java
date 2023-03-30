package com.example.amplifieradmin.ui.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.amplifieradmin.R;
import com.example.amplifieradmin.data.model.GetCountriesData;

import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<GetCountriesData> {

    private boolean hint;
    private int selectedId = -1;
    private List<GetCountriesData> items;
    private OnClickListener listener;

    public CountrySpinnerAdapter(Context context, boolean hint, List<GetCountriesData> items,
                                 OnClickListener listener) {
        super(context, 0, items);
        this.hint = hint;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        SpinnerItemHolder holder;

        if (row == null) {
            holder = new SpinnerItemHolder();
            if (hint) {
                row = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_spinner_hint, parent, false);
                holder.tilTitle = row.findViewById(R.id.til_title);
                holder.etTitle = row.findViewById(R.id.et_title);
             //   holder.tilTitle.setHintEnabled(false);
            } else {
                row = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_spinner_plain, parent, false);
                holder.tvTitle = row.findViewById(R.id.tv_title);
            }
            row.setTag(holder);
        } else {
            holder = (SpinnerItemHolder) row.getTag();
        }

        if (hint) {
            if (position == 0) {
                if (items.get(position).getName().contains("|")) {
                    holder.etTitle.setHint(items.get(position).getName().split("\\|")[0]);
                } else {
                    holder.etTitle.setHint(items.get(position).getName());
                }
            } else {
              //  holder.tilTitle.setHintEnabled(true);
              //  holder.tilTitle.setHint(items.get(0).getName());

                if (items.get(position).getName().contains("|")) {
                    holder.etTitle.setText(items.get(position).getName().split("\\|")[0]);
                } else {
                    holder.etTitle.setText(items.get(position).getName());
                }
            }
        } else {
            if (items.get(position).getName().contains("|")) {
                holder.tvTitle.setText(items.get(position).getName().split("\\|")[0]);
            } else {
                holder.tvTitle.setText(items.get(position).getName());
            }
        }

        if (hint) {
            holder.etTitle.setOnClickListener(listener);
        } else {
            holder.tvTitle.setOnClickListener(listener);
        }

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (hint && position == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_spinner_dropdown_empty, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_spinner_dropdown, parent, false);
            TextView textview = view.findViewById(R.id.tv_title);
            ImageView check = view.findViewById(R.id.iv_check);
            if (items.get(position).getName() != null) {
                if (items.get(position).getName().contains("|")) {
                    textview.setText(items.get(position).getName().split("\\|")[0]);
                } else {
                    textview.setText(items.get(position).getName());
                }
            }

            if (position == selectedId) {
                check.setImageResource(R.drawable.ic_check_circle);
                check.setVisibility(View.VISIBLE);
            } else {
                check.setImageResource(0);
                check.setVisibility(View.GONE);
            }
        }

        return view;
    }

    public void updateSelection(int selectedId) {
        this.selectedId = selectedId;
        notifyDataSetChanged();
    }

    public int getSelectedId() {
        return selectedId;
    }

    public List<GetCountriesData> getList() {
        return items;
    }

    static class SpinnerItemHolder {
        TextView tvTitle;
        RelativeLayout tilTitle;
        EditText etTitle;
    }
}