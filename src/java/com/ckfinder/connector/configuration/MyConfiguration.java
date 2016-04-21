package com.ckfinder.connector.configuration;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

public class MyConfiguration extends Configuration {

    public MyConfiguration(ServletConfig servletConfig) {
        super(servletConfig);
    }

    @Override
    protected Configuration createConfigurationInstance() {
        return new MyConfiguration(this.servletConf);
    }

    @Override
    public boolean checkAuthentication(HttpServletRequest request) {
        return true;
    }
}
