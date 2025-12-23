package sumdu.edu.ua.config;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class EmailFreemarkerConfig {

    @Bean
    @Qualifier("emailFreemarkerConfig")
    public Configuration freemarkerEmailConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

        cfg.setClassLoaderForTemplateLoading(
                getClass().getClassLoader(),
                "/mail-templates/"
        );
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

}

