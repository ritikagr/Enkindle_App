package com.iitism.ritik.enkindle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ObbInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

    private ViewPager viewPager;
    private Button skip;
    private Button next;
    private MyViewPagerAdapter myViewPagerAdapter;
    private PrefManager prefManager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if(!prefManager.isFirstTimeLaunch())
        {
            launchHomeScreen();
            finish();
        }

        if(Build.VERSION.SDK_INT>=21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        skip = (Button) findViewById(R.id.btn_skip);
        next = (Button) findViewById(R.id.btn_next);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.intro_slide_1,
                R.layout.intro_slide_2,
                R.layout.intro_slide_3,
                R.layout.intro_slide_4
        };

        addBottomButtons(0);

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void addBottomButtons(int currentPage)
    {
        dots = new TextView[layouts.length];

        int[] activeColor = getResources().getIntArray(R.array.array_dot_active);
        int[] inactiveColor = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(inactiveColor[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length>0)
        dots[currentPage].setTextColor(activeColor[currentPage]);
    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen()
    {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void changeStatusBarColor()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void onSkip(View view)
    {
        launchHomeScreen();
    }

    public void onNext(View view)
    {
        int current = getItem(+1);
        if(current < layouts.length)
        {
            viewPager.setCurrentItem(current);
        }else
        {
            launchHomeScreen();
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomButtons(position);

            if(position==layouts.length-1)
            {
                next.setText(getString(R.string.start));
                skip.setVisibility(View.GONE);
            }else
            {
                next.setText(getString(R.string.next));
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter()
        {

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
