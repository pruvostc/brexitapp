package com.crispysnippets.brexitapp;

/*
 * Created by Christian Pruvost on 16/04/2017.
 * Re-using some initial code from obaro
 */
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RssFeedListAdapter
        extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private final List<RssFeedModel> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        return new FeedModelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);

        // SETS THE TITLE as a clickable URL
        //((TextView) holder.rssFeedView.findViewById(R.id.titleText)).setText("<a href=\""+
        //        rssFeedModel.getLink()+ "\">" + rssFeedModel.getTitle() + "</a>");
        holder.rssFeedView.findViewById(R.id.titleText).setClickable(true);
        ((TextView) holder.rssFeedView.findViewById(R.id.titleText)).setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href=\"" + rssFeedModel.getLink()+ "\">" + rssFeedModel.getTitle() + "</a>";
        ((TextView) holder.rssFeedView.findViewById(R.id.titleText)).setText(Helper.fromHtml(text));

        // SETS THE DESCRIPTION (converting the description into HTML when applicable)
        holder.rssFeedView.findViewById(R.id.descriptionText).setClickable(true);
        ((TextView) holder.rssFeedView.findViewById(R.id.descriptionText)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) holder.rssFeedView.findViewById(R.id.descriptionText)).setText(Helper.fromHtml(rssFeedModel.getDescription()));

        //SETS THE URL (to be removed now that the title is clickable)
        ((TextView) holder.rssFeedView.findViewById(R.id.linkText)).setText(rssFeedModel.getLink());

        //SETS THE DATE
        ((TextView) holder.rssFeedView.findViewById(R.id.pubdateText)).setText(rssFeedModel.getDate());
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }
}
