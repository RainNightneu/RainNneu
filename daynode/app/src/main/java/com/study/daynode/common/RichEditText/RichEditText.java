package com.study.daynode.common.RichEditText;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.study.daynode.R;
import com.study.daynode.common.ImageLoadFresco;
import com.study.daynode.mediautils.MediaUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * wuxiaojian
 */
public class RichEditText extends ScrollView implements EditTextDataI {

  /**
   * 标题字数限制
   */
  private static final int TITLE_COUNT = 25;

  /**
   * 图文排序的时候，view默认缩小的高度
   */
  private final int SIZE_REDUCE_VIEW = dip2px(80);
  private final int WIDTH = dip2px(280);
  private final int HEIGHT = dip2px(400);

  /**
   * 出发ScrollView滚动时，顶部与底部的偏移量
   */
  private final int SCROLL_OFFSET = (int) (SIZE_REDUCE_VIEW * .3);

  /**
   * 默认Marging
   */
  private final int DEFAULT_MARGING = dip2px(15);

  /**
   * 拖动排序的时候，当在ScrollView边界拖动时默认自滚动速度
   */
  private final int DEFAULT_SCROLL_SPEED = dip2px(15);

  /**
   * 虚线背景
   */
  private final GradientDrawable dashDrawable;

  /**
   * 因为排序状态下会修改EditText的Background，所以这里保存默认EditText
   * 的Background, 当排序完成后用于还原EditText默认的Background
   */
  private Drawable editTextBackground;

  /**
   * 布局填充器
   */
  private LayoutInflater inflater;

  /**
   * 每创建一个child，为该child赋一个ID，该ID保存在view的tag属性中
   */
  private int viewTagID = 1;

  private LinearLayout parentLayout;

  /**
   * 标题栏ViewGroup
   */
  private LinearLayout titleLayout;

  /**
   * 用于放置各种文本图片内容的容器
   */
  private LinearLayout containerLayout;

  /**
   * EditText的软键盘监听器
   */
  private OnKeyListener editTextKeyListener;

  /**
   * EditText和ImageView的焦点监听listener
   */
  private OnFocusChangeListener focusListener;

  /**
   * 最近获取焦点的一个EditText
   */
  private EditText lastFocusEdit;

  /**
   * 标题栏EditText
   */
  private EditText etTitle;

  /**
   * 添加或者删除图片View时的Transition动画
   */
  private LayoutTransition mTransitioner;

  /**
   * 用于实现拖动效果的帮助类
   */
  private ViewDragHelper viewDragHelper;

  /**
   * 因为文字长短不一（过长换行让EditText高度增大），导致EditText高度不一，
   * 所以需要一个集合存储排序之前未缩小/放大的EditText高度
   */
  private SparseArray<Integer> editTextHeightArray = new SparseArray<>();

  /**
   * 准备排序时，缩小各个child，并存放缩小的child的top作为该child的position值
   */
  private SparseArray<Integer> preSortPositionArray;

  /**
   * 排序完成后，子child位置下标
   */
  private SparseIntArray indexArray = new SparseIntArray();

  /**
   * 容器相对于屏幕顶部和底部的长度值，用于排序拖动Child的时候判定ScrollView是否滚动
   */
  private int containerTopVal, containerBottomVal;

  /**
   * 循环线程执行器，用于拖动view到边缘时ScrollView自动滚动功能
   */
  private ScheduledExecutorService scheduledExecutorService;

  /**
   * 是否正在自动滚动
   */
  private boolean isAutoScroll;

  /**
   * 自动滚动速度向量
   */
  private int scrollVector;

  private float currRawY;

  private float preY;
  private Context mContext;
  private EditText editText;
  private List<EditTextData> dataList;
  private int width;
  private int height;

  public RichEditText(Context context) {
    this(context, null);
  }

  public RichEditText(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    inflater = LayoutInflater.from(context);
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    width = wm.getDefaultDisplay().getWidth();
    height = wm.getDefaultDisplay().getHeight();
    initListener();

    initParentLayout();

    initTitleLayout();

    initContainerLayout();

    // 初始化虚线背景 Drawable
    dashDrawable = new GradientDrawable();
    dashDrawable.setStroke(dip2px(1), Color.parseColor("#4CA4E9"), dip2px(4), dip2px(3));
    dashDrawable.setColor(Color.parseColor("#ffffff"));

    // 初始化ViewDragHelper
    viewDragHelper = ViewDragHelper.create(containerLayout, 1.5f, new ViewDragHelperCallBack());
  }

  /**
   * 初始化分割线（用来分开标题栏ViewGroup与内容容器ViewGroup）
   */
  private void initLineView() {
    // 父容器中中添加一条分割线用来分开标题栏ViewGroup与内容容器ViewGroup
    View lineView = new View(getContext());
    lineView.setBackgroundColor(Color.parseColor("#dddddd"));

    LinearLayout.LayoutParams lineLayoutParams =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
    lineLayoutParams.leftMargin = DEFAULT_MARGING;
    lineLayoutParams.rightMargin = DEFAULT_MARGING;
    lineView.setLayoutParams(lineLayoutParams);
    titleLayout.addView(lineView);
  }

  /**
   * 创建标题栏ViewGroup以及标题栏中编辑标题的EditText和字数提醒的TextView
   */
  private void initTitleLayout() {

    // 创建标题栏的ViewGroup
    titleLayout = new LinearLayout(getContext());
    titleLayout.setOrientation(LinearLayout.VERTICAL);
    titleLayout.setPadding(0, DEFAULT_MARGING, 0, DEFAULT_MARGING);

    LinearLayout.LayoutParams titleLayoutParams =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    titleLayout.setLayoutParams(titleLayoutParams);

    parentLayout.addView(titleLayout);

    // 标题栏的ViewGroup中添加一个显示字数限制的提醒TextView(先创建，待先插入标题栏EditText之后再插入tvTextLimit)
    final TextView tvTextLimit = new TextView(getContext());
    tvTextLimit.setText(String.format("0/%d", TITLE_COUNT));
    tvTextLimit.setTextColor(Color.parseColor("#aaaaaa"));
    tvTextLimit.setTextSize(13);

    LinearLayout.LayoutParams textLimitLayoutParams =
        new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    textLimitLayoutParams.rightMargin = DEFAULT_MARGING;
    textLimitLayoutParams.topMargin = DEFAULT_MARGING / 2;
    textLimitLayoutParams.gravity = Gravity.RIGHT;
    tvTextLimit.setLayoutParams(textLimitLayoutParams);

    // 标题栏的ViewGroup中添加一个EditText，用来填写标题文本
    etTitle = (EditText) inflater.inflate(R.layout.edit, null);
    etTitle.setHint("title");
    etTitle.setHintTextColor(Color.parseColor("#aaaaaa"));
    etTitle.setGravity(Gravity.TOP);
    etTitle.setCursorVisible(true);
    InputFilter[] filters = { new InputFilter.LengthFilter(TITLE_COUNT) };
    etTitle.setFilters(filters);
    etTitle.setBackgroundResource(android.R.color.transparent);
    etTitle.setTextColor(Color.BLACK);
    etTitle.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        String titleStr = etTitle.getText().toString();
        tvTextLimit.setText(String.format("%d/%d", titleStr.length(), TITLE_COUNT));
      }

      @Override public void afterTextChanged(Editable s) {
      }
    });

    LinearLayout.LayoutParams editTitleLayoutParams =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    editTitleLayoutParams.leftMargin = DEFAULT_MARGING;
    editTitleLayoutParams.rightMargin = DEFAULT_MARGING;
    etTitle.setLayoutParams(editTitleLayoutParams);

    titleLayout.addView(etTitle);
    initLineView();
    titleLayout.addView(tvTextLimit);
  }

  public void setTextTitleHint() {
    etTitle.setHint("title");
    etTitle.setText("");
    editText.setHint("content");
    editText.setText("");
    parentLayout.removeAllViews();
    initTitleLayout();

    initContainerLayout();
  }

  public void setTitle(String title) {
    etTitle.setText(title);
  }

  public void setContent(String content) {
    editText.setText(content);
  }

  /**
   * 创建父容器LinearLayout，指定为ScrollView的子View
   */
  private void initParentLayout() {
    // 因为ScrollView的子view只能有一个，并且是ViewGroup,所以先创建一个Linearlayout父容器，用来放置所有其他ViewGroup
    parentLayout = new LinearLayout(getContext());
    parentLayout.setOrientation(LinearLayout.VERTICAL);

    LayoutParams layoutParams =
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    parentLayout.setLayoutParams(layoutParams);

    addView(parentLayout);
  }

  /**
   * 初始化监听器
   */
  private void initListener() {
    // 初始化键盘退格监听
    // 主要用来处理点击回删按钮时，view的一些列合并操作
    editTextKeyListener = new OnKeyListener() {

      @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
            && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
          EditText edit = (EditText) v;
          onBackspacePress(edit);
        }
        return false;
      }
    };

    focusListener = new OnFocusChangeListener() {

      @Override public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof RelativeLayout) { // 图片
          processSoftKeyBoard(false);
        } else if (v instanceof EditText) {
          if (hasFocus) {
            lastFocusEdit = (EditText) v;
          }
        }
      }
    };
  }

  /**
   * 初始化ContainerLayout文本内容容器
   */
  private void initContainerLayout() {
    containerLayout = createContaniner();
    parentLayout.addView(containerLayout);

    EditText firstEdit = createEditText("content");
    editTextHeightArray.put(Integer.parseInt(firstEdit.getTag().toString()),
        ViewGroup.LayoutParams.WRAP_CONTENT);
    editTextBackground = firstEdit.getBackground();
    containerLayout.addView(firstEdit);
    lastFocusEdit = firstEdit;
  }

  /**
   * 停止ScrollView的自动滚动
   */
  private void stopOverEdgeAutoScroll() {
    if (isAutoScroll) {
      scheduledExecutorService.shutdownNow();
      isAutoScroll = false;
    }
  }

  /**
   * 拖动view到或者超出边缘时，ScrollView开始自动滚动
   */
  private void startOverEdgeAutoScroll() {
    if (!isAutoScroll) {
      scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
      scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
        @Override public void run() {
          RichEditText.this.scrollBy(0, scrollVector);
        }
      }, 0, 15, TimeUnit.MILLISECONDS);
      isAutoScroll = true;
    }
  }

  /**
   * 创建图片文本内容容器
   */
  @NonNull private LinearLayout createContaniner() {
    LayoutParams layoutParams =
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    final LinearLayout containerLayout = new LinearLayout(getContext()) {
      @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
      }

      @Override public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        int action = event.getAction();
        switch (action) {
          case MotionEvent.ACTION_DOWN:
            break;
          case MotionEvent.ACTION_MOVE:

            break;
          case MotionEvent.ACTION_UP:
            stopOverEdgeAutoScroll();
            break;
        }
        return true;
      }

      @Override public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(false);

        return super.dispatchTouchEvent(ev);
      }
    };
    containerLayout.setPadding(0, DEFAULT_MARGING, 0, DEFAULT_MARGING);
    containerLayout.setOrientation(LinearLayout.VERTICAL);
    containerLayout.setBackgroundColor(Color.WHITE);
    containerLayout.setLayoutParams(layoutParams);
    setupLayoutTransitions(containerLayout);
    return containerLayout;
  }

  /**
   * 处理软键盘backSpace回退事件
   *
   * @param editTxt 光标所在的文本输入框
   */
  private void onBackspacePress(EditText editTxt) {

    int startSelection = editTxt.getSelectionStart();
    // 只有在光标已经顶到文本输入框的最前方，在判定是否删除之前的图片，或两个View合并
    if (startSelection == 0) {
      int editIndex = containerLayout.indexOfChild(editTxt);
      View preView = containerLayout.getChildAt(editIndex - 1); // 如果editIndex-1<0,
      // 则返回的是null
      if (null != preView) {
        if (preView instanceof RelativeLayout || preView instanceof ImageView) {
          // 光标EditText的上一个view对应的是图片或者是一个“将来可编辑文本”的图标
          onImageDeleteClick(preView);
        } else if (preView instanceof EditText) {
          // 光标EditText的上一个view对应的还是文本框EditText
          String str1 = editTxt.getText().toString();
          EditText preEdit = (EditText) preView;
          String str2 = preEdit.getText().toString();

          // 合并文本view时，不需要transition动画
          containerLayout.setLayoutTransition(null);
          containerLayout.removeView(editTxt);
          containerLayout.setLayoutTransition(mTransitioner); // 恢复transition动画

          // 文本合并
          preEdit.setText(str2 + str1);
          preEdit.requestFocus();
          preEdit.setSelection(str2.length(), str2.length());
          lastFocusEdit = preEdit;
        }
      }
    }
  }

  /**
   * 处理图片删除击事件
   *
   * @param view 整个image对应的relativeLayout view
   */
  private void onImageDeleteClick(View view) {
    if (!mTransitioner.isRunning()) {
      int index = containerLayout.indexOfChild(view);
      int nextIndex = index + 1;
      int lastIndex = index - 1;

      View child;
      if (index == 0) { // 删除图片位于第一个位置，只检查下一个位置的View是否为“可编辑文本”的图标
        child = containerLayout.getChildAt(nextIndex);
      } else {
        // 先检查上一个位置的View是否为“可编辑文本”的图标，如果不是就检查下一个位置的View
        child = containerLayout.getChildAt(lastIndex);
        if (!(child instanceof ImageView)) {
          child = containerLayout.getChildAt(nextIndex);
        }
      }

      if (child instanceof ImageView) {
        // 如果该View是“可编辑文本”的图标，则一并删除
        containerLayout.removeView(child);
      }

      containerLayout.removeView(view);
    }
  }

  /**
   * 生成一个“将来用于编辑文字的图片”ImageView
   *
   * @return ImageView
   */
  @NonNull private ImageView createInsertEditTextImageView() {
    final ImageView ivInsertEditText = new ImageView(getContext());
    ivInsertEditText.setTag(viewTagID++);
    ivInsertEditText.setScaleType(ImageView.ScaleType.FIT_START);
    ivInsertEditText.setClickable(true);
    ivInsertEditText.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        int index = containerLayout.indexOfChild(ivInsertEditText);
        containerLayout.removeView(ivInsertEditText);
        EditText editText = insertEditTextAtIndex(index, "");
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        lastFocusEdit = editText;
        processSoftKeyBoard(true);
      }
    });

    // 调整ImageView的外边距
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    lp.bottomMargin = DEFAULT_MARGING;
    ivInsertEditText.setLayoutParams(lp);

    return ivInsertEditText;
  }

  public void addContent(final String content, final int position) {
    lastFocusEdit.setText("");
    lastFocusEdit.setHint("");

    if (isImage(content)) {
      final RelativeLayout imageLayout = createImageLayout(content);
      SimpleDraweeView imageView = (SimpleDraweeView) imageLayout.getChildAt(0);
      new ImageLoadFresco.LoadImageFrescoBuilder(mContext, imageView,
          "file:///" + content).setIsRadius(true).build();
      imageView.setTag(content);
      /*imageView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          Toast.makeText(mContext, "点击啦", Toast.LENGTH_LONG).show();
        }
      });*/
      // 批量插入需要立即执行，否则index错误（舍弃动画效果）
      containerLayout.addView(imageLayout, position);
    } else if (isAudioImage(content)) {
      final RelativeLayout imageLayout = createImageLayout(content);
      SimpleDraweeView imageView = (SimpleDraweeView) imageLayout.getChildAt(0);
      ViewGroup.LayoutParams para = imageView.getLayoutParams();
      para.width = 250;//修改宽度
      para.height = 250;//修改高度
      imageView.setLayoutParams(para);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageURI(Uri.parse("res://com.study.daynode/" + R.drawable.play));
      imageView.setTag(content);
      imageView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          //Toast.makeText(context, "点击啦" + content, Toast.LENGTH_LONG).show();
          playVoice(content);
        }
      });
      containerLayout.addView(imageLayout, position);
    } else {
      insertEditTextAtIndex(position, content);
    }
    processSoftKeyBoard(false);
  }

  private void playVoice(String path) {
    try {
      MediaPlayer player = new MediaPlayer();
      player.setDataSource(path);
      player.prepare();
      player.start();
    } catch (Exception e) {
    }
  }

  /**
   * 生成文本输入框
   */
  private EditText createEditText(String hint) {
    editText = (EditText) inflater.inflate(R.layout.edit, null);
    editText.setOnKeyListener(editTextKeyListener);
    editText.setTag(viewTagID++);
    editText.setHint(hint);
    editText.setTextColor(Color.BLACK);
    editText.setOnFocusChangeListener(focusListener);
    editText.setPadding(0, 10, 0, 10);
    editText.setHintTextColor(Color.parseColor("#aaaaaa"));
    // 调整EditText的外边距
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    lp.bottomMargin = DEFAULT_MARGING;
    lp.leftMargin = DEFAULT_MARGING;
    lp.rightMargin = DEFAULT_MARGING;
    editText.setLayoutParams(lp);
    return editText;
  }

  public void showMethodManager() {
    editText.setFocusable(true);
    editText.setFocusableInTouchMode(true);
    editText.requestFocus();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {

      @Override public void run() {
        InputMethodManager imm =
            (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
      }
    }, 100);
  }

  public void hideMethodManager() {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
  }

  /**
   * 生成图片Layout
   */
  private RelativeLayout createImageLayout(String content) {

    RelativeLayout.LayoutParams contentImageLp =
        new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    SimpleDraweeView simpleDraweeView =
        (SimpleDraweeView) inflater.inflate(R.layout.drawee_view, null);
    simpleDraweeView.setLayoutParams(contentImageLp);

    RelativeLayout layout = new RelativeLayout(getContext());
    layout.addView(simpleDraweeView);
    layout.setTag(viewTagID++);
    setFocusOnView(layout, true);

    int bitmapWidth = getbitmapWidth(content);
    int bitmapHeight = getbitmapHeight(content);
    if (getbitmapWidth(content) > width - width / 3) {
      bitmapWidth = WIDTH;
      bitmapHeight = HEIGHT;
    }
    // 调整imageView的外边距
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(bitmapWidth, bitmapHeight);
    lp.bottomMargin = DEFAULT_MARGING;
    lp.leftMargin = DEFAULT_MARGING;
    lp.rightMargin = DEFAULT_MARGING;
    layout.setLayoutParams(lp);

    return layout;
  }

  private void setFocusOnView(View view, boolean isFocusable) {
    view.setClickable(isFocusable);
    view.setFocusable(isFocusable);
    view.setFocusableInTouchMode(isFocusable);
    if (isFocusable) {
      view.setOnFocusChangeListener(focusListener);
    } else {
      view.setOnFocusChangeListener(null);
    }
  }

  public int getbitmapHeight(String content) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    Bitmap bitmap = BitmapFactory.decodeFile(content, options);

    return options.outHeight;
  }

  public int getbitmapWidth(String content) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    Bitmap bitmap = BitmapFactory.decodeFile(content, options);
    return options.outWidth;
  }

  /**
   * 插入图片前的预处理
   */
  private void prepareAddImage() {
    View firstView = containerLayout.getChildAt(0);
    if (containerLayout.getChildCount() == 1 && firstView == lastFocusEdit) {
      lastFocusEdit = (EditText) firstView;
      lastFocusEdit.setHint("content");
    }
  }

  /**
   * 插入一张图片
   */
  private void insertImage(String imagePath) {
    String lastEditStr = lastFocusEdit.getText().toString();
    int cursorIndex = lastFocusEdit.getSelectionStart();
    String lastStr = lastEditStr.substring(0, cursorIndex).trim();
    int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);

    if (lastEditStr.length() == 0 || lastStr.length() == 0) {
      // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
      insertImageViewAtIndex(lastEditIndex, imagePath, false);
    } else {
      // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
      lastFocusEdit.setText(lastStr);
      String editStr2 = lastEditStr.substring(cursorIndex).trim();
      if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
        lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
        lastFocusEdit.requestFocus();
        lastFocusEdit.setSelection(0);
      }
      insertImageViewAtIndex(lastEditIndex + 1, imagePath, false);
    }
    processSoftKeyBoard(false);
  }

  /**
   * 批量插入一组图片
   */
  private void insertBatchImage(List<String> imagePathList) {
    String imagePath;
    for (int i = 0; i < imagePathList.size(); i++) {
      imagePath = imagePathList.get(i);
      insertImageViewAtIndex(containerLayout.indexOfChild(lastFocusEdit), imagePath, true);
    }
  }

  /**
   * 隐藏或者显示软键盘
   *
   * @param isShow true:显示，false:隐藏
   */
  public void processSoftKeyBoard(boolean isShow) {
    InputMethodManager imm =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (isShow) {
      imm.showSoftInput(lastFocusEdit, InputMethodManager.SHOW_FORCED);
    } else {
      imm.hideSoftInputFromWindow(lastFocusEdit.getWindowToken(), 0);
    }
  }

  /**
   * 在指定位置添加一个“将来用于编辑文字的图片”
   */
  private void insertEditTextImageView(int index) {
    ImageView ivInsertEditText = createInsertEditTextImageView();
    containerLayout.addView(ivInsertEditText, index);
  }

  /**
   * 在指定位置插入EditText
   *
   * @param index 位置
   * @param editStr EditText显示的文字
   */
  public EditText insertEditTextAtIndex(final int index, String editStr) {
    EditText editText = createEditText("");
    editText.setText(editStr);
    // 请注意此处，EditText添加、或删除不触动Transition动画
    containerLayout.setLayoutTransition(null);
    containerLayout.addView(editText, index);
    containerLayout.setLayoutTransition(mTransitioner); // add之后恢复transition动画
    return editText;
  }

  /**
   * 在指定位置添加ImageView
   */
  private void insertImageViewAtIndex(int index, final String imagePath, boolean isBatch) {
    if (index > 0) {
      View currChild = containerLayout.getChildAt(index);
      // 当前index位置的child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
      if (currChild instanceof RelativeLayout) {
        insertEditTextImageView(index);
      }

      int lastIndex = index - 1;
      View child = containerLayout.getChildAt(lastIndex);
      // index位置的上一个child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
      if (child instanceof RelativeLayout) {
        insertEditTextImageView(index++);
      }
    }
    final RelativeLayout imageLayout = createImageLayout(imagePath);
    SimpleDraweeView imageView = (SimpleDraweeView) imageLayout.getChildAt(0);
    if (isImage(imagePath)) {

      new ImageLoadFresco.LoadImageFrescoBuilder(mContext, imageView,
          "file:///" + imagePath).setIsRadius(true).build();
      imageView.setTag(imagePath);
    } else if (isAudioImage(imagePath)) {
      ViewGroup.LayoutParams para = imageView.getLayoutParams();
      para.width = 250;//修改宽度
      para.height = 250;//修改高度
      imageView.setLayoutParams(para);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageURI(Uri.parse("res://com.study.daynode/" + R.drawable.play));
      imageView.setTag(imagePath);
      imageView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          //Toast.makeText(context, "点击啦" + content, Toast.LENGTH_LONG).show();
          playVoice(imagePath);
        }
      });
    }

    final int finalIndex = index;
    if (isBatch) {
      // 批量插入需要立即执行，否则index错误（舍弃动画效果）
      containerLayout.addView(imageLayout, finalIndex);
    } else {
      // 单张插入onActivityResult无法触发动画，此处post处理
      containerLayout.postDelayed(new Runnable() {
        @Override public void run() {
          containerLayout.addView(imageLayout, finalIndex);
        }
      }, 200);
    }
  }

  /**
   * 初始化transition动画
   */
  private void setupLayoutTransitions(LinearLayout containerLayout) {
    mTransitioner = new LayoutTransition();
    containerLayout.setLayoutTransition(mTransitioner);
    mTransitioner.setDuration(300);
  }

  /**
   * dp和pixel转换
   *
   * @param dipValue dp值
   * @return 像素值
   */
  private int dip2px(float dipValue) {
    float m = getContext().getResources().getDisplayMetrics().density;
    return (int) (dipValue * m + 0.5f);
  }

  private void prepareSortConfig() {
    indexArray.clear();

    int[] position = new int[2];
    RichEditText.this.getLocationOnScreen(position);

    RichEditText sortRichEditor = RichEditText.this;
    containerTopVal = position[1] + sortRichEditor.getPaddingTop() + SCROLL_OFFSET;
    containerBottomVal = containerTopVal + sortRichEditor.getHeight()
        - sortRichEditor.getPaddingBottom()
        - SCROLL_OFFSET;
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
  }

  @Override public void computeScroll() {
    super.computeScroll();
    if (viewDragHelper.continueSettling(true)) {
      invalidate();
    }
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        preY = ev.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        if (Math.abs(ev.getY() - preY) >= viewDragHelper.getTouchSlop()) {
          processSoftKeyBoard(false);
        }
        break;
    }
    return super.onTouchEvent(ev);
  }

  /**
   * 重新排列Child的位置，更新{@link #indexArray} 中view的下标顺序
   */
  private void resetChildPostion() {
    indexArray.clear();
    View child;
    int tagID, sortIndex;
    int childCount = containerLayout.getChildCount();
    for (int i = 0; i < childCount; i++) {
      child = containerLayout.getChildAt(i);
      tagID = Integer.parseInt(child.getTag().toString());
      sortIndex = (preSortPositionArray.get(tagID) - DEFAULT_MARGING) / (SIZE_REDUCE_VIEW
          + DEFAULT_MARGING);
      indexArray.put(i, sortIndex);
    }
  }

  /**
   * 根据绝对路径添加一张图片
   */
  @Override public void addImage(String imagePath) {
    prepareAddImage();
    insertImage(imagePath);
  }

  /**
   * 根据图片绝对路径数组批量添加一组图片
   */
  @Override public void addImageArray(String[] imagePaths) {
    if (imagePaths == null) return;
    addImageList(Arrays.asList(imagePaths));
  }

  /**
   * 根据图片绝对路径集合批量添加一组图片
   */
  @Override public void addImageList(List<String> imageList) {
    if (imageList == null || imageList.isEmpty()) return;
    prepareAddImage();

    String lastEditStr = lastFocusEdit.getText().toString();
    int cursorIndex = lastFocusEdit.getSelectionStart();
    String lastStr = lastEditStr.substring(0, cursorIndex).trim();
    int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);

    // 如果EditText非空且光标不在最顶端，则添加该组图片后，最后插入一个EditText
    lastFocusEdit.setText(lastStr);
    String editStr2 = lastEditStr.substring(cursorIndex).trim();
    if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
      lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
      lastFocusEdit.requestFocus();
      lastFocusEdit.setSelection(0);
    }
    insertBatchImage(imageList);
  }

  /**
   * 对外提供的接口, 生成编辑数据上传
   */
  @Override public List<EditTextData> GetEditData() {
    dataList = new ArrayList<>();
    int num = containerLayout.getChildCount();
    for (int index = 0; index < num; index++) {
      View itemView = containerLayout.getChildAt(index);
      EditTextData itemData = new EditTextData();
      if (itemView instanceof EditText) {
        EditText item = (EditText) itemView;
        itemData.setInputStr(item.getText().toString());
      } else if (itemView instanceof RelativeLayout) {
        SimpleDraweeView item = (SimpleDraweeView) ((RelativeLayout) itemView).getChildAt(0);
        itemData.setImagePath(item.getTag().toString());
      }
      dataList.add(itemData);
    }

    return dataList;
  }

  /**
   * 获取当前编辑器中图片数量
   */
  @Override public int getImageCount() {
    int imageCount = 0;
    int num = containerLayout.getChildCount();
    for (int index = 0; index < num; index++) {
      View child = containerLayout.getChildAt(index);
      if (child instanceof RelativeLayout) imageCount++;
    }
    return imageCount;
  }

  /**
   * 编辑器内容是否为空
   */
  @Override public boolean isContentEmpty() {
    boolean isEmpty = true;
    int num = containerLayout.getChildCount();
    for (int index = 0; index < num; index++) {
      View itemView = containerLayout.getChildAt(index);
      if (itemView instanceof ImageView) continue;
      if (itemView instanceof EditText) {
        EditText item = (EditText) itemView;
        if (!TextUtils.isEmpty(item.getText().toString().trim())) {
          isEmpty = false;
          break;
        }
      } else if (itemView instanceof RelativeLayout) {
        ImageView item = (ImageView) ((RelativeLayout) itemView).getChildAt(0);
        if (!TextUtils.isEmpty(item.getTag().toString())) {
          isEmpty = false;
          break;
        }
      }
    }
    return isEmpty;
  }

  /**
   * 获取标题
   */
  @Override public String getTitleData() {
    return etTitle.getText().toString().trim();
  }

  private class ViewDragHelperCallBack extends ViewDragHelper.Callback {

    @Override public boolean tryCaptureView(View child, int pointerId) {
      return (child instanceof RelativeLayout);
    }

    @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
      final int leftBound = getPaddingLeft() + DEFAULT_MARGING;
      final int rightBound = getWidth() - child.getWidth() - leftBound;
      final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
      return newLeft;
    }

    @Override public int clampViewPositionVertical(View child, int top, int dy) {
      return top;
    }

    @Override public int getViewVerticalDragRange(View child) {
      return 0;
    }

    @Override public int getViewHorizontalDragRange(View child) {
      return 0;
    }

    @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
      int releasedViewID = Integer.parseInt(releasedChild.getTag().toString());
      int releasedViewPos = preSortPositionArray.get(releasedViewID);
      viewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), releasedViewPos);
      invalidate();
    }

    @Override public void onViewCaptured(View capturedChild, int activePointerId) {

    }

    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
      int reduceChildCount = containerLayout.getChildCount();

      View sortChild;
      int changeViewTagID, sortViewTagID, changeViewPosition, sortViewPosition;
      for (int i = 0; i < reduceChildCount; i++) {
        sortChild = containerLayout.getChildAt(i);
        if (sortChild != changedView) {
          changeViewTagID = Integer.parseInt(changedView.getTag().toString());
          sortViewTagID = Integer.parseInt(sortChild.getTag().toString());

          changeViewPosition = preSortPositionArray.get(changeViewTagID);
          sortViewPosition = preSortPositionArray.get(sortViewTagID);

          if ((changedView.getTop() > sortChild.getTop() && changeViewPosition < sortViewPosition)
              || (changedView.getTop() < sortChild.getTop()
              && changeViewPosition > sortViewPosition)) {

            sortChild.setTop(changeViewPosition);
            sortChild.setBottom(changeViewPosition + SIZE_REDUCE_VIEW);

            preSortPositionArray.put(sortViewTagID, changeViewPosition);
            preSortPositionArray.put(changeViewTagID, sortViewPosition);

            resetChildPostion();
            break;
          }
        }
      }
    }

    @Override public void onViewDragStateChanged(int state) {
      super.onViewDragStateChanged(state);
    }
  }

  /**
   * 判断该文件是否是一个图片
   */
  private boolean isImage(String fileName) {
    return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
  }

  /**
   * 是否事音频图片
   */
  private boolean isAudioImage(String fileName) {
    return fileName.endsWith(MediaUtils.MEDIA_FORMAT);
  }
}
