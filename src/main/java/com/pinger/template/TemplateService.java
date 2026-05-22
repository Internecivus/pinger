package com.pinger.template;

import com.pinger.messaging.LabelsProvider;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateService {

    private static final Configuration configuration;

    static {
        try {
            configuration = new Configuration();
            configuration.setClassForTemplateLoading(TemplateService.class, "/templates");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setSharedVariable("labels", LabelsProvider.getInstance().getResources());
        }
        catch (TemplateModelException e) {
            throw new RuntimeException(e);
        }
    }

	public static String process(final Template templateFile, final Map<String, ?> model) {
        try {
            final freemarker.template.Template template = configuration.getTemplate(templateFile.getFileName());
            final Writer out = new StringWriter();
            template.process(model, out);
            return out.toString();
        }
		catch (IOException | TemplateException e) {
		    throw new RuntimeException(e);
        }
	}

}
