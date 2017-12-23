package com.study.daynode.diary;

import android.app.Activity;
import com.study.daynode.base.BasePresenter;
import com.study.daynode.bean.Note;
import com.study.daynode.common.GreenDaoManager;
import com.study.daynode.gen.NoteDao;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by wuxiaojian on 16/12/5.
 */
public class DiaryPresenter extends BasePresenter<DiaryContract.DiaryView>
    implements DiaryContract.DiaryPresenter {

  public DiaryPresenter(Activity activity, DiaryContract.DiaryView view) {
    super(activity, view);
  }

  @Override
  public void insertNote(String noteTitle, String noteContent, long creatTime, int weatherPosition,
      String location) {
    NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
    Note note = new Note(null, creatTime, noteContent, noteTitle, weatherPosition, location);
    noteDao.insert(note);
    mView.saveStatus();
  }

  @Override public void updateNote(Long id, String noteTitle, String noteContent, long creatTime,
      int weatherPosition, String location) {
    NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
    Note note = new Note(id, creatTime, noteContent, noteTitle, weatherPosition, location);
    noteDao.update(note);
  }

  @Override public void getNote(long noteId) {
    NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
    QueryBuilder<Note> queryBuilder =
        noteDao.queryBuilder().where(NoteDao.Properties.Id.eq(noteId));
    Note note = queryBuilder.unique();
    mView.responseNoteDetail(note);
  }

  @Override public boolean getNoteByData(String data) {
    NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
    QueryBuilder<Note> queryBuilder =
        noteDao.queryBuilder().where(NoteDao.Properties.Localtion.eq(data));
    Note note = queryBuilder.unique();
    if (note != null) {
      mView.responseNoteDetail(note);
      return true;
    } else {
      return false;
    }
  }

  @Override public void delNote(long noteId) {
    NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
    noteDao.deleteByKey(noteId);
  }
}
