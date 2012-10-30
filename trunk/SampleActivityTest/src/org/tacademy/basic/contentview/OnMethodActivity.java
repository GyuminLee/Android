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
	 * Activity�� ���ʷ� �����Ǿ��� �� ȣ���.
	 * savedInstanceState�� Activity�� BG���·� �������� Android System�� ���� �״� ���
	 * onSaveInstanceState���� Bundle�� ������ ���� �Ѿ��.
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
			 * Panel Test�� ���� Button��.
			 * openPanel�� �̿��Ͽ� Window�� feature�� �����ϸ�, onCreatePanelView, onCreatePanelMenu, onPreparePanel ���� ȣ���.
			 * Window feature�� ���ο� feature�� �߰��ϴ� �͵� ������ ���̳�, Android�� reserved�Ǿ� �ִ� feature�� �ֱ� ������ ���� ���� �ʿ�.
			 * ���ο� feature�� ����ϴ� ��� MAX 31������ ��밡��(���������� feature�� 0x1 << feature �Ͽ� ����ϱ� ����)
			 * ���ο� feature�� ����ϴ� ���� ��Ȳ�� ���� �ٸ� Menuȭ���� �����ְ��� �� �� ��� ����.
			 * �ش� feature�� �̿��ϱ� ���ؼ��� Window�� requestFeature�� ���� ��ϵǾ� �־�� ��.
			 * FEATURE_OPTIONS_PANEL�� FEATURE_CONTEXT_MENU�� default�� ��ϵǾ� ����.
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// openOptionsMenu()�� ������ ������.
				getWindow().openPanel(Window.FEATURE_OPTIONS_PANEL, null);
//				getWindow().openPanel(MY_NEW_WINDOW_FEATURE, null);
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * openContextMenu�� �̿��Ͽ� context menu�� ������ �� ����.
			 * ���������δ� view.showContextMenu�� ȣ����. showContextMenu�� ContextMenu�� ������������ return��.
			 * ContextMenu�� ���, View�� LongClick�� ���� ȣ���.(showContextMenu ȣ���)
			 * OnLongClickListener�� View�� �����Ǿ� �ִ� ���, 
			 * onLongClick���� true�� �Ѱ��ָ� ContextMenu�� �� �߰�, false�� �Ѱ��ָ� ContextMenu�� ��.
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
	 * onCreate�� savedInstanceState�� null�� �ƴ� ��� ȣ���.
	 * savedInstanceState�� �ִ� ���� ���� Activity�� ������ ����.
	 * View�� ��� setSaveEnabled�� �̿��Ͽ� ���¸� ������ �� ����.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onRestoreInstnaceState", Toast.LENGTH_SHORT).show();
		super.onRestoreInstanceState(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 * Activity���� initialize�۾��� �Ϸ��� �� ȣ���.
	 * onStart, onRestoreInstanceState�� ����� �� ȣ��.
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPostCreate", Toast.LENGTH_SHORT).show();
		super.onPostCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 * Activity�� ȭ���� ���� �� ���� ȣ��.
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 * Activity�� BG�� �ִٰ� �ٽ� FG�� �ö���� ������ �� ȣ��.
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onReStart", Toast.LENGTH_SHORT).show();
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 * Activity�� F/G Event�� ���� �� �ִ� ������ ȣ��.
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostResume()
	 * ������ Resume�� �� �Ŀ� ȣ��.
	 */
	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPostResume", Toast.LENGTH_SHORT).show();
		super.onPostResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 * Activity�� FG���� BG�� ������ �� ȣ��.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 * Activity�� FG Event�� ���� �� ���� �Ǵ� ������ ȣ��.
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onUserLeaveHint()
	 * ������� ���ÿ� ���� FG���� User Interaction�� ���� BG�� ������ �� ȣ���.
	 * ���� ��� Home Key�� ���� BG�� �� ���� ȣ��ǰ�, Call�� �ͼ� BG�� �� ��쿡�� ȣ����� ����.
	 */
	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onUserLeaveHint", Toast.LENGTH_SHORT).show();
		super.onUserLeaveHint();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateThumbnail(android.graphics.Bitmap, android.graphics.Canvas)
	 * ���� ��� ȣ��Ǵ��� �� �� Ȯ���� ���� �ڴ�.
	 * Running Task�� Thumbnail�� ��ü�� �� ����Ѵٰ� �Ǿ� �ִµ�, ���ͳ� ���������� ���� �������� �ʴ´ٰ� ����.
	 * Platform source Ȯ�� ���, com.android.internal.R.dimen.thumbnail_height�� width�� ��� 0�̾ onCreateThumbnail�� ȣ��ȵ�.
	 */
	@Override
	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onCreateThumbnail", Toast.LENGTH_SHORT).show();
		return super.onCreateThumbnail(outBitmap, canvas);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDescription()
	 * stop�� �ش� �Լ��� ȣ��Ǿ�Description�� ����Ǿ�� �ϳ� ������� ����.
	 * Platform Source�� �� �� �м��� ���� �˰� ����.
	 */
	@Override
	public CharSequence onCreateDescription() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onCreateDescription", Toast.LENGTH_SHORT).show();
		return "�׽�Ʈ";
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 * ȭ�鿡�� ����� �� ȣ���.
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		super.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 * ActivityStack���� ���ŵ� �� ȣ���. (�Ϲ������� Backkey�� �����ų� finish()�� ȣ���ϴ� ��� ȣ���)
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 * �Ϲ������� configuration�� ����Ǹ�, Activity�� �׿��ٰ� �ٽ� ���µ�, 
	 * AndroidManifest.xml���� Activity�� configChanges�� �����ϸ� Activity�� �׿��� ����� �ʰ�
	 * onConfigurationChanged�� ȣ���.
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
	 * Configuration�� ����Ǹ�, Activity�� �׿��� �ٽ� ���µ�, 
	 * �̶�, onStop������ ȣ��Ǿ� �����ϰ� ���� instance(�Ϲ������� activity)�� �Ѱ��־��ٰ�
	 * Activity�� �ٽ� ���۵Ǹ�, getLastNonConfigurationInstance()��  �ش� instance�� �����ͼ� ó��.
	 * Fragment�� �����鼭 deprecated ��.
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onRetainNonConfigurationInstance", Toast.LENGTH_SHORT).show();
		return super.onRetainNonConfigurationInstance();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onLowMemory()
	 * System�� �޸𸮰� ������ �� ȣ���.
	 * cache���� nullify��Ű�� �۾� ���� �� �־�� ��.
	 * �ش� �Լ� ȣ�� �� gc�� ȣ���.
	 */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onLowMemory", Toast.LENGTH_SHORT).show();
		super.onLowMemory();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * Key�� ������ �� ȣ��.
	 * Home Key�� �޾Ƽ� ó���ϱ⸦ ���ϴ� ���,
	 * onAttachedToWindow()���� getWindow().getType(WindowManager.LayoutParams.TYPE_KEYGUARD) 
	 * �Ǵ� getWindow().getType(WindowManager.LayoutParams.TYPE_KEYGAURD_DIALOG)�� ������ �ָ��.
	 * ����� HomeKey�� KeyEvent.KEYCODE_HOME ��.
	 * super�� onKeyDown������ keyMode�� ���� ���� �ٸ� ������ ȣ���.
	 * DEFAULT_KEY_DISABLE : �ƹ� ���۵� ���� �ʰ� return false;
	 * DEFAULT_KEY_SHORTCUT : menu�� shortcut�� ��ϵǾ� �ִ� ��� �ش� shortcut�� ȣ����. menu�� �� ���� ���� ���¿��� shortcut�� Ȯ���Ϸ��� �� ���� �����ؾ� ��.
	 * DEFAULT_KEY_DIALER : ��ȭ�Ŵ� ȭ������ �̵�.
	 * DEFAULT_KEY_SEARCH_XXX : startSearch�Լ� ȣ���.
	 * ������ TestApp���� Ȯ��.
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
	 * BackKey�� ������ �� ȣ���.
	 * Activity�� onBackPressed()������ finish()�� ȣ���� �ְ� �ֱ� ������
	 * BackKey�� ������ ��, Activity�� �������� �ʱ� ���ؼ��� super.onBackPressed()�� ȣ���ϸ� �ȵ�.
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
	 * ����� �Է� (key, touch, trackball��)�� �߻��ϴ� ��� ȣ���.
	 */
	@Override
	public void onUserInteraction() {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onUserInteraction");
		super.onUserInteraction();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowAttributesChanged(android.view.WindowManager.LayoutParams)
	 * getWindow().setAttribute(WindowManager.LayoutParams)�� ���� Window�� Attribute�� �����ϸ� ȣ���.
	 * setLayout, setGravity, setType, setFormat, setWindowAnimation, setSoftInputMode, addFlags, clearFlags, setFlags
	 * ȣ��ÿ��� ȣ���. params�� ����� window�� attribute��.
	 */
	@Override
	public void onWindowAttributesChanged(LayoutParams params) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onWindowAttributesChanged", Toast.LENGTH_SHORT).show();
		super.onWindowAttributesChanged(params);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContentChanged()
	 * setContentView�� addContentView�� ȣ���Ͽ� Layout�� ����Ǹ� ȣ���.
	 */
	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContentChanged", Toast.LENGTH_SHORT).show();
		super.onContentChanged();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 * Window�� focus�� �ްų� �Ҿ������ ȣ���.
	 * Notification Bar�� �����ų� �ø��� �̶� �ش� �Լ��� ȣ���. 
	 * AnimationDrawable���� animation�� �����Ϸ��� Window�� focus�� ȹ���� �������� ������.
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onWindowFocusChanged", Toast.LENGTH_SHORT).show();
		super.onWindowFocusChanged(hasFocus);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onAttachedToWindow()
	 * Main Window�� WindowManager�� �߰��Ǹ� ȣ���.
	 * ���⼭ Window�� Type�� ������ �� �� ����.
	 * TYPE_KEYGUARD�� TYPE_KEYGUARD_DIALOG�� �����ϸ� KEYGUARD_WINDOW(ȭ�麸ȣ�⿡�� ���Ǵ� Window)�� �����Ǿ� 
	 * Home Key�� ���� �� ����. �Ϲ������� KEYGUARD�� �����ϸ� HOME KEY�� ���� HOME ȭ������ �̵����� �ʴ� ���� �����̳�,
	 * �Ｚ ������S2, LG ��Ƽ�ӽ��� ��� Home���� �̵���. (���ķ����Ϳ��� �׽�Ʈ�� Home���� �̵����� ����)
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
	 * MenuKey�� ������ �� Default Menuȭ���� �ƴ� ������ Menuȭ���� ����� ������, �� �Լ����� Menu�� �ش��ϴ� View�� ���� �Ѱ��ָ� ��.
	 * MenuKey�� �����ų� getWindow().openPanel()�� ȣ���ϴ� ��� ȣ���.
	 * getWindow().openPanel�� ȣ���ϴ� ���, featureId�� getWindow().requestFeature()�� window�� feature�� �����Ǿ� �־�� ������.
	 * onCreate�� openPanel�� �ּ��� Ǯ�� onCreatePanelView�� �ּ��� Ǯ�� ������ Ȯ�� �� �� ����.
	 * onCreatePanelView���� View�� return�ϴ� ��� onCreatePanelMenu�� onCreateOptionMenu�� ȣ����� ����.
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
	 * onCreatePanelView���� View�� �������� ���� ��� ȣ���.
	 * featureId�� �̿��Ͽ� ���� �ٸ� Menu�� ������ �� ����.
	 * Activity�� onCreatePanelMenu������ featureId�� FEATURE_OPTIONS_PANEL�� ���  onCreateOptionsMenu�� ȣ���� ��.
	 * return ���� true�� ȭ�鿡 �����ְ� false�̸� ȭ�鿡 �������� ����.
	 */
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		// TODO Auto-generated method stub

		Toast.makeText(this, "onCreatePanelMenu", Toast.LENGTH_SHORT).show();
		return super.onCreatePanelMenu(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPreparePanel(int, android.view.View, android.view.Menu)
	 * Panel�� �ѹ��� �����ǰ� �������� �޴� Key �Ǵ� OpenPanel�� �̿��Ͽ� ȣ��ÿ��� onPreparePanel�� ȣ���.
	 * onPreparePanel���� view �Ǵ� menu�� ���� �Ӽ��� ������ �� �� ����.
	 * Activity�� onPreparePanel�� featureId�� FEATURE_OPTIONS_PANEL�̰� menu�� �ִ� ���(onCreatePanel���� null�� �ѱ� ���)�� onPrepareOptionMenu�� ȣ���� �ְ�, 
	 * onPrepareOptionMenu�� true�̰� menu�� visibleItem �ִ� ��� true�� �Ѱ���. 
	 * return ���� true�̸� ȭ�鿡 �����ְ� false�̸� ȭ�鿡 �������� ����.
	 */
	@Override
	public boolean onPreparePanel(int featureId, View view, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPreparePanel", Toast.LENGTH_SHORT).show();
		return super.onPreparePanel(featureId, view, menu);
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuOpened(int, android.view.Menu)
	 * menu�� ȭ�鿡 �������� �� ȣ���. 
	 * Activity�� onMenuOpended������ parent�� �־ parent�� onMenuOpened�� ȣ������ ����.
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onMenuOpend", Toast.LENGTH_SHORT).show();
		return super.onMenuOpened(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 * Menu���� MenuItem�� ���õǸ� ȣ���.
	 * Activity�� onMenuItemSelected������ featureId�� FEATURE_OPTIONS_PANEL�� ��� onOptionItemSelected�� 
	 * FEATURE_CONTEXT_MENU�� ��� onContextItemSelected�� ȣ����.
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onMenuItemSelected", Toast.LENGTH_SHORT).show();
		return super.onMenuItemSelected(featureId, item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPanelClosed(int, android.view.Menu)
	 * Panel�� ȭ�鿡�� ����� �� ȣ���.
	 * Activity�� onPanelClosed������ featureId�� FEATURE_OPTIONS_PANEL�� ��� onOptionsMenuClosed��
	 * FEATURE_CONTEXT_MENU�� ��쿡�� onContextMenuClosed�� ȣ����.
	 */
	@Override
	public void onPanelClosed(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPanelClosed", Toast.LENGTH_SHORT).show();
		super.onPanelClosed(featureId, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Activity�� onCreateOptionMenu������ parent�� �ִ� ��� parent�� onCreateOptionsMenu�� ȣ���� �ֱ� ������,
	 * ActivtyGroup�� ����ϴ� ��� MenuID ������ �ߺ����� �ʵ��� �� �־�� ��.
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
	 * Activity�� onPrepareOptionMenu������ parent�� �ִ� ��� parent�� onPrepareOptionsMenu�� ȣ���� �ֱ� ������,
	 * ActivityGroup�� ����ϴ� ��� ���� �ʿ���. 
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
	 * view�� showContextMenu�� ȣ��Ǹ�, parent�� showContextMenuForChild(view)�� ȣ��Ǿ� ���������� Window�� �ִ� decorView�� showContextMenuForChild�� ȣ���.
	 * decorView�� showContextMenuForChild���� ContextMenu�� �����Ͽ�, view�� createContextMenu�� ȣ���� �ְ�, view�� �ڽ��� onCreateContextMenu�� ȣ�� ��,
	 * Activity�� onCreateContextMenu�� ȣ���� �ְ� parent�� createContextMenu�� ȣ���� �־� parentContextMenu�� ������.
	 * Menu�� ������ ���� MenuDialogHelper�� �̿��Ͽ� AlertDialog�� Menu�� ������.
	 * ContextMenu�� OptionsMenuó�� Costum�� �ȵ�.
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
	 * Activity�� onContextItemSelected������ parent�� �ִ� ��� parent�� onContextItemSelected�� ȣ���� �ְ� ����.
	 * context menu �����ÿ��� parent�� onCreateContextMenu�� ȣ������ �ʰ� �ִٰ� onContextItemSelected������
	 * parent�� �Լ��� ȣ���� �ִ� ���� ��������, �ƴϸ� parent���� contextmenu�� ���õǾ��� �� ó���� �� �� �ֵ��� �ϱ� ���� ������
	 * �� �� ����. 
	 * ActivityGroup�� �̿��� ��� ActivityGroup�� onContextItemSelected�� �ۼ��ϰ� Menu�� ������ ChildActivity���� �ϴ� ���� ������.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContextItemSelected", Toast.LENGTH_SHORT).show();
		return super.onContextItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextMenuClosed(android.view.Menu)
	 * Activity�� onContextMenuClosed������ parent�� onContextMenuClosed�� ȣ���� ��.
	 */
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onContextMenuClosed", Toast.LENGTH_SHORT).show();
		super.onContextMenuClosed(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSearchRequested()
	 * Search key�� �ִ� �ܸ��� ��� searchkey�� ������ ȣ���.
	 * SearchRequest������ startSearch�Լ��� �̿��Ͽ� Searchȭ���� �����.
	 * startSearch����� ���ؼ��� SampleSearchLocal ����.
	 */
	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onSearchRequested", Toast.LENGTH_SHORT).show();
		startSearch("��",false,null,false);
		return super.onSearchRequested();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onApplyThemeResource(android.content.res.Resources.Theme, int, boolean)
	 * setTheme�� theme�� ������ �� ȣ���.
	 */
	@Override
	protected void onApplyThemeResource(Theme theme, int resid, boolean first) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onApplyThemeResource", Toast.LENGTH_SHORT).show();
		super.onApplyThemeResource(theme, resid, first);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onTitleChanged(java.lang.CharSequence, int)
	 * setTitle, setTitleColor�� �̿��Ͽ� Title�� ������ �� ȣ���.
	 */
	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onTitleChanged", Toast.LENGTH_SHORT).show();
		super.onTitleChanged(title, color);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onChildTitleChanged(android.app.Activity, java.lang.CharSequence)
	 * ActivityGroup���� ChildActivity�� Title�� ����� �� ȣ���.
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
	 * getLayoutInflater() �� ������ LayoutInflater�� setFactory�� activity�� ����� ���, inflate�� �� ȣ���.
	 */
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		Log.i(this.getClass().getSimpleName(),"onCreateView : ViewName - " + name);
		return super.onCreateView(name, context, attrs);
	}

	
}
