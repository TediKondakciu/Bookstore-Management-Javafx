package application.bookstore.models;

import application.bookstore.auxiliaries.CustomObjectOutputStream;
import application.bookstore.auxiliaries.FileHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class BaseModel {
    public abstract boolean saveInFile();
    public abstract boolean updateFile();

    public boolean save(File dataFile) {
        if (!isValid())
            return false;
        try {
            ObjectOutputStream outputStream;
            FileOutputStream fileOutputStream = new FileOutputStream(dataFile, true);
            if (dataFile.length() == 0)
                outputStream = new ObjectOutputStream(fileOutputStream);
            else
                outputStream = new CustomObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public abstract boolean isValid();
    public abstract boolean deleteFromFile();


}
