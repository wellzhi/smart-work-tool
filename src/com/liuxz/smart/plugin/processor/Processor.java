package com.liuxz.smart.plugin.processor;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface Processor {
    void process(SourceNoteData sourceNoteData) throws IOException, TemplateException, Exception;
}
