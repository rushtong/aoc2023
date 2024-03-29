package aoc2023.day2;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

  public static void main(String[] args) {
    try {
      part1();
      part2();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * --- Day 2: Cube Conundrum ---
   * <p>
   * You're launched high into the atmosphere! The apex of your trajectory just barely reaches the
   * surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's
   * quite cold, but you don't see much snow. An Elf runs over to greet you.
   * <p>
   * The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll
   * be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't
   * get many visitors up here; would you like to play a game in the meantime?
   * <p>
   * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue.
   * Each time you play this game, he will hide a secret number of cubes of each color in the bag,
   * and your goal is to figure out information about the number of cubes.
   * <p>
   * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag,
   * grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do
   * this a few times per game.
   * <p>
   * You play several games and record the information from each game (your puzzle input). Each game
   * is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated
   * list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
   * <p>
   * For example, the record of a few games might look like this:
   * <pre>
   * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
   * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
   * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
   * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
   * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
   * </pre>
   * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first
   * set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue
   * cubes; the third set is only 2 green cubes.
   * <p>
   * The Elf would first like to know which games would have been possible if the bag contained only
   * 12 red cubes, 13 green cubes, and 14 blue cubes?
   * <p>
   * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded
   * with that configuration. However, game 3 would have been impossible because at one point the
   * Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because
   * the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have
   * been possible, you get 8.
   * <p>
   * Determine which games would have been possible if the bag had been loaded with only 12 red
   * cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
   */
  static void part1() throws IOException {
    Gson gson = new Gson();
    List<String> lines = getLines();
    List<Game> validGames = new ArrayList<>();
    lines.forEach(line -> {
      Game game = parseGame(line);
      if (game.getBlueCubes() <= 14 && game.getGreenCubes() <= 13 && game.getRedCubes() <= 12) {
        validGames.add(game);
      }
    });
    var validGameSum = validGames.stream().map(Game::getGameNumber).mapToInt(Integer::intValue).sum();
    System.out.println("Sum: " + validGameSum);
  }

  /**
   * --- Part Two --- The Elf says they've stopped producing snow because they aren't getting any
   * water! He isn't sure why the water stopped; however, he can show you how to get to the water
   * source to check it out for yourself. It's just up ahead!
   * <p>
   * As you continue your walk, the Elf poses a second question: in each game you played, what is
   * the fewest number of cubes of each color that could have been in the bag to make the game
   * possible?
   * <p>
   * Again consider the example games from earlier:
   * <pre>
   * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
   * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
   * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
   * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
   * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
   * </pre>
   * <ul>
   * <li>In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes.
   *    If any color had even one fewer cube, the game would have been impossible.</li>
   * <li>Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.</li>
   * <li>Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.</li>
   * <li>Game 4 required at least 14 red, 3 green, and 15 blue * cubes.</li>
   * <li>Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.</li>
   * </ul>
   * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together.
   * The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
   * <p>
   * For each game, find the minimum set of cubes that must have been present. What is the sum of
   * the power of these sets?
   */
  static void part2() throws IOException {
    List<String> lines = getLines();
    List<Game> games = lines.stream().map(Day2::parseGame).toList();
    var gamePowerSum = games.stream().map(Game::getPower).mapToInt(Integer::intValue).sum();
    System.out.println("Sum: " + gamePowerSum);
  }

  private static Game parseGame(String line) {
    Game game = new Game();
    Pattern gameRegex = Pattern.compile("Game (\\d+): (.*)");
    Matcher gameNumberMatcher = gameRegex.matcher(line);
    gameNumberMatcher.find();
    game.setGameNumber(Integer.parseInt(gameNumberMatcher.group(1)));
    Pattern cubeRegex = Pattern.compile("(\\d+) (\\w+)");
    Matcher cubeMatcher = cubeRegex.matcher(line);
    while (cubeMatcher.find()) {
      game.bumpCount(cubeMatcher.group(2), Integer.parseInt(cubeMatcher.group(1)));
    }
    return game;
  }

  private static List<String> getLines() throws IOException {
    File file = new File("src/main/java/aoc2023/day2/input.txt");
    return Files.readAllLines(Paths.get(file.getAbsolutePath()));
  }

}
