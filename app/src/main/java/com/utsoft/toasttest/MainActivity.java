package com.utsoft.toasttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.utsoft.uttoastlibary.UTToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_fail)
    Button btnFail;
    @BindView(R.id.btn_common)
    Button btnCommon;
    @BindView(R.id.btn_remind)
    Button btnRemind;
    @BindView(R.id.btn_success)
    Button btnSuccess;
    @BindView(R.id.btn_warn)
    Button btnWarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_fail, R.id.btn_common, R.id.btn_remind, R.id.btn_success, R.id.btn_warn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fail:
                UTToastUtil.fail("请求失败！");
                break;
            case R.id.btn_common:
                UTToastUtil.common("普通提醒！");
                break;
            case R.id.btn_remind:
                UTToastUtil.remind("提示一下！");
                break;
            case R.id.btn_success:
                UTToastUtil.success("提醒成功！");
                break;
            case R.id.btn_warn:
                UTToastUtil.warn("严重警告！");
                break;
        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button:
//                UTToastUtil.common("提示一下！");
//                break;
//            case R.id.button2:
//                new Thread() {
//                    @Override
//                    public void run() {
//                        UTToastUtil.success("请求成功！");
//                    }
//                }.start();
//                break;
//        }
//
//    }
}
