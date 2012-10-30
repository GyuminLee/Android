package org.tacademy.basic.sampletabfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class TabManager implements OnTabChangeListener {

    private final FragmentActivity mActivity;
    private final TabHost mTabHost;
    private final int mContainerId;
    private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
    TabInfo mLastTab;
    private TabInfoState savedTabInfo = null;

    public void saveTabInfo(Bundle b) {
    	if (b != null) {
    		TabInfoState state = new TabInfoState(mTabs);
    		b.putParcelable("tabinfo", state);
    	}
    }
    
    public void restoreTabInfo(Bundle b) {
    	if (b != null) {
    		savedTabInfo = b.getParcelable("tabinfo");
    	}
    }
    
    static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;
        private Fragment firstFragment;
        private boolean bShowFirstFragmentWhenTabSelect = true;
        private boolean bBackStackEnable = false;
        private ArrayList<Fragment> mFragmentStack = new ArrayList<Fragment>();
        

        TabInfo(String _tag, Class<?> _class, Bundle _args, boolean bShowFirst) {
            tag = _tag;
            clss = _class;
            args = _args;
            bShowFirstFragmentWhenTabSelect = bShowFirst;
        }
    }

    static final class TabInfoInternal {
    	public String key;
    	public String tag;
    	public String clazzName;
    	public Bundle args;
    	public String fragmentId;
    	public String firstFragmentId;
    	public int bShow;
    	public String[] mStackIds;
    }

    static final class TabInfoState implements Parcelable {

    	HashMap<String,TabInfo> tabs;
    	ArrayList<TabInfoInternal> internalTabs;
    	Bundle mFragments = new Bundle();
    	
    	public TabInfoState(HashMap<String,TabInfo> tabs) {
    		this.tabs = tabs;
    		internalTabs = new ArrayList<TabInfoInternal>();
			Set keySet = tabs.keySet();
			Object[] keys = keySet.toArray();
			for (int i = 0; i < keys.length; i++) {
				String key = (String)keys[i];
				TabInfo info = tabs.get(key);
				internalTabs.add(getInternalTabs(info));
			}
    	}
  
		private TabInfoInternal getInternalTabs(TabInfo info) {
			// TODO Auto-generated method stub
			TabInfoInternal internal = new TabInfoInternal();
			FragmentManager fm;
			internal.key = info.tag;
			internal.tag = info.tag;
			internal.args = info.args;
			internal.clazzName = info.clss.getName();
			if (info.fragment != null) {
				internal.fragmentId = info.tag + "_fragmentId";
				fm = info.fragment.getFragmentManager();
				fm.putFragment(mFragments, internal.fragmentId, info.fragment);
			} else {
				internal.fragmentId = null;
			}
			if (info.firstFragment != null) {
				internal.firstFragmentId = info.tag + "_firstFragmentId";
				fm = info.firstFragment.getFragmentManager();
				fm.putFragment(mFragments, internal.firstFragmentId, info.firstFragment);
			} else {
				internal.firstFragmentId = null;
			}
			internal.bShow = info.bShowFirstFragmentWhenTabSelect?1:0;
			
			internal.mStackIds = new String[info.mFragmentStack.size()];
			for (int i = 0; i < internal.mStackIds.length; i++) {
				fm = info.mFragmentStack.get(i).getFragmentManager();
				internal.mStackIds[i] = info.tag + "_stack_" + i;
				fm.putFragment(mFragments, internal.mStackIds[i], info.mFragmentStack.get(i));
			}
			
			return internal;
		}

		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void writeToParcel(Parcel out, int flags) {
			// TODO Auto-generated method stub
			out.writeBundle(mFragments);
			out.writeInt(internalTabs.size());
			for (int i = 0; i < internalTabs.size(); i++) {
				TabInfoInternal info = internalTabs.get(i);
				out.writeString(info.key);
				writeInfo(out,flags, info);
			}
		}
		
		private void writeInfo(Parcel out,int flags, TabInfoInternal info ) {
			out.writeString(info.tag);
			out.writeString(info.clazzName);
			if (info.args != null) {
				out.writeInt(1);
				out.writeBundle(info.args);
			} else {
				out.writeInt(0);
			}
			out.writeString(info.fragmentId);
			out.writeString(info.firstFragmentId);
			out.writeInt(info.bShow);
			out.writeInt(info.mStackIds.length);
			if (info.mStackIds.length > 0) {
				out.writeStringArray(info.mStackIds);
			}
		}
		
		public static Parcelable.Creator<TabInfoState> CREATOR = new Parcelable.Creator<TabManager.TabInfoState>() {

			public TabInfoState createFromParcel(Parcel in) {
				// TODO Auto-generated method stub
				return new TabInfoState(in);
			}

			public TabInfoState[] newArray(int size) {
				// TODO Auto-generated method stub
				return new TabInfoState[size];
			}
		};
		public TabInfoState(Parcel in) {
			mFragments = in.readBundle();
			int count = in.readInt();
			internalTabs = new ArrayList<TabInfoInternal>();
			
			for (int i = 0; i < count; i++) {
				internalTabs.add(readTabInfoInternal(in));
			}
		}

		private TabInfoInternal readTabInfoInternal(Parcel in) {
			TabInfoInternal tabinfo = new TabInfoInternal();
			tabinfo.key = in.readString();
			tabinfo.tag = in.readString();
			tabinfo.clazzName = in.readString();
			int flag = in.readInt();
			if (flag == 1) {
				tabinfo.args = in.readBundle();
			}
			tabinfo.fragmentId = in.readString();
			tabinfo.firstFragmentId = in.readString();
			tabinfo.bShow = in.readInt();
			int arraySize = in.readInt();
			tabinfo.mStackIds = new String[arraySize];
			if (arraySize > 0) {
				in.readStringArray(tabinfo.mStackIds);
			}
			return tabinfo;
		}
		
		public TabInfo getTabInfo(FragmentManager fm,String tag) {
//			if (tabs != null) return tabs.get(tag);
			if (internalTabs == null || mFragments == null) return null;
			try {
				for (int i = 0; i < internalTabs.size(); i++) {
					TabInfoInternal info = internalTabs.get(i);
					if (info.tag.equals(tag)) {
						Class clazz = Class.forName(info.clazzName);
						boolean isShow = (info.bShow==1)?true:false;
						TabInfo tabinfo = new TabInfo(tag,clazz,info.args,isShow);
						if (info.firstFragmentId != null) {
							tabinfo.firstFragment = fm.getFragment(mFragments, info.firstFragmentId);
						}
						if (info.fragmentId != null) {
							tabinfo.fragment = fm.getFragment(mFragments, info.fragmentId);
						}
						for (int j = 0; j < info.mStackIds.length; j++) {
							Fragment f = fm.getFragment(mFragments, info.mStackIds[j]);
							tabinfo.mFragmentStack.add(f);
						}
						return tabinfo;
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
    	
    }
    
    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
        mActivity = activity;
        mTabHost = tabHost;
        mContainerId = containerId;
        mTabHost.setOnTabChangedListener(this);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
    	addTab(tabSpec,clss,args,true);
    }
    
    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args, boolean isShowFirstFragmentWhenTabSelected) {
        tabSpec.setContent(new DummyTabFactory(mActivity));
        String tag = tabSpec.getTag();

        TabInfo info = null;
        if (savedTabInfo != null) {
        	info = savedTabInfo.getTabInfo(mActivity.getSupportFragmentManager(), tag);
        }
        if (info == null) {
        	info = new TabInfo(tag, clss, args,isShowFirstFragmentWhenTabSelected);
        }

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
//        info.firstFragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
//        info.fragment = info.firstFragment;
        if (info.fragment != null && !info.fragment.isDetached()) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.detach(info.fragment);
            ft.commit();
        }

        mTabs.put(tag, info);
        mTabHost.addTab(tabSpec);
    }

    public void addFragmentAtCurrentTab(Fragment f) {
    	if (mLastTab != null) {
    		if (mLastTab.fragment != null) {
    			FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
    			
    			clearTabFragment(mLastTab,ft);
    			
		    	mLastTab.fragment = f;
		    	ft.add(mContainerId, f);
		    	ft.commit();
    		}
    	}
    }
    
    public void addFragmentStackAtCurrentTab(Fragment f) {
    	if (mLastTab != null) {
    		if (mLastTab.fragment != null) {
    			FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
    			
    			int size = mLastTab.mFragmentStack.size();
    			if (size > 0) {
    				Fragment topFragment = mLastTab.mFragmentStack.get(size - 1);
    				ft.detach(topFragment);
    			} else {
    				ft.detach(mLastTab.fragment);
    			}
    			
    			mLastTab.mFragmentStack.add(f);
    			ft.add(mContainerId, f);
    			ft.commit();
    		}
    	}
    }
    
    private boolean clearTabStackFragment(TabInfo tabInfo, FragmentTransaction ft) {
		if (tabInfo.mFragmentStack.size() > 0) {
			for (int i = tabInfo.mFragmentStack.size() - 1; i >=0 ; i--) {
				Fragment sf = tabInfo.mFragmentStack.get(i);
				ft.remove(sf);
			}
			tabInfo.mFragmentStack.clear();
			return true;
		}
		return false;
    }
    
    private void clearTabFragment(TabInfo tabInfo, FragmentTransaction ft) {
		clearTabStackFragment(tabInfo,ft);
    	if (tabInfo.fragment == tabInfo.firstFragment) {
    		ft.detach(tabInfo.fragment);
    	} else {
    		ft.remove(tabInfo.fragment);
    	}    	
    }
    
    public void moveFirstFragmentAtCurrentTab() {
    	if (mLastTab != null) {
    		if (mLastTab.fragment != null) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				boolean isStackExist = clearTabStackFragment(mLastTab, ft);
    			if (mLastTab.fragment != mLastTab.firstFragment) {
    				ft.remove(mLastTab.fragment);
    				mLastTab.fragment = mLastTab.firstFragment;
    				ft.attach(mLastTab.fragment);
    			} else if (isStackExist) {
    				ft.attach(mLastTab.fragment);
    			}    			
				ft.commit();
    		}
    	}
    }
    
    public boolean backStackFragmentAtCurrentTab() {
    	return backStackFragmentAtCurrentTab(null,null);
    }
    public boolean backStackFragmentAtCurrentTab(Fragment f,Bundle b) {
    	if (mLastTab != null) {
    		if (mLastTab.fragment != null) {
    			int size = mLastTab.mFragmentStack.size();
    			if ( size > 0) {
        			FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
    				Fragment oFragment = mLastTab.mFragmentStack.remove(size - 1);
    				Fragment nFragment = (size > 1) ? mLastTab.mFragmentStack.get(size - 2) : mLastTab.fragment;
    				if (f != null && f != oFragment) return false;
    				if (b != null) {
    					TabFragment target = (TabFragment)nFragment;
    					target.setResultBundle(b);
    				}
    				ft.remove(oFragment);
    				ft.attach(nFragment);
    				ft.commit();
    				return true;
    			} 
    		}
    	}
    	return false;
    }
    
    private void setTabFragment(TabInfo tabInfo, FragmentTransaction ft) {
    	Fragment f;
    	if (tabInfo.mFragmentStack.size() > 0) {
    		f = tabInfo.mFragmentStack.get(tabInfo.mFragmentStack.size() - 1);
    	} else {
    		f = tabInfo.fragment;
    	}
    	
    	ft.attach(f);
    }
    
    public boolean backPressed() {
    	boolean isBackProcessed = false;
    	if (mLastTab != null) {
    		TabFragment f;
    		if (mLastTab.mFragmentStack.size() > 0) {
    			f = (TabFragment)mLastTab.mFragmentStack.get(mLastTab.mFragmentStack.size() - 1);
    		} else {
    			f = (TabFragment)mLastTab.fragment;
    		}
    		isBackProcessed = f.onBackPressed();
    	}
    	return isBackProcessed;
    }
    
    @Override
    public void onTabChanged(String tabId) {
        TabInfo newTab = mTabs.get(tabId);
        if (mLastTab != newTab) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                	if (mLastTab.bShowFirstFragmentWhenTabSelect) {
                		clearTabStackFragment(mLastTab,ft);
                    	if (mLastTab.fragment == mLastTab.firstFragment) {
                    		ft.detach(mLastTab.fragment);
                    	} else {
                    		ft.remove(mLastTab.fragment);
                    		mLastTab.fragment = mLastTab.firstFragment;
                    	}
                	} else {
                		int size = mLastTab.mFragmentStack.size();
                		if (size > 0) {
                			Fragment f = mLastTab.mFragmentStack.get(size - 1);
                			ft.detach(f);
                		} else {
                			ft.detach(mLastTab.fragment);
                		}
                	}
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.firstFragment = Fragment.instantiate(mActivity,
                            newTab.clss.getName(), newTab.args);
                    newTab.fragment = newTab.firstFragment;
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                	setTabFragment(newTab,ft);
                    //ft.attach(newTab.firstFragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            mActivity.getSupportFragmentManager().executePendingTransactions();
        }
    }
}
