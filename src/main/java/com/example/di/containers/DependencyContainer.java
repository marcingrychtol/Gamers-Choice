package com.example.di.containers;

import java.util.HashMap;
import java.util.Map;

public abstract class DependencyContainer implements DependencyContained {
    Map<Class,Object> beans = new HashMap<>();

    public Map<Class, Object> getBeans() {
        return beans;
    }

    public void setBeans(Map<Class, Object> beans) {
        this.beans = beans;
    }
}
