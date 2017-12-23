package com.study.daynode.note;

import com.study.daynode.base.ILoadView;
import com.study.daynode.base.IPresenter;
import com.study.daynode.bean.Note;
import java.util.List;

public interface NoteContract {
    interface View extends ILoadView {
        void responseNoteList(List<Note> noteList);
    }

    interface Presenter extends IPresenter {
        void getNoteList();
    }
}
