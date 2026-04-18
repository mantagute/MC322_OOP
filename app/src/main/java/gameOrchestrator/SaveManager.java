package gameOrchestrator;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class SaveManager {

    private static final String SAVE_FILE = "save.json";

    public static boolean isThereAnySave() {
        return new File(SAVE_FILE).exists();
    }

    public static void saveGame(SaveState saveState) {
        Gson gson = new Gson();
        String json = gson.toJson(saveState);
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    public static SaveState loadGame() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(SAVE_FILE)){
            return gson.fromJson(reader, SaveState.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
