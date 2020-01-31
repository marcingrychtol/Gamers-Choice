package com.example.di.containers;

public interface DependencyContaining {

    <T> T get(Class<T> klass);

}
