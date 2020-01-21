package util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import service.UserService;

import javax.servlet.SingleThreadModel;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator instance;
    private PageGenerator(){
        cfg = new Configuration();
    }

    public static PageGenerator getInstance(){
        if(instance == null){
            instance = new PageGenerator();
        }
        return instance;
    }

    private static final String HTML_DIR = "templates";

    private final Configuration cfg;


    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}