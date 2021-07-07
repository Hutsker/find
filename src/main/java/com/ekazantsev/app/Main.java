package com.ekazantsev.app;

import com.ekazantsev.check.Find;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            processCmdOption(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод, который обрабатывает командную строку
    private static void processCmdOption(String[] args) throws IOException {
        Find find = new Find();


        // создание ключей -io и -d ---------------------------------------------------------------------
        Options options = new Options();
        options.addOption("i", "input", true,
                "input .json file or directory containing .json files");

        // парсинг командной строки ---------------------------------------------------------------------
        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("plsqlParser", options);
        }

        // обработка ключей -----------------------------------------------------------------------------
        // если в командной строке есть -i
        if (cmd.hasOption("i")) {
            // если input - json файл
            if (cmd.getOptionValue("i").contains(".json")) {

                // запускаем проверку
                find.FindStatement(cmd.getOptionValue("i"));

            }

            // если input - папка (если input не содержит .json)
            if (!cmd.getOptionValue("i").contains(".json") &&  !cmd.getOptionValue("i").equals(cmd.getOptionValue("o"))) {

                File inputFolder = new File(cmd.getOptionValue("i"));

                find.processFolder(inputFolder);

            }
        }
    }
}