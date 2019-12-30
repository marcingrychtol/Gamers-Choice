package com.example.di.containers.impl;

import com.example.di.containers.DependencyContainer;
import com.example.storage.impl.LocalGameStorage;

public class LocalDependencyContainer extends DependencyContainer {

    public LocalDependencyContainer(){
        super.getBeans().put(LocalGameStorage.class,new LocalGameStorage());
    }

    @Override
    public <T> T get(Class<T> klass) {
        return (T) super.getBeans().get(klass);
    }
}
