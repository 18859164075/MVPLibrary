package com.xujl.baselibrary.mvp.port;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃  神兽保佑
 * 　　　　┃　　　┃  代码无bug
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * Created by xujl on 2017/4/28.
 */

public interface IBasePresenter extends View.OnClickListener {

    void exit();//退出界面
    Context exposeContext ();//获取context

    Activity exposeActivity ();//获取activity

    IBasePresenter exposeBasePresenter ();//获取Presenter实例，慎用

    View exposeRootView ();//获取根视图

    IBaseView exposeView ();//获取视图模块

    IBaseModel exposeModel ();//获取数据模块

    boolean isMVP ();//设置是否MVP加载模式

}