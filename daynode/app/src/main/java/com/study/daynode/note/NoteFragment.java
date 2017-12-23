package com.study.daynode.note;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.startsmake.mainnavigatetabbar.widget.FragmentVisibilityListener;
import com.study.daynode.R;
import com.study.daynode.base.BaseFragment;
import com.study.daynode.bean.Note;
import com.study.daynode.common.ThemeManager;
import com.study.daynode.databinding.FragmentNoteBinding;
import java.util.List;

/**
 */
public class NoteFragment extends BaseFragment<NotePresenter> implements NoteContract.View,FragmentVisibilityListener {

  private RecyclerView note_list;
  private ImageView left_btn, right_btn;
  private TextView title_tx;
  private NoteAdapter noteAdapter;
  private FragmentNoteBinding fragmentNoteBinding;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    fragmentNoteBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
    note_list = fragmentNoteBinding.noteList;
    fragmentNoteBinding.setSetThemeBg(ThemeManager.getInstance().getBgDrawable(getActivity()));
    fragmentNoteBinding.setSetTextColor(ThemeManager.getInstance().getThemeColor(getActivity()));
    note_list.setLayoutManager(new LinearLayoutManager(getActivity()));
    left_btn = (ImageView) fragmentNoteBinding.getRoot().findViewById(R.id.left_btn);
    left_btn.setVisibility(View.GONE);
    right_btn = (ImageView) fragmentNoteBinding.getRoot().findViewById(R.id.right_bnt);
    right_btn.setVisibility(View.GONE);
    title_tx = (TextView) fragmentNoteBinding.getRoot().findViewById(R.id.title_tx);
    title_tx.setText("日记");
    mPresenter.getNoteList();
    return fragmentNoteBinding.getRoot();
  }

  public static NoteFragment newInstance() {
    NoteFragment fragment = new NoteFragment();
    return fragment;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (NoteAdapter.clickItem) {
      mPresenter.getNoteList();
      NoteAdapter.clickItem = false;
    }
  }

  @Override protected NotePresenter getPresenter() {
    return new NotePresenter(getActivity(), this);
  }

  @Override public void loadView(Throwable e) {

  }

  @Override public void responseNoteList(List<Note> noteList) {
    noteAdapter = new NoteAdapter(noteList, getActivity());
    note_list.setAdapter(noteAdapter);
  }

  @Override public void onFragmentVisible() {
    mPresenter.getNoteList();
  }

  @Override public void onFragmentInvisible() {

  }
}
