package com.javapoke.app;

import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import com.apps.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.apps.util.Console.*;

public class JavaPokeApp implements SplashApp {
    private static final int maxLength = 12;
    private static final int maxNumOfPokemon = 4;
    private static final String pokemonData = "data/Pokemon Chart.csv";

    private final Map<Integer, Pokemon> pokemonMap = loadPokemonMap();
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Introduction intro = new Introduction(prompter);
    private Trainer player;

    /*
     * TODO: Create tests for prompts
     */

    public void beginChallenge() {
        intro.startUp();    // Completed
        chooseTrainer();    // Completed
        choosePokemon();    // Completed
        //startGame();
        gameOver();
    }

    @Override
    public void start() {
        beginChallenge();
    }

    private void gameOver() {
        clear();
        try {
            Files.readAllLines(Path.of("images/gameOver.txt")).forEach(System.out::println);
            Console.pause(3000);
            Files.readAllLines(Path.of("images/thank_you_message.txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        clear();
    }

    /*
     * This method will allow the user to choose from a Map of Pokémon and depending on their input
     * will add the Pokémon to their arsenal. Will need to make sure that the player may not choose
     * the same Pokémon on the list.
     */
    private void choosePokemon() {
        clear();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();      // changed to map instead of list
        try {
            Files.readAllLines(Path.of("images/PokemonChart.txt")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blankLines(1);
        for (int i = 0; i < maxNumOfPokemon; i++) {
            String pokemonPrompt = prompter.prompt("\t Input the Option # to select pokemon #" + (i + 1) + ": "
                    , "^[1-9]|10$", "\n\t\t This is not a valid option!\n");
            if (!trainerPokemon.containsValue(pokemonMap.get(Integer.parseInt(pokemonPrompt)))) {
                trainerPokemon.put(i + 1, pokemonMap.get(Integer.parseInt(pokemonPrompt)));   // from the change above
            } else {
                System.out.println("\n     Can not choose duplicate Pokemon for this challenge.\n");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        System.out.println(player);
    }

    private Map<Integer, Pokemon> loadPokemonMap()
    throws RuntimeException {
        Map<Integer, Pokemon> pokemonMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(pokemonData));

            for (String line : lines) {
                String[] tokens = line.split(",");
                if (tokens.length != 4) {
                    throw new RuntimeException("Invalid Line in CSV file " + line);
                }
                pokemonMap.put(Integer.valueOf(tokens[0]),
                        new Pokemon(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonMap;
    }

    private void chooseTrainer() {
        clear();

        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));

        String trainerPrompt = "images/trainer_selection.txt";
        try {
            Files.readAllLines(Path.of(trainerPrompt)).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player = trainers.get(0);
                break;
            case 2:
                player = trainers.get(1);
                break;
            case 3:
                player = trainers.get(2);
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player = new Trainer(characterName);
                break;
        }
        System.out.println(player);
    }
}