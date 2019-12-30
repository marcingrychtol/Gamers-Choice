package com.example.di.containers.impl;

import com.example.di.containers.DependencyContainer;
import com.example.storage.impl.PostgresGameStorage;

public class PostgresDependencyContainer extends DependencyContainer {

    public PostgresDependencyContainer(){
        super.getBeans().put(PostgresGameStorage.class,new PostgresGameStorage());
    }
    @Override
    public <T> T get(Class<T> klass) {
        return (T) super.getBeans().get(klass);
    }
}
