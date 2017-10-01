package com.wuzp.newspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wuzp.newspace.R;

import java.io.File;
import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder>{
    private Context mContext;
    private List<String> mData;
    private LayoutInflater inflater;
    private String parentPath = "";
    private OnAvatarItemClickListener onItemClickListener;

    public AvatarAdapter(Context context, List<String> data, String parentPath){
        this.mContext = context;
        this.mData = data;
        this.parentPath = parentPath;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_avatar,null,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String path = parentPath + File.separator + mData.get(position);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClickListener(path);
                }
            }
        });
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .dontAnimate()
                .crossFade()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData == null? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view){
            super(view);
            img = (ImageView)view.findViewById(R.id.img);
        }
    }

    public void setOnItemClickListener(OnAvatarItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnAvatarItemClickListener {
        void onItemClickListener(String path);
    }
}
