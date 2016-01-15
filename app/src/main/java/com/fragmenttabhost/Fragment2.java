package com.fragmenttabhost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import engineering.R;

public class Fragment2 extends ListFragment {
	private List<Map<String , Object>> data ;
	private SimpleAdapter adapter ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 初始化数据源与适配器
		data = new ArrayList<Map<String,Object>>() ;
		Map<String , Object> map1 = new HashMap<String, Object>() ;
		map1.put("icon", android.R.drawable.ic_media_play) ;
		map1.put("name", "play") ;
		data.add(map1) ;
		Map<String , Object> map2 = new HashMap<String, Object>() ;
		map2.put("icon", android.R.drawable.ic_menu_add) ;
		map2.put("name", "add") ;
		data.add(map2) ;
		Map<String , Object> map3 = new HashMap<String, Object>() ;
		map3.put("icon", android.R.drawable.ic_menu_delete) ;
		map3.put("name", "delete") ;
		data.add(map3) ;

		adapter = new SimpleAdapter(getActivity(), data, R.layout.listview_item, new String[]{"icon" , "name"}, new int[]{R.id.listview_item_iv , R.id.listview_item_tv}) ;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setListAdapter(adapter) ;
		super.onActivityCreated(savedInstanceState);
	}

	// ListView 的监听器
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "ListView选中项为 :" + position, Toast.LENGTH_SHORT).show() ;
	}
}












