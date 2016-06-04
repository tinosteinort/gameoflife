package gol.base;

import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tino on 14.05.2016.
 */
public class SpringBootstrap {

    public ClassPathXmlApplicationContext bootstrap(final Stage stage) {

//        final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        registerStage(beanFactory, stage);

//        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationCtx.xml");
//        ctx.addBeanFactoryPostProcessor(new BeanDefinitionRegistryPostProcessor() {
//            @Override
//            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
////                registerStage(registry, stage);
//            }
//
//            @Override
//            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//                registerStage(beanFactory, stage);
//            }
//        });

//        context.register(GameOfLifeConfig.class);
//        context.register(WebstartConfig.class);
//        context.refresh();

        return ctx;
//
//        final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        registerStage(beanFactory, stage);
//
//        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);
//        context.register(GameOfLifeConfig.class);
//        context.register(WebstartConfig.class);
//        context.refresh();
//
//        return context;
    }

    private void registerStage(final DefaultListableBeanFactory beanFactory, final Stage primaryStage) {
        final AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
        final BeanDefinition stageBeanDef = createStageBeanDefinition(primaryStage);
        beanFactory.registerBeanDefinition(nameGenerator.generateBeanName(stageBeanDef, beanFactory), stageBeanDef);
    }

    private BeanDefinition createStageBeanDefinition(final Stage stage) {
        final ConstructorArgumentValues arguments = new ConstructorArgumentValues();
        arguments.addGenericArgumentValue(stage, Stage.class.getName());

        return new RootBeanDefinition(StageFactoryBean.class, arguments, null);
    }
}
