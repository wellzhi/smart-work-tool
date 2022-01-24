package com.liuxz.smart.plugin.processor;

import com.liuxz.smart.plugin.data.NoteData;
// import icu.jogeen.markbook.data.NoteData;

import java.util.List;

public interface SourceNoteData {
    String getFileName();

    String getTopic();

    List<NoteData> getNoteList();


}
