package com.example.sjtujj;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class T4_xxxqActivity extends Activity {
private String mid;
private Xxxq_net Xxxq_netItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_t4_xxxq);
		
		mid = getIntent().getExtras().getString("id");
		
		getData();
		
		//���Ϸ��ؼ�
        LinearLayout lv_up = (LinearLayout)findViewById(R.id.t4_xxxq_up);
        lv_up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	private void getData(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", mid);
		MyHttpClient.doPost2(T4_xxxqActivity.this, new NetRespondPost() {
			@Override
			public void netWorkOk(String json) {
				JSONObject jsonObject = JSONObject.parseObject(json);
//				JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
				String code = jsonObject.getString("code");
				if (code.equals("200")) {
					JSONObject data1 = jsonObject.getJSONObject("data");
					Xxxq_netItems = JSONObject.parseObject(data1.toString(), Xxxq_net.class);	
					setData();
				}
				else
					Toast.makeText(T4_xxxqActivity.this, jsonObject.getString("desc"), Toast.LENGTH_LONG).show();
			}
			@Override
			public void netWorkError() {
			}
		}, MyPath.getMessageInfo_path, map, MyPath.getSessionid());		
		
	}
	
	private void setData(){
		TextView tv1 = (TextView)findViewById(R.id.t4_xxxq_tv1);
		tv1.setText(Xxxq_netItems.getTitle());
		TextView tv2 = (TextView)findViewById(R.id.t4_xxxq_tv2);
		tv2.setText(Xxxq_netItems.getTime());
		TextView tv3 = (TextView)findViewById(R.id.t4_xxxq_tv3);
		tv3.setText(Html.fromHtml(Xxxq_netItems.getContent()));
		
	}
}
