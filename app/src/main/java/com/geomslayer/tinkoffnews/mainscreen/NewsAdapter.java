package com.geomslayer.tinkoffnews.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geomslayer.tinkoffnews.R;
import com.geomslayer.tinkoffnews.models.Title;
import com.geomslayer.tinkoffnews.utils.Utils;

import java.util.ArrayList;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Title> dataset;
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(int position) {
            // do nothing
        }
    };

    NewsAdapter(ArrayList<Title> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    void setOnclickListener(OnClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private TextView date;

        ViewHolder(View itemView, final OnClickListener listener) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);
            this.date = (TextView) itemView.findViewById(R.id.date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }

        void bind(Title title) {
            text.setText(Utils.toSpanned(title.getText()));
            date.setText(Utils.DATE_FORMAT.format(title.getPubDate()));
        }

    }

    interface OnClickListener {
        void onClick(int position);
    }

}
