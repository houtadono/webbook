package com.example.webbook.Config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer{
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   
	  exposeDirectory("uploads", registry);
    }

	   public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("forward:/home");
       registry.addViewController("/home").setViewName("home");
   }
   public void exposeDirectory(String dirName,ResourceHandlerRegistry registry) {
	   Path uploadDir = Paths.get(dirName);
	   String uploadPath = uploadDir.toFile().getAbsolutePath();
	   if (dirName.startsWith("../")) {
		   dirName = dirName.replace("../", "");
	   }
	   registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/"+uploadPath +"/");
   }
}
