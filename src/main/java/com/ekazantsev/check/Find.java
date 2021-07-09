package com.ekazantsev.check;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gson.*;

public class Find
{
    public void processFolder(File folder, String configFile) {
        File[] folderEntries = folder.listFiles();

        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                processFolder(entry, configFile);
                continue;
            }

            System.out.println(entry.getPath());

            // строим дерево и сохраняем его
            FindStatement(entry.getPath(), configFile);

        }
    }
    List<String> objects = new ArrayList<>();
    String cur_object = "";
    List<String> statements = new ArrayList<>();

    public void FindStatement(String inputFile, String configFile)

    {
        objects.add("procedure");
        objects.add("function");
        objects.add("trigger");
        //statements.add("commit_statement");
        //statements.add("execute_immediate");
        try
        {
            statements = Files.readAllLines(Paths.get(configFile), Charset.forName("Windows-1251"));
            for(int i = 0; i<statements.size(); i++)
                statements.set(i,statements.get(i).trim().toLowerCase());
            FileReader reader = new FileReader(inputFile);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(reader);
            FindRecurse(jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FindRecurse(JsonElement jsonElement)
    {
        if(jsonElement.isJsonObject())
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            java.util.Iterator iterator = jsonObject.keySet().iterator();
            while(iterator.hasNext())
            {
                Object it = iterator.next();
                for(String s : objects)
                    if(it.toString().contains(s+"_body"))
                    {
                        JsonArray ja = jsonObject.get(it.toString()).getAsJsonArray();
                        cur_object=ja.get(0).getAsJsonObject().get("text").toString().substring(1,ja.get(0).getAsJsonObject().get("text").toString().length()-1)+" "+ja.get(1).getAsJsonObject().get("identifier").getAsJsonArray().get(0).getAsJsonObject().get("id_expression").getAsJsonArray().get(0).getAsJsonObject().get("regular_id").getAsJsonArray().get(0).getAsJsonObject().get("text").toString().substring(1, ja.get(1).getAsJsonObject().get("identifier").getAsJsonArray().get(0).getAsJsonObject().get("id_expression").getAsJsonArray().get(0).getAsJsonObject().get("regular_id").getAsJsonArray().get(0).getAsJsonObject().get("text").toString().length()-1)+" ";
                    }
                boolean f = false;
                for(String s : statements)
                    if(it.toString().contains(s) && !f)
                    {
                        //System.out.println(it.toString());
                        JsonArray ja = jsonObject.get(it.toString()).getAsJsonArray();
                        System.out.println(cur_object + "Line " + ja.get(0).getAsJsonObject().get("line").toString() + " " +it.toString());
                        f=true;
                    }
                if(!f)
                {
                    FindRecurse(jsonObject.get(it.toString()));
                }
            }
        }
        else if (jsonElement.isJsonArray())
        {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement je : jsonArray)
            {
                FindRecurse(je);
            }
        }
    }
}
