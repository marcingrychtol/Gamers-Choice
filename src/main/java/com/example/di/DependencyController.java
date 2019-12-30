package com.example.di;

import com.example.di.containers.DependencyContainer;

public class DependencyController {
    public static DependencyContainer getDependencyContainer() {
        return dependencyContainer;
    }

    public static void setDependencyContainer(DependencyContainer dependencyContainer) {
        DependencyController.dependencyContainer = dependencyContainer;
    }

    public static DependencyContainer dependencyContainer;


}
