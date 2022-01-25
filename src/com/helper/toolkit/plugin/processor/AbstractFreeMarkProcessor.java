package com.helper.toolkit.plugin.processor;

import freemarker.template.Template;

import java.io.IOException;
import java.io.Writer;

public abstract class AbstractFreeMarkProcessor implements Processor {


    protected abstract Template getTemplate() throws IOException;

    protected abstract Object getModel(SourceNoteData sourceNoteData);

    protected abstract Writer getWriter(SourceNoteData sourceNoteData) throws Exception;


    @Override
    public final void process(SourceNoteData sourceNoteData) throws Exception {
        Template template = getTemplate();
        Object model = getModel(sourceNoteData);
        Writer writer = getWriter(sourceNoteData);
        template.process(model, writer);

    }
}
