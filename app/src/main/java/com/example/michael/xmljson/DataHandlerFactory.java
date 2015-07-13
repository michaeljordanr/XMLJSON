package com.example.michael.xmljson;


public class DataHandlerFactory {

    public static DataHandler newDataHandler(DataHandler.Format format) {

        if (format == DataHandler.Format.XML) {
            return new XMLDataHandler();
        } else if (format == DataHandler.Format.JSON) {
            return new JSONDataHandler();
        }

        return null;
    }
}
