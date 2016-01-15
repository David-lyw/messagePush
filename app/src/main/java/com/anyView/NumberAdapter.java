package com.anyView;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import engineering.R;


public class NumberAdapter extends BaseAdapter {

	private ArrayList<String> numbers = new ArrayList<String>();

	// 这个是构造代码块，当NumberAdapter创建的时候会执行
	{
		// 模拟数据
		for (int i = 0; i < 30; i++) {
			numbers.add(String.valueOf(100000 + i));
		}
	}

	@Override
	public int getCount() {
		return numbers.size();
	}

	@Override
	public Object getItem(int position) {
		return numbers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// 创建一个View
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.adapter_number_list, null);

			// 用一个ViewHolder保存View里面的引用
			holder = new ViewHolder();
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.ib_del = (ImageButton) convertView.findViewById(R.id.ib_del);

			// 把ViewHolder保存到View里面去
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 把数据显示到ViewHolder里面的控件中去
		holder.tv_number.setText(numbers.get(position));

		holder.ib_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击删除按钮的时候删除一个号码
				numbers.remove(position);
				notifyDataSetChanged();	// 通知数据发生改变了
			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView tv_number;
		ImageButton ib_del;
	}

}
