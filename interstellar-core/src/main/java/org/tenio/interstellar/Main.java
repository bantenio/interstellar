package org.tenio.interstellar;

import cn.hutool.core.io.FileUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    static {
        cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
    }

    public static void main(String[] args) throws IOException, TemplateException {
        ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(Main.class, "/template/");
        cfg.setTemplateLoader(classTemplateLoader);
        Template template;

        template = cfg.getTemplate("ConsumerFunctionalInterface.ftl");
        generateConsumer(template);

        template = cfg.getTemplate("FunctionFunctionalInterface.ftl");
        generateFunction(template);
    }

    public static void generateConsumer(Template template) throws TemplateException, IOException {
        StringWriter stringWriter = null;
        Map<String, Object> data = new LinkedHashMap<>();
        for (int idx = 1; idx < 13; idx++) {
            data.put("size", idx);
            stringWriter = new StringWriter();
            template.process(data, stringWriter);
            FileUtil.appendString(stringWriter.toString(), "Consumer" + idx + ".java", StandardCharsets.UTF_8);
        }
    }

    public static void generateFunction(Template template) throws TemplateException, IOException {
        StringWriter stringWriter = null;
        Map<String, Object> data = new LinkedHashMap<>();
        for (int idx = 1; idx < 13; idx++) {
            data.put("size", idx);
            stringWriter = new StringWriter();
            template.process(data, stringWriter);
            FileUtil.appendString(stringWriter.toString(), "Function" + idx + ".java", StandardCharsets.UTF_8);
        }
    }
}
