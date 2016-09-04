package com.androprogrammer.dagger2sample.domain.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androprogrammer.dagger2sample.R;
import com.androprogrammer.dagger2sample.domain.listeners.RowItemElementClickListener;
import com.androprogrammer.dagger2sample.domain.util.Utility;
import com.androprogrammer.dagger2sample.models.UserDataResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wasim on 7/28/2016.
 */

public class DashBoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<UserDataResponse> data;
    private RowItemElementClickListener clickListener;

    private static final int ANIMATED_ITEMS_COUNT = 6;
    private int lastAnimatedPosition = -1;
    private boolean animateItems;

    public DashBoardListAdapter(Context context, List<UserDataResponse> mData) {

        this.ctx = context;
        this.data = mData;
    }

    public void setAnimateItems(boolean animateItems) {
        this.animateItems = animateItems;
    }

    public void setClickListener(RowItemElementClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_userImage)
        ImageView rowUserImage;
        @BindView(R.id.row_tv_userName)
        TextView rowTvUserName;
        @BindView(R.id.row_icon_mail)
        ImageView rowIconMail;
        @BindView(R.id.row_tv_email)
        TextView rowTvEmail;
        @BindView(R.id.row_icon_web)
        ImageView rowIconWeb;
        @BindView(R.id.row_userWebsite)
        TextView rowUserWebsite;

        public Viewholder(View v) {
            super(v);

            ButterKnife.bind(this,v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_dashboardlist, parent, false);

        Viewholder rowViewHolder = new Viewholder(itemView);

        itemView.setOnClickListener(view -> {
            if (clickListener != null)
            {
                clickListener.onLayoutClick(itemView, rowViewHolder.getAdapterPosition());
            }
        });

        return rowViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        runEnterAnimation(holder.itemView, position);

        UserDataResponse row_data = getItem(position);

        ((Viewholder) holder).rowTvUserName.setText(row_data.getName());
        ((Viewholder) holder).rowTvEmail.setText(row_data.getEmail());

        ((Viewholder) holder).rowUserWebsite.setText(row_data.getWebsite());

        ((Viewholder) holder).rowIconMail.setColorFilter(ctx.getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY);

        ((Viewholder) holder).rowIconWeb.setColorFilter(ctx.getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY);

        if (!TextUtils.isEmpty(row_data.getName()))
        {
            ((Viewholder) holder).rowUserImage.setImageDrawable(generateTextImage(row_data.getName()));
        }

    }

    public UserDataResponse getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utility.getScreenHeight(ctx));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(4.f))
                    .setDuration(500)
                    .start();
        }
    }

    private Drawable generateTextImage(String name)
    {
        int color1 = ColorGenerator.MATERIAL.getColor(name);

        return TextDrawable.builder()
                .buildRound(name.substring(0,1),color1);
    }

}
