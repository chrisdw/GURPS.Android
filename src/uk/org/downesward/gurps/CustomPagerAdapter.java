package uk.org.downesward.gurps;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CustomPagerAdapter extends PagerAdapter {

	private Context mContext;
	private Vector<View> pages;

	List<String> pageTitles = new ArrayList<String>();

	public CustomPagerAdapter(Context context, Vector<View> pages) {
		this.mContext = context;
		this.pages = pages;

		pageTitles.add("General");
		pageTitles.add("Attributes");
		pageTitles.add("Advantages");
		pageTitles.add("Disadvantages");
		pageTitles.add("Perks");
		pageTitles.add("Quirks");
		pageTitles.add("Skills");
		pageTitles.add("Ranged Weapons");
		pageTitles.add("Mellee Weapons");
		pageTitles.add("Equipment");
		pageTitles.add("Spells");
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View page = pages.get(position);
		container.addView(page);
		return page;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return pageTitles.get(position);
	}
}