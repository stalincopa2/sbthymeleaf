package net.osgg.crudthymeleaf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig implements WebMvcConfigurer  {

	@Value("${upload.path}")
	public String uploadDir;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    String myExternalFilePath = "file:"+uploadDir+"/";
	    registry.addResourceHandler("/pic/**").addResourceLocations(myExternalFilePath);
	}

}
