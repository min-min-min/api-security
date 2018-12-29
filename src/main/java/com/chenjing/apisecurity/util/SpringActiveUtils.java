package com.chenjing.apisecurity.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Chenjing
 * @date 2018/12/29
 */
@Component
public class SpringActiveUtils {

    @Autowired
    private Environment environment;

    public boolean isIgnoredProfiles(List<String> excludeProfiles) {
        return isContains(excludeProfiles);
    }

    public boolean isNeedEncrypt(List<String> excludeProfiles) {
        return !isContains(excludeProfiles);
    }

    public boolean isNeedDecrypt(List<String> excludeProfiles) {
        return !isContains(excludeProfiles);
    }

    private boolean isContains(List<String> excludeProfiles) {
        if (excludeProfiles != null && !excludeProfiles.isEmpty()) {
            return environment.acceptsProfiles(Profiles.of(excludeProfiles.toArray(new String[0])));
        } else {
            return false;
        }
    }
}
