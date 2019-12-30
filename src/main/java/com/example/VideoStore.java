package com.example;

import com.example.di.DependencyController;
import com.example.di.containers.impl.LocalDependencyContainer;
import com.example.storage.impl.LocalGameStorage;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;

public class VideoStore extends NanoHTTPD {

    RequestUrlMapper requestUrlMapper = new RequestUrlMapper();

    public VideoStore(int port) throws IOException {
        super(port);
        start(5000,false);
        System.out.println("Server has started!");
    }

    public static void main(String[] args )
    {

        DependencyController.setDependencyContainer(new LocalDependencyContainer());
        try {
            VideoStore videoStore = new VideoStore(8081);
        } catch (IOException e) {
            System.err.println("Error while starting server: " + e.getMessage());
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        return requestUrlMapper.delegateRequest(session);
    }
}
