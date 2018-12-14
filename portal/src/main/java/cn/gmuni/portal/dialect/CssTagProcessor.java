package cn.gmuni.portal.dialect;

import org.springframework.core.env.Environment;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import org.thymeleaf.templatemode.TemplateMode;

public class CssTagProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "href";
    private static final int PRECEDENCE = 10000;

    //运行环境
    private String active = null;


    protected CssTagProcessor(String dialectPrefix, Environment env) {

        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                "link",              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's own precedence)
                true);             // Remove the matched attribute afterwards
        this.active = env.getProperty("spring.profiles.active");
    }


    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName, String attributeValue,
                             IElementTagStructureHandler handler) {
        if("dev".equals(active)){
            handler.setAttribute("href", "/static/less/" +attributeValue+".less");
            handler.setAttribute("rel","stylesheet/less");
        }else{
            handler.setAttribute("href", "/static/css/" +attributeValue+".css");
            handler.setAttribute("rel","stylesheet");
        }


    }
}