package io.pivotal.poc.du.loyalty.s4;

import io.pivotal.poc.du.loyalty.s4.domain.JpaConfiguration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{JpaConfiguration.class,RestConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
