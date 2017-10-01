package com.wuzp.newspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wuzp.newspace.R;
import com.wuzp.newspace.network.entity.avatar.PicFolder;

import java.io.File;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>{
    private OnFolderItemClickListener onFolderItemClickListener;
    private Context mContext;
    private List<PicFolder> picFolders;
    private File mImgDir;

    public FolderAdapter(Context context, List<PicFolder> picFolders, File mImgDir){
        mContext = context;
        this.picFolders = picFolders;
        this.mImgDir = mImgDir;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_avatar_folder,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mImgDir == null) return;
        PicFolder picFolder = picFolders.get(position);
        String folderPath = picFolder.getDir();
        String currentFolderPath = mImgDir.getAbsolutePath();
        Glide.with(mContext)
                .load(picFolder.getFirstImagePath())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .dontAnimate()
                .crossFade()
                .into(holder.imgFolderCover);
        String tempName = picFolder.getName();
        if(tempName.startsWith("/")){
            tempName = tempName.replace("/","");
        }
        holder.textFolderName.setText(tempName);
        if(!TextUtils.isEmpty(folderPath) && folderPath.equals(currentFolderPath)){
            holder.imgChecked.setVisibility(View.VISIBLE);
        }else{
            holder.imgChecked.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onFolderItemClickListener != null){
                    onFolderItemClickListener.onItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return picFolders == null ? 0 : picFolders.size();
    }

    public void setmImgDir(File imgDir){
        if(imgDir != null){
            this.mImgDir = imgDir;
            notifyDataSetChanged();
        }
    }

    public void setOnFolderItemClickListener(OnFolderItemClickListener onFolderItemClickListener) {
        this.onFolderItemClickListener = onFolderItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFolderCover;
        private TextView textFolderName;
        private ImageView imgChecked;

        public ViewHolder(View view){
            super(view);
            imgFolderCover = (ImageView)view.findViewById(R.id.img_folder_cover);
            textFolderName = (TextView)view.findViewById(R.id.text_folder_name);
            imgChecked     = (ImageView)view.findViewById(R.id.img_checked);
        }
    }

    public interface OnFolderItemClickListener {
        void onItemClickListener(int position);
    }

}
