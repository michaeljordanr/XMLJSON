package com.example.michael.xmljson;


import java.io.IOException;
import java.util.List;

public interface DataHandler {

    public enum Format{
        XML, JSON
    }

    public String convertToString(Data data) throws IOException;

    public List<Integer> extractNumbers(String string) throws IOException;
}
