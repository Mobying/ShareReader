package com.france.sharereader.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.france.sharereader.R;
import com.france.sharereader.adapter.LeftMenuAdapter;
import com.france.sharereader.util.LogUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends ActionBarActivity {
	//声明相关变量
	@ViewInject(id=R.id.tl_custom)private Toolbar toolbar;
	@ViewInject(id=R.id.dl_left)private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	@ViewInject(id=R.id.lv_left_menu)private ListView lvLeftMenu;
	private SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FinalActivity.initInjectedView(this);//实现IOC注解组件
//		initViews(); //获取控件
		initLeftSlip();//侧滑
		initClickEvents();//注册事件


	}
	private void initViews() {
		toolbar = (Toolbar) findViewById(R.id.tl_custom);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
		lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
		//判断侧滑点击的按钮并处理逻辑
	}
	private void initClickEvents(){
		lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LogUtil.ShowLog("position:"+position);
			}

		});
	}
	private void initLeftSlip(){
		toolbar.setTitle(R.string.app_name);//设置Toolbar标题
		toolbar.setTitleTextColor(R.color.white); //设置标题颜色
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//创建返回键，并实现打开关/闭监听
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//设置菜单列表
		simpleAdapter = new LeftMenuAdapter().getAdapter(this);
		lvLeftMenu.setAdapter(simpleAdapter);
	}
}