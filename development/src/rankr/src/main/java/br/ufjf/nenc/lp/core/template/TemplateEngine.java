package br.ufjf.nenc.lp.core.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class TemplateEngine {

    private static final String ERR_MSG = "Failure compiling template:%s to %s with scope %s";

    public void compile(String templatePath, String destination, Object scope)
            throws TemplateEngineException {
        try {
            doCompile(templatePath, destination, scope);
        } catch (Exception e) {
            String eMsg = String.format(ERR_MSG, templatePath, destination, scope);
            throw new TemplateEngineException(eMsg, e);
        }
    }

    private void doCompile(String templatePath, String destination, Object scope)
            throws Exception {

        OutputStream out = new FileOutputStream(destination);
        FileReader reader = new FileReader(templatePath);

        Writer writer = new OutputStreamWriter(out);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(reader, templatePath);
        mustache.execute(writer, scope);

        writer.flush();
    }

    public void compile(Reader templateReader, String destination, Object scope)
            throws TemplateEngineException {
        try {
            doCompile(templateReader, destination, scope);
        } catch (Exception e) {
            String eMsg = String.format(ERR_MSG, templateReader, destination, scope);
            throw new TemplateEngineException(eMsg, e);
        }
    }

    private void  doCompile(Reader templateReader, String destination, Object scope)
            throws Exception {

        OutputStream out = new FileOutputStream(destination);

        Writer writer = new OutputStreamWriter(out);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(templateReader, destination);
        mustache.execute(writer, scope);

        writer.flush();
    }


}
