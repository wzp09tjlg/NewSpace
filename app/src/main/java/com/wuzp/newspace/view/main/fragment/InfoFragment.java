package com.wuzp.newspace.view.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.adapter.BindingViewHolder;
import com.wuzp.newspace.adapter.CommonAdapter;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentInfoBinding;
import com.wuzp.newspace.databinding.ItemInfoBinding;
import com.wuzp.newspace.network.entity.main.InfosBean;
import com.wuzp.newspace.network.entity.widget.TagBean;
import com.wuzp.newspace.utils.ActivityUtil;
import com.wuzp.newspace.utils.GlideUtil;
import com.wuzp.newspace.view.detail.WebDetailActivity;
import com.wuzp.newspace.widget.tag.TagView;
import com.wuzp.newspace.widget.tag.TagViewSub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/9/23.
 */
@SuppressWarnings("all")
public class InfoFragment extends MvpFragment<FragmentInfoBinding,InfoPresenter> implements InfoView {

    private List<InfosBean.InfoBean> mData = null;
    private CommonAdapter<InfosBean.InfoBean> infoAdapter;

    @Override
    protected InfoPresenter createPresenter() {
        return new InfoPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void onCreateView() {
        super.onCreateView();
        presenter.start();
    }

    @Override
    protected void initView() {
        super.initView();
        binding.layoutTitle.imgTitleBack.setVisibility(View.INVISIBLE);
        binding.layoutTitle.imgTitleMenu.setVisibility(View.INVISIBLE);
        binding.layoutTitle.textTitle.setText("资讯");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        ((FragmentInfoBinding)binding).recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        infoAdapter = new CommonAdapter<InfosBean.InfoBean>(mContext,R.layout.item_info) {
            @Override
            public void convert(BindingViewHolder holder, int position) {
                ItemInfoBinding binding = holder.getBinding();
                final InfosBean.InfoBean bean = mData.get(position);
                String header = bean.getImage().startsWith("http:") || bean.getImage().startsWith("https:") ? "" : "http:";
                String tear = bean.getImage().endsWith(".jpg") || bean.getImage().startsWith("jpeg") ? "" : ".jpg";
                String coverUrl = header + bean.getImage() + tear;
                GlideUtil.load(mContext,coverUrl,R.drawable.icon_defualt_cover,R.drawable.icon_defualt_cover,binding.imgCover);
                binding.textName.setText(bean.getName());
                binding.textTime.setText(bean.getDay());
                binding.textTitle.setText(bean.getTitle());
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("TITLE",bean.getName());
                        bundle.putString("URL",bean.getUrl());
                        ActivityUtil.startWithBundle(mContext, WebDetailActivity.class,bundle);
                    }
                });
                dividerTag(binding,bean.getAid());
            }

            private void dividerTag(ItemInfoBinding binding,String tags){//做简单测试 将数据拆分为多个Tag
                binding.tags.removeAllViews();//首先是清空layout，便于复用
                binding.tags.setMaxLines(1);
                if(TextUtils.isEmpty(tags)) return;
                List<TagBean> listTags = getTags(tags,"","");
                TagView.MarginLayoutParams mParams = new TagView.MarginLayoutParams(
                        TagView.MarginLayoutParams.WRAP_CONTENT,mContext.getResources().getDimensionPixelSize(R.dimen.dimen_16dp));
                int margin = (int) mContext.getResources().getDimension(R.dimen.dimen_8dp);
                int  mTagPaddingTop = mContext.getResources().getDimensionPixelSize(R.dimen.dimen_2dp);
                int mTagPaddingLeft = mContext.getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
                int tempSize = listTags.size();
                TagBean tagBean = null;
                for(int i=0;i<tempSize;i++) {
                    tagBean = listTags.get(i);
                    if(TextUtils.isEmpty(tagBean.getName().trim())) continue;//空值不显示出来
                    mParams.setMargins(0, 0, margin, 0);
                    final TagViewSub textView = new TagViewSub(mContext);
                    textView.setText(tagBean.getName().toString());
                    textView.setTextSize(10f);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(mTagPaddingLeft,0,mTagPaddingLeft,0);
                    if(tagBean.getName().length() < 2 || TagBean.TYPE_ALBUM == tagBean.getType()){
                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.drawable_tag_normal_small_album));
                        textView.setTextColor(Color.parseColor("#ff5AB4DC"));//连载状态 浅蓝色
                    }else if(tagBean.getName().length() < 5 ||TagBean.TYPE_FINISHED == tagBean.getType()){
                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.drawable_tag_normal_small_finished));
                        textView.setTextColor(Color.parseColor("#ffF06E5F"));//完结状态 浅红色
                    }else if( tagBean.getName().length() < 8 || TagBean.TYPE_MATCH == tagBean.getType()){
                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.drawable_tag_normal_small_match));
                        textView.setTextColor(Color.parseColor("#ffff9600"));//关键字匹配状态 浅橘黄色
                    }else {
                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.drawable_tag_normal_small));
                        textView.setTextColor(Color.parseColor("#ff666666"));//其他默认状态 浅灰色
                    }
                    textView.setTag(listTags.get(i));
                    binding.tags.addView(textView,mParams);
                }
            }

            private ArrayList<TagBean> getTags(String tags, String status, String wordCount){
                if(!TextUtils.isEmpty(wordCount)){
                    tags = wordCount + "," + tags;
                }
                if(!TextUtils.isEmpty(status)){
                    tags = status + "," + tags;
                }
                ArrayList<TagBean> listTags = new ArrayList<>();
                String[] tempTags = tags.split("6");//商定的就是以 6 连接（看数据有很多的6）
                int len = tempTags.length;
                String macAlbum = status;
                String macFinished = "已完结";
                String macMatch = "Match";
                TagBean tagBean = null;
                for(int i=0;i<len;i++){
                    if(macFinished.equals(tempTags[i])){
                        tagBean = new TagBean();
                        tagBean .setName(tempTags[i]);
                        tagBean.setType(TagBean.TYPE_FINISHED);
                        listTags.add(0,tagBean);
                    }else if(tempTags[i].matches(macAlbum)){
                        tagBean = new TagBean();
                        tagBean .setName(tempTags[i]);
                        tagBean.setType(TagBean.TYPE_ALBUM);
                        listTags.add(tagBean);
                    }else if(macMatch.equals(tempTags[i])){
                        tagBean = new TagBean();
                        tagBean .setName(tempTags[i]);
                        tagBean.setType(TagBean.TYPE_MATCH);
                        listTags.add(tagBean);
                    }else {
                        if(!TextUtils.isEmpty(tempTags[i].trim())){
                            tagBean = new TagBean();
                            tagBean .setName(tempTags[i]);
                            tagBean.setType(TagBean.TYPE_NORMAL);
                            listTags.add(tagBean);
                        }
                    }
                }
                return listTags;
            }
        };
        ((FragmentInfoBinding)binding).recycler.setAdapter(infoAdapter);
        showWaiting();
    }

    @Override
    public void setData(List<InfosBean.InfoBean> data) {
        if(mData != null && mData.size() > 0){
            mData.addAll(data);
        }else if(mData == null){
            mData = data;
        }
        infoAdapter.setData(mData);
        hideWaiting();
    }

}
