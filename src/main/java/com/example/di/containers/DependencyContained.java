package com.example.di.containers;

public interface DependencyContained {

    <T> T get(Class<T> klass);

}
