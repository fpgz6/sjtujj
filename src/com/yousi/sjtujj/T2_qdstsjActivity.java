package com.yousi.sjtujj;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.yousi.util.DB;
import com.yousi.util.MyHttpClient;
import com.yousi.util.MyPath;
import com.yousi.util.NetRespondPost;
import com.yousi.util.NewMyPath;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

public class T2_qdstsjActivity extends Activity {
private String rid = "";
private String time = "2015-03-01 12:00";
private Date date;
private Calendar calendar;
private EditText et1;
private EditText et2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_t2_qdstsj);
		
		rid = getIntent().getExtras().getString("rid");

		calendar = Calendar.getInstance();
		date = calendar.getTime();
		
		//listener
        final DatePickerDialog.OnDateSetListener DateSet = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
                // 每次保存设置的日期
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                et1.setText(str);
            }
        };	
        
        final TimePickerDialog.OnTimeSetListener TimeSet = new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				
				String str = hourOfDay + ":" + minute;

                et2.setText(str);
			}
		};
		
		
		//设置时间日期
		et1 = (EditText)findViewById(R.id.t2_qdstsj_et1);
		et1.setInputType(InputType.TYPE_NULL);
		
		et1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog datePickerDialog = new DatePickerDialog(
                        T2_qdstsjActivity.this, DateSet, calendar
                                .get(Calendar.YEAR), calendar
                                .get(Calendar.MONTH), calendar
                                .get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
			}
		});
		
		et2 = (EditText)findViewById(R.id.t2_qdstsj_et2);
		et2.setInputType(InputType.TYPE_NULL);
		
		et2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialog timePickerDialog = new TimePickerDialog(
                        T2_qdstsjActivity.this, TimeSet, calendar
                                .get(Calendar.HOUR_OF_DAY), calendar
                                .get(Calendar.MINUTE), true);
                timePickerDialog.show();
			}
		});		
		
		//确定提交
		Button bt = (Button)findViewById(R.id.t2_qdstsj_queding);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alert = new AlertDialog.Builder(T2_qdstsjActivity.this).create();
				alert.setTitle("注意");
				alert.setMessage("确保你已联系过家长，沟通后家长愿意接受你的试教！");
				alert.setButton(DialogInterface.BUTTON_POSITIVE,"确认", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					//TODO
						PostData();
					}
				});
				alert.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					//DONOTHING
					}
				});	
				alert.show();
				
			}
		});
		
		
		//左上返回键
        LinearLayout lv_up = (LinearLayout)findViewById(R.id.t2_qdstsj_up);
        lv_up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	
	
	private void PostData(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rid", rid);
		map.put("listentime",et1.getText().toString()+" "+et2.getText().toString());
		MyHttpClient.doPost2(T2_qdstsjActivity.this, new NetRespondPost() {
			@Override
			public void netWorkOk(String json) {
				JSONObject jsonObject = JSONObject.parseObject(json);
//				JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
				String code = jsonObject.getString("code");
				if (code.equals("200")) {
					//return
					finish();
				}
				else
					Toast.makeText(T2_qdstsjActivity.this, jsonObject.getString("desc"), Toast.LENGTH_LONG).show();
			}
			@Override
			public void netWorkError() {
			}
		}, NewMyPath.confirmTeach_path, map, DB.getSessionid(T2_qdstsjActivity.this));
	}
}
