package org.tenio.interstellar.example.module;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.context.spring.JsonNodeAccessor;
import org.tenio.interstellar.example.vo.UserVO;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.util.Map;

public class JsonNodeAccessorTestModule implements ApplicationRunner, ApplicationContextAware {
    private static final MapAccessor MAP_ACCESSOR = new MapAccessor();
    private static final EnvironmentAccessor ENVIRONMENT_ACCESSOR = new EnvironmentAccessor();
    private static final JsonNodeAccessor JSON_NODE_ACCESSOR = new JsonNodeAccessor();
    private ApplicationContext applicationContext;

    private final SpelExpressionParser parser;

    public JsonNodeAccessorTestModule(SpelExpressionParser parser) {
        this.parser = parser;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String json = """
                {
                    "user": {
                        "name": "sunkaihan",
                        "age": 36
                    },
                    "id": [1, 2, 3]
                }
                """;
        Map<String, Object> context = JsonUtils.fromJsonToMap(json);
        testGroovy(context);
    }

    protected void testGroovy(Map<String, Object> context) {
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse("""
            id = (context.id as List)
            user = (context.user as Map)
            println(context.user1?.name)
            println(context.user?.name)
            println(id[2])
            println(id[3])
            context.id << 4
            println(id[3])
            id.remove(3)
            user.remove('age')
            println(context)
""");
        script.setProperty("context", context);
        script.run();
    }

    protected void testSPEL(Map<String, Object> context) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
        evaluationContext.addPropertyAccessor(MAP_ACCESSOR);
        evaluationContext.addPropertyAccessor(ENVIRONMENT_ACCESSOR);
        evaluationContext.addPropertyAccessor(JSON_NODE_ACCESSOR);
        evaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));

        Expression expression = parser.parseExpression("id[0]");
        expression.setValue(evaluationContext, 4);

        Expression expression1 = parser.parseExpression("user");
        expression1.setValue(evaluationContext, new UserVO().setName("Tenio").setAge(3));

        Expression expression2 = parser.parseExpression("id");
        System.out.println(expression2.getValue(evaluationContext).getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
