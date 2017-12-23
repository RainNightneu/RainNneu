package com.study.daynode.diary;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.study.daynode.R;
import com.study.daynode.base.BaseActivity;
import com.study.daynode.bean.DiaryTime;
import com.study.daynode.bean.Note;
import com.study.daynode.bean.WeatherItem;
import com.study.daynode.common.RichEditText.EditTextData;
import com.study.daynode.common.RichEditText.RichEditText;
import com.study.daynode.common.RichEditText.RichTextView;
import com.study.daynode.common.ThemeManager;
import com.study.daynode.common.VeDate;
import com.study.daynode.common.photo.PhotoPickerActivity;
import com.study.daynode.common.popup.WeatherCallBack;
import com.study.daynode.common.popup.WeatherPopup;
import com.study.daynode.databinding.ActivityDiaryBinding;
import com.study.daynode.mediautils.MediaUtils;
import com.werb.permissionschecker.PermissionChecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.functions.Action1;

import java.util.*;

import static com.study.daynode.common.popup.WeatherPopup.getMenu;

public class DiaryActivity extends BaseActivity<DiaryPresenter>
        implements DiaryContract.DiaryView, DatePickerDialog.OnDateSetListener, View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, WeatherCallBack {
    public static final int REQUEST_PHOTO = 0x0023;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    //private TextView diary_location;
    //private LinearLayout buttom_toolbar;
    private RichTextView diary_content;
    private ImageView save;
    private ImageView input;
    private ImageView photo;
    private ImageView recode;
    private ImageView del;
    private long noteid;
    private String noteData;
    private RichEditText note_rich;
    private FrameLayout note_back;
    private int weatherPisition;
    private List<WeatherItem> weatherList;
    private WeatherPopup weatherPopup;
    //private ImageView weather_icon;
    private boolean enabled;
    private StringBuilder update_location;
    //private String lastLocation;
    private ActivityDiaryBinding activityDiaryBinding;
    private LinearLayout diary_time_information;

    private ImageView left_btn, right_btn;
    private TextView title_tx;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionChecker permissionChecker;
    private int type;
    private MediaUtils mediaUtils;
    private boolean isCancel;
    private Chronometer chronometer;
    private RelativeLayout audioLayout;
    private String duration;

    private TextView info;
    private ImageView micIcon;
    private boolean isAddNode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDiaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary);
        activityDiaryBinding.setCalendarColor(ThemeManager.getInstance().getThemeColor(this));
        //findViewById(R.id.shadow_view).setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        noteid = bundle.getLong("noteId");
        noteData = bundle.getString("noteData");
        weatherList = getMenu(this);
        weatherPopup = new WeatherPopup(this, this);
        note_rich = (RichEditText) findViewById(R.id.note_rich);
        note_back = (FrameLayout) findViewById(R.id.note_back);
        note_back.setVisibility(View.GONE);
        note_back.setOnClickListener(this);
        left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setImageDrawable(getResources().getDrawable(R.drawable.back));
        left_btn.setVisibility(View.VISIBLE);
        left_btn.setOnClickListener(this);
        right_btn = (ImageView) findViewById(R.id.right_bnt);
        right_btn.setVisibility(View.GONE);
        title_tx = (TextView) findViewById(R.id.title_tx);
        title_tx.setText("日记详情");
        //diary_location = (TextView) findViewById(R.id.diary_location);
        diary_content = (RichTextView) findViewById(R.id.diary_content);
        //weather_icon = (ImageView) findViewById(R.id.weather_icon);
        //weather_icon.setOnClickListener(this);
        //weather_icon.setImageResource(weatherList.get(0).icon);
        save = (ImageView) findViewById(R.id.save);
        save.setOnClickListener(this);
        recode = (ImageView) findViewById(R.id.location);
        recode.setVisibility(View.VISIBLE);
        initAudio();
        //recode.setOnClickListener(audioClick);
        photo = (ImageView) findViewById(R.id.photo);
        photo.setVisibility(View.VISIBLE);
        photo.setOnClickListener(this);
        input = (ImageView) findViewById(R.id.input);
        input.setVisibility(View.VISIBLE);
        input.setOnClickListener(this);
        del = (ImageView) findViewById(R.id.del);
        del.setVisibility(View.VISIBLE);
        del.setOnClickListener(this);
        //buttom_toolbar = (LinearLayout) findViewById(R.id.buttom_toolbar);
        //buttom_toolbar.setBackgroundColor(ThemeManager.getInstance().getThemeColor(this));
        diary_time_information = (LinearLayout) findViewById(R.id.diary_time_information);
        diary_time_information.setOnClickListener(this);
        diary_content.setTitleColor(ThemeManager.getInstance().getThemeColor(this));
        viewEnabled(false);
        if (mPresenter.getNoteByData(noteData)) {
            //原有的数据
            isAddNode = false;
        } else {
            //新添加的数据
            title_tx.setText("写日记");
            input.performClick();
            isAddNode = true;
        }
    }

    private void initAudio() {
        permissionChecker = new PermissionChecker(this); // initialize，must need
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        mediaUtils.setTargetDir(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        mediaUtils.setTargetName(UUID.randomUUID() + MediaUtils.MEDIA_FORMAT);
        // btn
        info = (TextView) findViewById(R.id.tv_info);
        recode.setOnTouchListener(touchListener);
        chronometer = (Chronometer) findViewById(R.id.time_display);
        chronometer.setOnChronometerTickListener(tickListener);
        micIcon = (ImageView) findViewById(R.id.mic_icon);
        audioLayout = (RelativeLayout) findViewById(R.id.audio_layout);
    }

    private void setCurrentTime(boolean updateCurrentTime, Note note) {
        if (updateCurrentTime) {
            long time = note.getCreateTime();
            calendar.setTimeInMillis(time);
        }
        DiaryTime diaryTime = new DiaryTime();
        if (isAddNode) {
            diaryTime.setMonth(timeUtils.getMonth()[calendar.get(Calendar.MONTH)]);
            diaryTime.setTime(sdf.format(calendar.getTime()));
            diaryTime.setDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            diaryTime.setDay(timeUtils.getDays()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        } else {
            try {
                DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
                Date date = fmt.parse(noteData);
                calendar.setTime(date);
                diaryTime.setMonth(timeUtils.getMonth()[calendar.get(Calendar.MONTH)]);
                diaryTime.setTime(sdf.format(calendar.getTime()));
                diaryTime.setDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                diaryTime.setDay(timeUtils.getDays()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            } catch (Exception e) {
                diaryTime.setMonth(timeUtils.getMonth()[calendar.get(Calendar.MONTH)]);
                diaryTime.setTime(sdf.format(calendar.getTime()));
                diaryTime.setDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                diaryTime.setDay(timeUtils.getDays()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            }
        }
        activityDiaryBinding.setDiaryTime(diaryTime);
    }

    private void viewEnabled(boolean enabled) {
        this.enabled = enabled;
        diary_time_information.setEnabled(enabled);
        photo.setEnabled(enabled);
        if (!enabled) {
            input.setImageDrawable(getResources().getDrawable(R.drawable.icon_uninput));
            note_rich.setVisibility(View.GONE);
            diary_content.setVisibility(View.VISIBLE);
            recode.setOnTouchListener(null);
            photo.setImageDrawable(getResources().getDrawable(R.drawable.icon_photo));
            del.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
        } else {
            del.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
            recode.setOnTouchListener(touchListener);
            photo.setImageDrawable(getResources().getDrawable(R.drawable.icon_photo));
            note_rich.setVisibility(View.VISIBLE);
            diary_content.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diary_time_information:
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(calendar.getTimeInMillis());
                datePickerFragment.setOnDateSetListener(this);
                datePickerFragment.show(getSupportFragmentManager(), "datePickerFragment");
                break;
            case R.id.save:
                save();
                break;
            case R.id.input:
                input.setImageDrawable(getResources().getDrawable(R.drawable.icon_input));
                viewEnabled(true);
                break;
            case R.id.location:
                break;
            case R.id.photo:
                startActivityForResult(new Intent(this, PhotoPickerActivity.class), REQUEST_PHOTO);
                break;
            case R.id.del:
                showDialog("删除日记", "确定删除这篇日记", R.id.del);
                break;
            case R.id.weather_icon:
                weatherPopup.showPopupWindow();
                break;
            case R.id.note_back:
                if (enabled) {
                    showDialog("内容修改", "内容已修改是否保存", R.id.note_back);
                } else {
                    onBackPressed();
                }
                break;
            case R.id.left_btn:
                if (enabled) {
                    showDialog("内容修改", "内容已修改是否保存", R.id.note_back);
                } else {
                    onBackPressed();
                }
                break;
            default:
                break;
        }
    }

    private void save() {
        List<EditTextData> editList = note_rich.GetEditData();

        final StringBuilder content = new StringBuilder();
        Observable.from(editList).subscribe(new Action1<EditTextData>() {
            @Override
            public void call(EditTextData data) {
                if (data.getInputStr() != null) {

                    content.append(data.getInputStr()).append("*");
                } else if (data.getImagePath() != null) {

                    content.append(data.getImagePath()).append("*");
                }
            }
        });
        String title = note_rich.getTitleData();
        long createTime = System.currentTimeMillis();
        //String dataStr = VeDate.getStringDateShort();
        String dataStr = noteData;

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            if (isAddNode) {
                if (update_location != null) {
                    mPresenter.insertNote(title, content.toString(), createTime, weatherPisition, dataStr);
                } else {
                    mPresenter.insertNote(title, content.toString(), createTime, weatherPisition, dataStr);
                }
            } else {
                if (update_location != null) {
                    mPresenter.updateNote(noteid, title, content.toString(), createTime, weatherPisition,
                            dataStr);
                } else {
                    mPresenter.updateNote(noteid, title, content.toString(), createTime, weatherPisition,
                            dataStr);
                }
            }

            diary_content.setTitle(title);

            String contentDate = content.toString();
            String sp = "\\*";
            String[] contentList = contentDate.split(sp);

            for (int i = 0; i < contentList.length; i++) {
                diary_content.addContent(contentList[i], i, true);
            }
        } else {
            Toast.makeText(this, getString(R.string.diary_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        viewEnabled(false);
    }

    @Override
    public void loadView(Throwable e) {

    }

    @Override
    protected DiaryPresenter getPresenter() {
        return new DiaryPresenter(this, this);
    }

    @Override
    public void saveStatus() {

    }

    @Override
    public void responseNoteDetail(Note note) {
        setCurrentTime(true, note);
        note_rich.setTitle(note.getTitle());
        note_rich.setContent(note.getContent());
        //weather_icon.setImageResource(weatherList.get(note.getWeatherPosition()).icon);
        diary_content.setTitle(note.getTitle());
        //lastLocation = note.getLocaltion();
        //diary_location.setText(lastLocation);
        String content = note.getContent();
        String sp = "\\*";
        String[] contentList = content.split(sp);
        for (int i = 0; i < contentList.length; i++) {
            note_rich.addContent(contentList[i], i);
            diary_content.addContent(contentList[i], i, false);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.isShown()) {
            calendar.set(year, monthOfYear, dayOfMonth);
            setCurrentTime(false, null);
            TimePickerFragment timePickerFragment =
                    TimePickerFragment.newInstance(calendar.getTimeInMillis());
            timePickerFragment.setOnTimeSetListener(this);
            timePickerFragment.show(getSupportFragmentManager(), "timePickerFragment");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setCurrentTime(false, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_PHOTO) {
            String[] photoPaths = data.getStringArrayExtra(PhotoPickerActivity.INTENT_PHOTO_PATHS);
            note_rich.addImageArray(photoPaths);
        }
    }

    @Override
    public void weatherPosition(int position) {
        weatherPisition = position;
        //weather_icon.setImageResource(weatherList.get(position).icon);
        weatherPopup.dismiss();
    }

    private void showDialog(String title, String content, final int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.note_back) {
                    finish();
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.del) {
                    mPresenter.delNote(noteid);
                    finish();
                } else if (id == R.id.note_back) {
                    save();
                    finish();
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (enabled) {
                showDialog("内容修改", "内容已修改是否保存", R.id.note_back);
            } else {
                onBackPressed();
            }
        }
        return false;
    }


  /* View.OnClickListener audioClick = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      type = 1;
      if (permissionChecker.isLackPermissions(PERMISSIONS)) {
        permissionChecker.requestPermissions();
      } else {
        startAudio();
      }
    }
  };

 View.OnClickListener videoClick = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      type = 2;
      if (permissionChecker.isLackPermissions(PERMISSIONS)) {
        permissionChecker.requestPermissions();
      } else {
        startVideo();
      }
    }
  };

  private void startAudio(){
    Intent intent = new Intent();
    intent.setClass(DiaryActivity.this,AudioRecorderActivity.class);
    startActivity(intent);
  }

  private void startVideo(){
    Intent intent = new Intent();
    intent.setClass(DiaryActivity.this,VideoRecorderActivity.class);
    startActivity(intent);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case PermissionChecker.PERMISSION_REQUEST_CODE:
        if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
          if(type == 1){
            startAudio();
          }else if(type == 2){
            startVideo();
          }
        } else {
          permissionChecker.showDialog();
        }
        break;
    }
  }*/

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            float downY = 0;
            int action = event.getAction();
            switch (v.getId()) {
                case R.id.location:
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            startAnim(true);
                            mediaUtils.record();
                            ret = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            stopAnim();
                            if (isCancel) {
                                isCancel = false;
                                mediaUtils.stopRecordUnSave();
                                Toast.makeText(DiaryActivity.this, "取消保存", Toast.LENGTH_SHORT).show();
                            } else {
                                int duration = getDuration(chronometer.getText().toString());
                                switch (duration) {
                                    case -1:
                                        break;
                                    case -2:
                                        mediaUtils.stopRecordUnSave();
                                        Toast.makeText(DiaryActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                                        break;
                                    default: {
                                        mediaUtils.stopRecordSave();
                                        String path = mediaUtils.getTargetFilePath();
                                        Toast.makeText(DiaryActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
                                        {
                                            String[] audioPaths = {path};
                                            note_rich.addImageArray(audioPaths);
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                moveAnim();
                                isCancel = true;
                            } else {
                                isCancel = false;
                                startAnim(false);
                            }
                            break;
                    }
                    break;
            }
            return ret;
        }
    };

    Chronometer.OnChronometerTickListener tickListener = new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000) {
                stopAnim();
                mediaUtils.stopRecordSave();
                Toast.makeText(DiaryActivity.this, "录音超时", Toast.LENGTH_SHORT).show();
                String path = mediaUtils.getTargetFilePath();
                Toast.makeText(DiaryActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                duration = d;
                return Integer.valueOf(d);
            } else {
                duration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            duration = "60";
            return -1;
        }
    }

    private void startAnim(boolean isStart) {
        audioLayout.setVisibility(View.VISIBLE);
        info.setText("上滑取消");
        //recode.setBackground(getResources().getDrawable(R.drawable.mic_pressed_bg));
        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.ic_mic_white_24dp));
        if (isStart) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%S");
            chronometer.start();
        }
    }

    private void stopAnim() {
        audioLayout.setVisibility(View.GONE);
        //recode.setBackground(getResources().getDrawable(R.drawable.mic_bg));
        chronometer.stop();
    }

    private void moveAnim() {
        info.setText("松手取消");
        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.ic_undo_black_24dp));
    }
}
