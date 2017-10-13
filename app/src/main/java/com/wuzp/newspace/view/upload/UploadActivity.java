package com.wuzp.newspace.view.upload;

import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityUploadBinding;
import com.wuzp.newspace.widget.toast.Msg;

import java.io.File;

/**
 * Created by wuzp on 2017/10/3.
 */
public class UploadActivity extends NewActivity<ActivityUploadBinding,UploadPresenter> implements UploadView, View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected UploadPresenter createPresenter() {
        return new UploadPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.btnUpload.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload:
                   File file = new File("");
                    presenter.upload(file);
                break;
        }
    }

    @Override
    public void setData(String url) {
        Msg.getInstance().show("upload success,and url:" + url);
    }

    @Override
    public void error(int code, String msg) {
        Msg.getInstance().show("upload failure,code:" + code +"   msg:" + msg);
    }

}
