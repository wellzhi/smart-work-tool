package com.helper.toolkit.plugin.processor;

import com.helper.toolkit.plugin.data.NoteData;

import java.util.List;

public interface SourceNoteData {
    String getFileName();

    String getTopic();

    List<NoteData> getNoteList();


}
