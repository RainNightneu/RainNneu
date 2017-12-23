package com.study.daynode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;
import com.study.daynode.calendar.CalendarFragment;
import com.study.daynode.diary.DiaryFragment;
import com.study.daynode.note.NoteFragment;

public class MainActivity extends AppCompatActivity {

  private MainNavigateTabBar mNavigateTabBar;
  private static final String TAG_PAGE_HOME = "日记";
  private static final String TAG_PAGE_CITY = "日历";
  private static final String TAG_PAGE_MESSAGE = "记录";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);
    mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
    mNavigateTabBar.addTab(NoteFragment.class,
        new MainNavigateTabBar.TabParam(R.drawable.comui_tab_home,
            R.drawable.comui_tab_home_selected, TAG_PAGE_HOME));
    /*mNavigateTabBar.addTab(DiaryFragment.class,
        new MainNavigateTabBar.TabParam(R.drawable.comui_tab_city,
            R.drawable.comui_tab_city_selected, TAG_PAGE_CITY));*/
    mNavigateTabBar.addTab(CalendarFragment.class,
        new MainNavigateTabBar.TabParam(R.drawable.comui_tab_message,
            R.drawable.comui_tab_message_selected, TAG_PAGE_MESSAGE));
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mNavigateTabBar.onSaveInstanceState(outState);
  }
}
