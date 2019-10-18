package com.linkallcloud.um.web.oapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.linkallcloud.web.interceptors.LacEnvInterceptor;
import com.linkallcloud.web.support.AppVisitorArgumentResolver;
import com.linkallcloud.web.support.TraceArgumentResolver;
import com.linkallcloud.um.web.oapi.aop.LacPermissionInterceptor;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.linkallcloud.um.web.oapi" })
public class UmOapiApplication implements WebMvcConfigurer {

	@Value("${lac.static.server}")
	private String staticServer;
	@Value("${lac.static.res.version}")
	private String staticResourceVersion;

	@Bean
	public LacEnvInterceptor getLacEnvInterceptor() {
		return new LacEnvInterceptor() {

			@Override
			protected String getStaticServer() {
				return staticServer;
			}

			@Override
			protected String getResourceVersion() {
				return staticResourceVersion;
			}

		};
	}

	@Bean
	@Order(1)
	public LacPermissionInterceptor getLacPermissionInterceptor() {
		return new LacPermissionInterceptor();// login, home + "pub/noPermission"
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration envpi = registry.addInterceptor(getLacEnvInterceptor());
		envpi.excludePathPatterns("/js/**", "/css/**", "/images/**", "/img/**", "/static/**");
		envpi.addPathPatterns("/**");

		InterceptorRegistration pi = registry.addInterceptor(getLacPermissionInterceptor());
		pi.excludePathPatterns("/js/**", "/css/**", "/images/**", "/img/**", "/static/**", "/login/**", "/verifyCode",
				"/exit", "/unsupport", "/error", "/pub/**", "/face/**");
		pi.addPathPatterns("/**");

		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AppVisitorArgumentResolver());
		resolvers.add(new TraceArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConverter);
	}

	public static void main(String[] args) {
		SpringApplication.run(UmOapiApplication.class, args);
	}

}
