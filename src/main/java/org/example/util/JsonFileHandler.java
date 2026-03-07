package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {

    private final ObjectMapper objectMapper;
    private final String dataDirectory;

    public JsonFileHandler(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    public <T> void saveList(String fileName, List<T> list) {
        File directory = new File(dataDirectory);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File file = new File(dataDirectory, fileName);
            objectMapper.writeValue(file, list);
            System.out.println("You have successfully saved the data.");
        } catch (IOException e) {
            System.out.println("The data could not be saved: " + e.getMessage());
        }
    }

    public <T> List<T> loadList(String fileName, Class<T> typeElement) {
        File file = new File(dataDirectory, fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, typeElement));
        } catch (IOException e) {
            System.out.println("The data could not be loaded: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
