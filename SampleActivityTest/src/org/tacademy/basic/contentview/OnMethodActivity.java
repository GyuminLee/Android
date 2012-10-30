package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OnMethodActivity extends Activity {

	TextView tv1;
	LinearLayout layout;
	
	public static final int MY_NEW_WINDOW_FEATURE = 30;
	
	int menu_show_level = 0;
	
	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Activity가 최초로 생성되었을 때 호출됨.
	 * savedInstanceState는 Activity가 BG상태로 내려갔다 Android System에 의해 죽는 경우
	 * onSaveInstanceState에서 Bundle에 저장한 값이 넘어옴.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    getWindow().requestFeature(MY_NEW_WINDOW_FEATURE);
	    setTheme(R.style.Theme_PlainText);
	    setTitle("Test");
	    setTitleColor(Color.BLUE);
	    LayoutInflater inflater = getLayoutInflater();
	    inflater.setFactory(this);
	    setContentView(R.layout.onmethod_layout);
	    tv1 = (TextView)findViewById(R.id.textView1);
	    layout = (LinearLayout)findViewById(R.id.layout);
	    registerForContextMenu(tv1);
	    registerForContextMenu(layout);
	    Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * Panel Test를 위한 Button임.
			 * openPanel을 이용하여 Window의 feature를 전달하면, onCreatePanelView, onCreatePanelMenu, onPreparePanel 등이 호출됨.
			 * Window feature에 새로운 feature를 추가하는 것도 가능해 보이나, Android에 reserved되어 있는 feature가 있기 때문에 사용시 주의 필요.
			 * 새로운 feature를 사용하는 경우 MAX 31까지만 사용가능(내부적으로 feature는 0x1 << feature 하여 사용하기 때문)
			 * 새로운 feature를 사용하는 경우는 상황에 따라 다른 Menu화면을 보여주고자 할 때 사용 가능.
			 * 해당 feature를 이용하기 위해서는 Window에 requestFeature로 먼저 등록되어 있어야 함.
			 * FEATURE_OPTIONS_PANEL과 FEATURE_CONTEXT_MENU는 default로 등록되어 있음.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// openOptionsMenu()와 동일한 동작임.
				getWindow().openPanel(Window.FEATURE_OPTIONS_PANEL, null);
//				getWindow().openPanel(MY_NEW_WINDOW_FEATURE, null);
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * openContextMenu를 이용하여 context menu를 보여줄 수 있음.
			 * 내부적으로는 view.showContextMenu를 호출함. showContextMenu는 ContextMenu가 보여졌는지를 return함.
			 * ContextMenu의 경우, View의 LongClick을 통해 호출됨.(showContextMenu 호출됨)
			 * OnLongClickListener가 View에 설정되어 있는 경우, 
			 * onLongClick에서 true를 넘겨주면 ContextMenu가 안 뜨고, false를 넘겨주면 ContextMenu가 뜸.
			 * 
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openContextMenu(tv1);
				//boolean isVisibled = tv1.showContextMenu();
			}
		});
	    
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 * onCreate의 savedInstanceState가 null이 아닌 경우 호출됨.
	 * savedInstanceState에 있는 값을 보고 Activity의 정보를 복원.
	 * View의 경우 setSaveEnabled를 이용하여 상태를 유지할 수 있음.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onRestoreInstnaceState", Toast.LENGTH_SHORT).show();
		super.onRestoreInstanceState(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 * Activity에서 initialize작업을 완료한 후 호출됨.
	 * onStart, onRestoreInstanceState가 수행된 후 호출.
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPostCreate", Toast.LENGTH_SHORT).show();
		super.onPostCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 * Activity가 화면을 점유 한 다음 호출.
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 * Activity가 BG에 있다가 다시 FG로 올라오기 시작할 때 호출.
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onReStart", Toast.LENGTH_SHORT).show();
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 * Activity가 F/G Event를 받을 수 있는 시점에 호출.
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostResume()
	 * 완전히 Resume이 된 후에 호출.
	 */
	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPostResume", Toast.LENGTH_SHORT).show();
		super.onPostResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 * Activity가 FG에서 BG로 내려갈 때 호출.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 * Activity가 FG Event를 받을 수 없게 되는 시점에 호출.
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onUserLeaveHint()
	 * 사용자의 선택에 의해 FG에서 User Interaction에 의해 BG로 내려갈 때 호출됨.
	 * 예를 들어 Home Key에 의해 BG로 갈 때는 호출되고, Call이 와서 BG로 갈 경우에는 호출되지 않음.
	 */
	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onUserLeaveHint", Toast.LENGTH_SHORT).show();
		super.onUserLeaveHint();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateThumbnail(android.graphics.Bitmap, android.graphics.Canvas)
	 * 언제 어떻게 호출되는지 좀 더 확인해 봐야 겠다.
	 * Running Task의 Thumbnail을 교체할 때 사용한다고 되어 있는데, 인터넷 문서에서는 현재 지원되지 않는다고 나옴.
	 * Platform source 확인 결과, com.android.internal.R.dimen.thumbnail_height와 width가 모두 0이어서 onCreateThumbnail이 호출안됨.
	 */
	@Override
	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onCreateThumbnail", Toast.LENGTH_SHORT).show();
		return super.onCreateThumbnail(outBitmap, canvas);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDescription()
	 * stop시 해당 함수가 호출되어Description이 변경되어야 하나 변경되지 않음.
	 * Platform Source를 좀 더 분석해 봐야 알것 같음.
	 */
	@Override
	public CharSequence onCreateDescription() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onCreateDescription", Toast.LENGTH_SHORT).show();
		return "테스트";
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 * 화면에서 사라질 때 호출됨.
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		super.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 * ActivityStack에서 제거될 때 호출됨. (일반적으로 Backkey를 누르거나 finish()를 호출하는 경우 호출됨)
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 * 일반적으로 configuration이 변경되면, Activity를 죽였다가 다시 띄우는데, 
	 * AndroidManifest.xml에서 Activity에 configChanges를 설정하면 Activity를 죽였다 띄우지 않고
	 * onConfigurationChanged가 호출됨.
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "onConfigurationChanged - PORTRAIT", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "onConfigurationChanged - LANDSCAPE", Toast.LENGTH_SHORT).show();
		}
		super.onConfigurationChanged(newConfig);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 * Configuration이 변경되면, Activity를 죽였다 다시 띄우는데, 
	 * 이때, onStop다음에 호출되어 보존하고 싶은 instance(일반적으로 activity)를 넘겨주었다가
	 * Activity가 다시 시작되면, getLastNonConfigurationInstance()로  해당 instance를 가져와서 처리.
	 * Fragment가 나오면서 deprecated 됨.
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onRetainNonConfigurationInstance", Toast.LENGTH_SHORT).show();
		return super.onRetainNonConfigurationInstance();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onLowMemory()
	 * System에 메모리가 부족할 때 호출됨.
	 * cache등을 nullify시키는 작업 등을 해 주어야 함.
	 * 해당 함수 호출 후 gc가 호출됨.
	 */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onLowMemory", Toast.LENGTH_SHORT).show();
		super.onLowMemory();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * Key가 눌렸을 때 호출.
	 * Home Key를 받아서 처리하기를 원하는 경우,
	 * onAttachedToWindow()에서 getWindow().getType(WindowManager.LayoutParams.TYPE_KEYGUARD) 
	 * 또는 getWindow().getType(WindowManager.LayoutParams.TYPE_KEYGAURD_DIALOG)를 설정해 주면됨.
	 * 참고로 HomeKey는 KeyEvent.KEYCODE_HOME 임.
	 * super의 onKeyDown에서는 keyMode에 따라 서로 다른 동작이 호출됨.
	 * DEFAULT_KEY_DISABLE : 아무 동작도 하지 않고 return false;
	 * DEFAULT_KEY_SHORTCUT : menu에 shortcut이 등록되어 있는 경우 해당 shortcut을 호출함. menu가 떠 있지 않은 상태에서 shortcut을 확인하려면 이 모드로 설정해야 함.
	 * DEFAULT_KEY_DIALER : 전화거는 화면으로 이동.
	 * DEFAULT_KEY_SEARCH_XXX : startSearch함수 호출됨.
	 * 별도의 TestApp에서 확인.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			Toast.makeText(this, "Home Key Pressed", Toast.LENGTH_SHORT).show();
		}
		Log.i(this.getClass().getSimpleName(),"onKeyDown");
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onKeyLongPress");
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onKeyUp");
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onKeyMultiple");
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 * BackKey를 눌렀을 때 호출됨.
	 * Activity의 onBackPressed()에서는 finish()를 호출해 주고 있기 때문에
	 * BackKey가 눌렸을 때, Activity를 종료하지 않기 위해서는 super.onBackPressed()를 호출하면 안됨.
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
		super.onBackPressed();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onTrackballEvent");
		return super.onTrackballEvent(event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onUserInteraction()
	 * 사용자 입력 (key, touch, trackball등)이 발생하는 경우 호출됨.
	 */
	@Override
	public void onUserInteraction() {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onUserInteraction");
		super.onUserInteraction();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowAttributesChanged(android.view.WindowManager.LayoutParams)
	 * getWindow().setAttribute(WindowManager.LayoutParams)를 통해 Window의 Attribute를 변경하면 호출됨.
	 * setLayout, setGravity, setType, setFormat, setWindowAnimation, setSoftInputMode, addFlags, clearFlags, setFlags
	 * 호출시에도 호출됨. params은 변경된 window의 attribute임.
	 */
	@Override
	public void onWindowAttributesChanged(LayoutParams params) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onWindowAttributesChanged", Toast.LENGTH_SHORT).show();
		super.onWindowAttributesChanged(params);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContentChanged()
	 * setContentView나 addContentView를 호출하여 Layout이 변경되면 호출됨.
	 */
	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContentChanged", Toast.LENGTH_SHORT).show();
		super.onContentChanged();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 * Window가 focus를 받거나 잃어버리면 호출됨.
	 * Notification Bar를 내리거나 올리면 이때 해당 함수가 호출됨. 
	 * AnimationDrawable등의 animation을 시작하려면 Window가 focus를 획득한 다음부터 가능함.
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onWindowFocusChanged", Toast.LENGTH_SHORT).show();
		super.onWindowFocusChanged(hasFocus);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onAttachedToWindow()
	 * Main Window가 WindowManager에 추가되면 호출됨.
	 * 여기서 Window의 Type의 설정해 줄 수 있음.
	 * TYPE_KEYGUARD나 TYPE_KEYGUARD_DIALOG로 설정하면 KEYGUARD_WINDOW(화면보호기에서 사용되는 Window)로 설정되어 
	 * Home Key를 받을 수 있음. 일반적으로 KEYGUARD로 설정하면 HOME KEY에 의해 HOME 화면으로 이동되지 않는 것이 정상이나,
	 * 삼성 갤럭시S2, LG 옵티머스의 경우 Home으로 이동함. (에뮬레이터에서 테스트시 Home으로 이동되지 않음)
	 */
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onAttachedToWindow", Toast.LENGTH_SHORT).show();
		getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onDetachedFromWindow", Toast.LENGTH_SHORT).show();
		super.onDetachedFromWindow();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreatePanelView(int)
	 * MenuKey를 눌렀을 때 Default Menu화면이 아닌 나만의 Menu화면을 만들고 싶으면, 이 함수에서 Menu에 해당하는 View를 만들어서 넘겨주면 됨.
	 * MenuKey를 누르거나 getWindow().openPanel()을 호출하는 경우 호출됨.
	 * getWindow().openPanel로 호출하는 경우, featureId는 getWindow().requestFeature()로 window에 feature가 설정되어 있어야 동작함.
	 * onCreate의 openPanel의 주석을 풀고 onCreatePanelView의 주석을 풀면 동작을 확인 할 수 있음.
	 * onCreatePanelView에서 View를 return하는 경우 onCreatePanelMenu와 onCreateOptionMenu는 호출되지 않음.
	 */
	@Override
	public View onCreatePanelView(int featureId) {
		// TODO Auto-generated method stub
//		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
//			return LayoutInflater.from(this).inflate(R.layout.option_menu, null);
//		} else if (featureId == MY_NEW_WINDOW_FEATURE) {
//			return LayoutInflater.from(this).inflate(R.layout.context_menu, null);
//		} 
		Toast.makeText(this, "onCreatePanelView", Toast.LENGTH_SHORT).show();
		return super.onCreatePanelView(featureId);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreatePanelMenu(int, android.view.Menu)
	 * onCreatePanelView에서 View를 생성하지 않은 경우 호출됨.
	 * featureId를 이용하여 서로 다른 Menu를 생성할 수 있음.
	 * Activity의 onCreatePanelMenu에서는 featureId가 FEATURE_OPTIONS_PANEL인 경우  onCreateOptionsMenu를 호출해 줌.
	 * return 값이 true면 화면에 보여주고 false이면 화면에 보여주지 않음.
	 */
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		// TODO Auto-generated method stub

		Toast.makeText(this, "onCreatePanelMenu", Toast.LENGTH_SHORT).show();
		return super.onCreatePanelMenu(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPreparePanel(int, android.view.View, android.view.Menu)
	 * Panel은 한번만 생성되고 다음번에 메뉴 Key 또는 OpenPanel을 이용하여 호출시에는 onPreparePanel이 호출됨.
	 * onPreparePanel에서 view 또는 menu에 대한 속성을 변경해 줄 수 있음.
	 * Activity의 onPreparePanel은 featureId가 FEATURE_OPTIONS_PANEL이고 menu가 있는 경우(onCreatePanel에서 null을 넘긴 경우)에 onPrepareOptionMenu를 호출해 주고, 
	 * onPrepareOptionMenu가 true이고 menu의 visibleItem 있는 경우 true를 넘겨줌. 
	 * return 값이 true이면 화면에 보여주고 false이면 화면에 보여주지 않음.
	 */
	@Override
	public boolean onPreparePanel(int featureId, View view, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPreparePanel", Toast.LENGTH_SHORT).show();
		return super.onPreparePanel(featureId, view, menu);
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuOpened(int, android.view.Menu)
	 * menu가 화면에 보여졌을 때 호출됨. 
	 * Activity의 onMenuOpended에서는 parent가 있어도 parent의 onMenuOpened는 호출하지 않음.
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onMenuOpend", Toast.LENGTH_SHORT).show();
		return super.onMenuOpened(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 * Menu에서 MenuItem이 선택되면 호출됨.
	 * Activity의 onMenuItemSelected에서는 featureId가 FEATURE_OPTIONS_PANEL인 경우 onOptionItemSelected를 
	 * FEATURE_CONTEXT_MENU인 경우 onContextItemSelected를 호출함.
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onMenuItemSelected", Toast.LENGTH_SHORT).show();
		return super.onMenuItemSelected(featureId, item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPanelClosed(int, android.view.Menu)
	 * Panel이 화면에서 사라질 때 호출됨.
	 * Activity의 onPanelClosed에서는 featureId가 FEATURE_OPTIONS_PANEL인 경우 onOptionsMenuClosed를
	 * FEATURE_CONTEXT_MENU인 경우에는 onContextMenuClosed를 호출함.
	 */
	@Override
	public void onPanelClosed(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPanelClosed", Toast.LENGTH_SHORT).show();
		super.onPanelClosed(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Activity의 onCreateOptionMenu에서는 parent가 있는 경우 parent의 onCreateOptionsMenu를 호출해 주기 때문에,
	 * ActivtyGroup을 사용하는 경우 MenuID 지정이 중복되지 않도록 해 주어야 함.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.visible, menu);
		Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
		return super.onCreateOptionsMenu(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 * Activity의 onPrepareOptionMenu에서는 parent가 있는 경우 parent의 onPrepareOptionsMenu를 호출해 주기 때문에,
	 * ActivityGroup을 사용하는 경우 주의 필요함. 
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		switch (menu_show_level) {
			case 0 :
				{
					MenuItem item = menu.getItem(R.id.hidden_item);
					item.setVisible(true);
				}
				break;
			case 1 :
				{
					menu.setGroupVisible(R.id.hidden_group, true);
				}
				break;
			case 2 :
				{
					MenuItem item = menu.getItem(R.id.hidden_item);
					item.setVisible(false);
					menu.setGroupVisible(R.id.hidden_group, false);
				}
				break;
			default :
				break;
		}
		menu_show_level = (menu_show_level + 1) %3;
		Toast.makeText(this, "onPrepareOptionsMenu", Toast.LENGTH_SHORT).show();
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onOptionsItemSelected", Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onOptionsMenuClosed", Toast.LENGTH_SHORT).show();
		super.onOptionsMenuClosed(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 * view의 showContextMenu가 호출되면, parent의 showContextMenuForChild(view)가 호출되어 최종적으로 Window에 있는 decorView의 showContextMenuForChild가 호출됨.
	 * decorView의 showContextMenuForChild에서 ContextMenu를 생성하여, view의 createContextMenu를 호출해 주고, view는 자신의 onCreateContextMenu를 호출 후,
	 * Activity의 onCreateContextMenu를 호출해 주고 parent의 createContextMenu를 호출해 주어 parentContextMenu도 수집함.
	 * Menu가 생성된 다음 MenuDialogHelper를 이용하여 AlertDialog로 Menu를 보여줌.
	 * ContextMenu는 OptionsMenu처럼 Costum이 안됨.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.textView1) {
			getMenuInflater().inflate(R.menu.order, menu);
		} else if (v.getId() == R.id.layout) {
			getMenuInflater().inflate(R.menu.visible, menu);
		}
		Toast.makeText(this, "onCreateContextMenu", Toast.LENGTH_SHORT).show();
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 * Activity의 onContextItemSelected에서는 parent가 있는 경우 parent의 onContextItemSelected를 호출해 주고 있음.
	 * context menu 생성시에는 parent의 onCreateContextMenu를 호출하지 않고 있다가 onContextItemSelected에서는
	 * parent의 함수를 호출해 주는 것이 버그인지, 아니면 parent에서 contextmenu가 선택되었을 때 처리해 줄 수 있도록 하기 위한 것인지
	 * 알 수 없음. 
	 * ActivityGroup을 이용할 경우 ActivityGroup에 onContextItemSelected를 작성하고 Menu의 생성은 ChildActivity에서 하는 것이 가능함.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContextItemSelected", Toast.LENGTH_SHORT).show();
		return super.onContextItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextMenuClosed(android.view.Menu)
	 * Activity의 onContextMenuClosed에서는 parent의 onContextMenuClosed를 호출해 줌.
	 */
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContextMenuClosed", Toast.LENGTH_SHORT).show();
		super.onContextMenuClosed(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSearchRequested()
	 * Search key가 있는 단말의 경우 searchkey가 눌리면 호출됨.
	 * SearchRequest에서는 startSearch함수를 이용하여 Search화면을 띄워줌.
	 * startSearch방법에 대해서는 SampleSearchLocal 참고.
	 */
	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onSearchRequested", Toast.LENGTH_SHORT).show();
		startSearch("가",false,null,false);
		return super.onSearchRequested();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onApplyThemeResource(android.content.res.Resources.Theme, int, boolean)
	 * setTheme로 theme를 적용할 때 호출됨.
	 */
	@Override
	protected void onApplyThemeResource(Theme theme, int resid, boolean first) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onApplyThemeResource", Toast.LENGTH_SHORT).show();
		super.onApplyThemeResource(theme, resid, first);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onTitleChanged(java.lang.CharSequence, int)
	 * setTitle, setTitleColor를 이용하여 Title을 변경할 때 호출됨.
	 */
	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onTitleChanged", Toast.LENGTH_SHORT).show();
		super.onTitleChanged(title, color);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onChildTitleChanged(android.app.Activity, java.lang.CharSequence)
	 * ActivityGroup에서 ChildActivity의 Title이 변경될 때 호출됨.
	 */
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onChildTitleChanged", Toast.LENGTH_SHORT).show();
		super.onChildTitleChanged(childActivity, title);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateView(java.lang.String, android.content.Context, android.util.AttributeSet)
	 * getLayoutInflater() 로 가져오 LayoutInflater에 setFactory로 activity를 등록할 경우, inflate할 때 호출됨.
	 */
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onCreateView : ViewName - " + name);
		return super.onCreateView(name, context, attrs);
	}

	
}
