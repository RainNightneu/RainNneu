package com.study.daynode.diary;

import com.study.daynode.base.ILoadView;
import com.study.daynode.base.IPresenter;
import com.study.daynode.bean.Note;

/**
 * Created by wuxiaojian on 16/12/5.
 */
public interface DiaryContract {
    interface DiaryView extends ILoadView {
        void saveStatus();

        void responseNoteDetail(Note note);


    }

    interface DiaryPresenter extends IPresenter {
        void insertNote(String noteTitle, String noteContent, long creatTime, int weatherPosition,
            String location);

        void updateNote(Long id, String noteTitle, String noteContent, long creatTime,
            int weatherPosition, String location);

        void getNote(long noteId);

        boolean getNoteByData(String data);

        void delNote(long noteId);
    }
}
