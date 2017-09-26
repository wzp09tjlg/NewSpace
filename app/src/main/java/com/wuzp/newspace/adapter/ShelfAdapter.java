package com.wuzp.newspace.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzp.newspace.R;
import com.wuzp.newspace.databinding.ItemShelfBinding;
import com.wuzp.newspace.network.entity.read.Book;
import com.wuzp.newspace.utils.GlideUtil;
import com.wuzp.newspace.utils.database.StringConvertUtil;
import com.wuzp.newspace.widget.toast.Msg;

/**
 * Created by wuzp on 2017/9/26.
 * 使用CursorLoader 跟 RecyclerView的adapter 结合使用
 */
public class ShelfAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public ShelfAdapter(Context context){
       this.mContext = context;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemShelfBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_shelf,parent,false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        ItemShelfBinding binding = holder.getBinding();
        mCursor.moveToPosition(position);
        final Book book = StringConvertUtil.getBookFromCursor(mCursor);
        GlideUtil.load(mContext,book.getImg(),R.drawable.icon_defualt_cover,R.drawable.icon_defualt_cover,binding.imgCover);
        binding.textBookTitle.setText(book.getTitle());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Msg.getInstance().show("you click book:" + book.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
