package com.ldhy.com;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.sdk.mgr.LogManager;
import com.sdk.mgr.SDKManager;
import com.sdk.mgr.utility;

public class AppActivity extends Cocos2dxActivity{ 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		initHandler();
		
		//熄屏屏蔽
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//300000 为appid 与 init 里的appid 要一致
    	com.yunva.im.sdk.lib.YvLoginInit.initApplicationOnCreate(this.getApplication(),"1000541");
    	
		LogManager.isDevMode = false;
		SDKManager.getInstance().initSdk(AppActivity.this, savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		SDKManager.mainInstance.onStart();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		SDKManager.mainInstance.onRestart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SDKManager.mainInstance.onResume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		SDKManager.mainInstance.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		SDKManager.mainInstance.onStop();
	}
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SDKManager.mainInstance.onDestroy();
		SDKManager.sdkInstance.destroyToolBar();
		com.yunva.im.sdk.lib.YvLoginInit.release();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		SDKManager.mainInstance.onNewIntent(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		SDKManager.mainInstance.onActivityResult(requestCode, resultCode, data);
	}
	
	static {
        System.loadLibrary("YvImSdk");
        System.loadLibrary("cocos2dlua");
    }
	
	/*
	 * 客服端消息分发
	 */
	@SuppressLint("HandlerLeak")
    private Handler mHandler = null;
	private void initHandler() {
		mHandler = new Handler() {
			public void handleMessage(Message msg)
			{
				switch (msg.what) 
				{
				default:
					SDKManager.onHandleMessage(msg);
					break;
				}
			}
		};
		
		utility.handler = mHandler;
	}
}