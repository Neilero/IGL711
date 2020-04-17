package ca.usherbrooke.dinf.dbinterface.controllers;

import java.io.Serializable;

public class SimpleResponse implements Serializable {
    public boolean requestDone;
    public String  message;

    public SimpleResponse( boolean requestDone, String message ) {
        this.requestDone = requestDone;
        this.message = message;
    }
}
