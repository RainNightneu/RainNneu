package com.study.daynode.calendar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import com.study.daynode.R;
import com.study.daynode.base.BaseFragment;
import com.study.daynode.base.IPresenter;
import com.study.daynode.common.ThemeManager;
import com.study.daynode.databinding.FragmentCalenderBinding;
import com.study.daynode.diary.DiaryActivity;
import java.util.*;

/**
 */
public class CalendarFragment extends BaseFragment {
  private FragmentCalenderBinding fragmentCalenderBinding;
  private DatePicker mDatePicker;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected IPresenter getPresenter() {
    return null;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    fragmentCalenderBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false);
    fragmentCalenderBinding.setSetThemeBg(ThemeManager.getInstance().getBgDrawable(getActivity()));
    mDatePicker = (DatePicker) fragmentCalenderBinding.getRoot().findViewById(R.id.datePicker);
    setDatePicker();
    return fragmentCalenderBinding.getRoot();
  }

  private void setDatePicker() {
    //Date currentTime = new Date();
    Calendar cal = Calendar.getInstance();
    mDatePicker.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    mDatePicker.setMode(DPMode.SINGLE);
    mDatePicker.setHolidayDisplay(false);
    mDatePicker.setDividerPadding(15);
    mDatePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
      @Override public void onDatePicked(String date) {
        //Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), DiaryActivity.class);
        //intent.putExtra("noteId", item.getId());
        intent.putExtra("noteData",date);
        getActivity().startActivity(intent);
      }
    });
  }

  public static CalendarFragment newInstance() {
    CalendarFragment fragment = new CalendarFragment();
    return fragment;
  }
}
