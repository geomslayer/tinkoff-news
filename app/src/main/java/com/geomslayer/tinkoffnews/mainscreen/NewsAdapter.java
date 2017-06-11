package com.geomslayer.tinkoffnews.mainscreen;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geomslayer.tinkoffnews.R;
import com.geomslayer.tinkoffnews.models.Title;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final DateFormat dateFormat =
            new SimpleDateFormat("HH:mm, dd MMMM yyyy", new Locale("ru", "RU"));
    private ArrayList<Title> dataset;
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(int position) {
            // pass
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

    public void setOnclickListener(OnClickListener listener) {
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                text.setText(Html.fromHtml(title.getText(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                text.setText(Html.fromHtml(title.getText()));
            }
            date.setText(dateFormat.format(title.getPubDate()));
        }

    }

    interface OnClickListener {
        void onClick(int position);
    }

}
