package cn.gmuni.portal.dialect;

import org.springframework.core.env.Environment;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class GmDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "gmDialect";

    private static final String PREFIX = "gm";

    public static final int PROCESSOR_PRECEDENCE = 1000;

    private Environment env = null;


    protected GmDialect(Environment env) {
        super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
        this.env = env;
}

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new CssTagProcessor(dialectPrefix,env));
        return processors;
    }
}
