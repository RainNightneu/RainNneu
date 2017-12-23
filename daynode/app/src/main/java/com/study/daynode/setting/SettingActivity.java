package com.study.daynode.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.study.daynode.R;
import com.study.daynode.common.ThemeManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by wuxiaojian on 16/12/15.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageView theme_one;
    private ImageView theme_two;
    private View titleView;
    private TextView tv_cancel_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        titleView = findViewById(R.id.relay_title);
        findViewById(R.id.tv_complete).setVisibility(View.GONE);
        tv_cancel_text = (TextView) findViewById(R.id.tv_cancel_text);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        tv_cancel_text.setText("设置");
        titleView.setBackgroundColor(ThemeManager.getInstance().getThemeColor(this));
        theme_one = (ImageView) findViewById(R.id.theme_one);
        theme_two = (ImageView) findViewById(R.id.theme_two);
        theme_one.setOnClickListener(this);
        theme_two.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme_one:
                ThemeManager.setCurrentTheme(this, ThemeManager.THEME_ONE);
                retSettheme();
                break;
            case R.id.theme_two:
                ThemeManager.setCurrentTheme(this, ThemeManager.THEME_TWO);
                retSettheme();
                break;
            case R.id.tv_cancel:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void retSettheme() {
        Toast.makeText(this, getString(R.string.change_theme), Toast.LENGTH_SHORT).show();

        Intent i = this.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(this.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }

}
