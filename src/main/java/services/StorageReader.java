package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.StorageConfig;

import java.io.File;
import java.io.IOException;

public class StorageReader {
    private static StorageReader storageReader;

    private StorageReader(){
        StorageConfig storageConfig = readStorage();
        DatabaseManager.getDatabaseManager().addStartingProducts(storageConfig);
    }

    public static StorageReader getStorageReader() {
        if(storageReader == null){
            storageReader = new StorageReader();
        }
        return storageReader;
    }

    public StorageConfig readStorage() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("src/main/resources/storage.json"), StorageConfig.class);
        }
        catch(IOException e){
            System.out.println("    Error at readStorage    ");
            return null;
        }
    }
}

