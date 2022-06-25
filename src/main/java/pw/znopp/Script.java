package pw.znopp;

import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Script {

    public static void main(String[] args) {
        System.out.println("i did not destroy the json");

        String version = askForInput("Please enter a valid Minecraft version", "My disappointment is immeasurable, and my day is ruined.", "version");

        String oldFile = askForInput("Please enter the file name of the old translation file", "Invalid file!", "file");
        String newFile = askForInput("Please enter the file name of the new translation file", "Invalid file!", "file");

        if (oldFile == null || newFile == null) return;

        try {
            int i = 0;
            JSONObject oldJson = reader(oldFile);
            LinkedHashMap<String, String> olds = convertJsonToHashMap(oldJson);

            JSONObject youngJson = reader(newFile);
            LinkedHashMap<String, String> youngs = convertJsonToHashMap(youngJson);

            // Deleted translations
            JSONObject deletedTranslations = new JSONObject();
            for (Map.Entry<String, String> old : olds.entrySet()) {
                String key = old.getKey();
                String value = old.getValue();

                // Check if the new translations contains old key
                if (!youngs.containsKey(key)) {
                    i++;
                    deletedTranslations.put(key, value);
                }
            }

            writeToFile("deleted-entries-" + version + "-" + System.currentTimeMillis() + ".json", deletedTranslations.toString(4));
            System.out.println("Deleted translations: " + i);

            // Added translations
            i = 0;
            JSONObject newTranslations = new JSONObject(oldJson.toString());
            System.out.println("Starts on L#" + (newTranslations.length() + 6));
            newTranslations.put("_COMMENT", version + " ENTRIES START HERE!");
            for (Map.Entry<String, String> young : youngs.entrySet()) {
                String key = young.getKey();
                String value = young.getValue();

                // Check if the new translations contains old key
                if (!olds.containsKey(key)) {
                    i++;
                    newTranslations.put(key, value);
                }
            }

            writeToFile("new-entries-" + version + "-" + System.currentTimeMillis() + ".json", newTranslations.toString(4).replaceAll("\"_COMMENT", "\n\n\n\n\s\s\s\s\"_COMMENT"));
            System.out.println("");
            System.out.println("New translations: " + i);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final Pattern pattern = Pattern.compile("1\\.([12][0-9]|[0-9])(\\.[1-9])?");

    private static String askForInput(String message, String invalidMessage, String type) {
        Scanner scanner = new Scanner(System.in);

        // Print message
        System.out.println(message);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            Matcher matcher = pattern.matcher(input);

            if (type == null) {
                return input;
            }

            switch (type) {

                // Special case for Minecraft versions
                case "version" -> {
                    if (matcher.find()) {
                        String found = matcher.group();
                        if (found.equalsIgnoreCase(input)) {
                            System.out.println("Using Minecraft version: " + found);
                            return found;
                        }
                    }
                }

                // Special case for files
                case "file" -> {
                    if (!input.endsWith(".json")) input += ".json";
                    if (new File(input).exists()) {
                        return input;
                    }
                }
            }

            System.out.println(invalidMessage);
        }

        return null;
    }

    private static void writeToFile(String fileName, String content) throws Exception {
        FileWriter fw = new FileWriter(fileName);
        fw.write(content);
        fw.close();
    }


    private static LinkedHashMap<String, String> convertJsonToHashMap(JSONObject json) {
        LinkedHashMap<String, String> keyValues = new LinkedHashMap<>();

        for (Iterator<String> it = json.keys(); it.hasNext(); ) {
            String key = it.next();
            String value = json.get(key).toString();

            keyValues.put(key, value);
        }

        return keyValues;
    }

    private static JSONObject reader(String fileName) throws NullPointerException, IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("bruh, file doesn't exist oh boi");
            throw new NullPointerException("the file " + fileName + " does not exist");
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("bruh :" + exception.getMessage());
            throw exception;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line);
        }

        // JSONArray JSONObject
        JSONObject jsonBlop;
        try {
            jsonBlop = new JSONObject(stringBuilder.toString());
        } catch (JSONException exception) {
            System.out.println("JSOn very bruh: " + exception.getMessage());
            throw exception;
        }

        return jsonBlop;
    }
}