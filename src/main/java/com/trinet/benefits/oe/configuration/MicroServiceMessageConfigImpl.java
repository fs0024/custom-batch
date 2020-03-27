package com.trinet.benefits.oe.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.trinet.common.MicroServiceMessageConfig;

@Component
public class MicroServiceMessageConfigImpl implements MicroServiceMessageConfig{

    private static final Logger LOGGER = LogManager.getLogger(MicroServiceMessageConfigImpl.class);

    @Autowired
    private Environment environment;

    @Override
    public String getProperty(String key) {

        LOGGER.debug(() -> String.format("Getting property  : %s", key));
        return environment.getProperty(key);
    }

}