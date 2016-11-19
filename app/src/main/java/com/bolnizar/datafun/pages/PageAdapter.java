package com.bolnizar.datafun.pages;

import com.bolnizar.datafun.R;
import com.bolnizar.datafun.Utils;
import com.bolnizar.datafun.facebook.Page;
import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private List<Page> mPages = new ArrayList<>();
    private final PrettyTime mPrettyTime = new PrettyTime();

    public void setPages(List<Page> mPages) {
        this.mPages = mPages;
        notifyDataSetChanged();
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    @Override
    public void onBindViewHolder(PageViewHolder holder, int position) {
        Page page = mPages.get(position);
        holder.subtitle.setText(mPrettyTime.format(page.date));
        holder.title.setText(page.name);
        String url = Utils.imageUrl(page.id);
        Glide.with(holder.image.getContext()).load(url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPages.size();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.page_title)
        TextView title;
        @BindView(R.id.page_subtitle)
        TextView subtitle;
        @BindView(R.id.page_delete)
        View delete;
        @BindView(R.id.page_image)
        ImageView image;

        public PageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
