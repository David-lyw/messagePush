package com.slidemenu;

import android.os.Bundle;
import android.view.View;

import com.common.BaseActivity;

import engineering.R;


/**
 * Created by sd on 2015/9/28.
 * 物业日常管理
 * sun
 */
public class ProcessPropertyManageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(R.layout.process_property_manager);
        initData();

    }

    @Override
    public void initView(int viewId) {
        super.initView(viewId);
        /*plan_subject_back = (ImageView) findViewById(R.id.plan_subject_back);
        lv = (ListView) findViewById(R.id.plan_subject_listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanSubjectActivity.this, PlanMajorsActivity.class);
                intent.putExtra("subjectId", data.get(position).getId());
                intent.putExtra("subjectName", data.get(position).getSubjectName());
                startActivity(intent);
            }
        });
        setListener(plan_subject_back);
*/

    }



    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.plan_subject_back:
                finish();
                break;
            default:
                break;
        }*/
    }

    /**
     * 初始化数据的方法
     * sun
     */
    public void initData(){

    }




}
