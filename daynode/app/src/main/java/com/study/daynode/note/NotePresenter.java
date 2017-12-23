package com.study.daynode.note;

import android.app.Activity;
import com.study.daynode.base.BasePresenter;
import com.study.daynode.bean.Note;
import com.study.daynode.common.GreenDaoManager;
import com.study.daynode.gen.NoteDao;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by wuxiaojian on 16/12/5.
 */
public class NotePresenter extends BasePresenter<NoteContract.View> implements NoteContract.Presenter {
    public NotePresenter(Activity activity, NoteContract.View view) {
        super(activity, view);
    }

    @Override
    public void getNoteList() {

        NoteDao noteDao = GreenDaoManager.getInstance().getSession().getNoteDao();
        QueryBuilder<Note> queryBuilder = noteDao.queryBuilder();
        List<Note> noteList = queryBuilder.orderDesc(NoteDao.Properties.CreateTime).list();
        mView.responseNoteList(noteList);

    }
}
