package aoc2023.day1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day1 {

  public static void main(String[] args) {
    try {
      part1();
      part2();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * --- Day 1: Trebuchet?! --- Something is wrong with global snow production, and you've been
   * selected to take a look. The Elves have even given you a map; on it, they've used stars to mark
   * the top fifty locations that are likely to be having problems.
   * <p>
   * You've been doing this long enough to know that to restore snow operations, you need to check
   * all fifty stars by December 25th.
   * <p>
   * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent
   * calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one
   * star. Good luck!
   * <p>
   * You try to ask why they can't just use a weather machine ("not powerful enough") and where
   * they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot
   * of questions") and hang on did you just say the sky ("of course, where do you think snow comes
   * from") when you realize that the Elves are already loading you into a trebuchet ("please hold
   * still, we need to strap you in").
   * <p>
   * As they're making the final adjustments, they discover that their calibration document (your
   * puzzle input) has been amended by a very young Elf who was apparently just excited to show off
   * her art skills. Consequently, the Elves are having trouble reading the values on the document.
   * <p>
   * The newly-improved calibration document consists of lines of text; each line originally
   * contained a specific calibration value that the Elves now need to recover. On each line, the
   * calibration value can be found by combining the first digit and the last digit (in that order)
   * to form a single two-digit number.
   * <p>
   * For example:
   * <p>
   * <pre>
   * 1abc2
   * pqr3stu8vwx
   * a1b2c3d4e5f
   * treb7uchet
   * </pre>
   * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding
   * these together produces 142.
   * <p>
   * Consider your entire calibration document. What is the sum of all of the calibration values?
   */
  static void part1() throws IOException {
    File file = new File("src/main/java/aoc2023/day1/input.txt");
    List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
    List<Integer> combines = new ArrayList<>();
    lines.forEach(l -> {
          String replaced = l.replaceAll("[^\\d]", "");
          String first = replaced.substring(0, 1);
          String last = replaced.substring(replaced.length() - 1);
          int combined = Integer.parseInt(first + last);
          combines.add(combined);
        }
    );
    System.out.println("Sum: " + combines.stream().mapToInt(Integer::intValue).sum());
  }

  /**
   * --- Part Two --- Your calculation isn't quite right. It looks like some of the digits are
   * actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine
   * also count as valid "digits".
   * <p>
   * Equipped with this new information, you now need to find the real first and last digit on each
   * line. For example:
   * <p>
   * <pre>
   * two1nine
   * eightwothree
   * abcone2threexyz
   * xtwone3four
   * 4nineeightseven2
   * zoneight234
   * 7pqrstsixteen
   * </pre>
   * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these
   * together produces 281.
   * <p>
   * What is the sum of all of the calibration values?
   */
  static void part2() throws IOException {
    File file = new File("src/main/java/aoc2023/day1/input.txt");
    List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
    List<Integer> combines = new ArrayList<>();
    lines.forEach(line -> {
      TreeMap<Integer, String> firstWordMap = buildFirstWordMap(line);
      TreeMap<Integer, String> lastWordMap = buildLastWordMap(line);
      String first = "";
      String last = "";
      if (firstWordMap.firstEntry() != null) {
        first = digitizeWordEntry(firstWordMap.firstEntry()).toString();
      }
      if (lastWordMap.lastEntry() != null) {
        last = digitizeWordEntry(lastWordMap.lastEntry()).toString();
      }
      Integer calibration = Integer.parseInt(first + last);
      combines.add(calibration);
    });
    System.out.println("Sum: " + combines.stream().mapToInt(Integer::intValue).sum());
  }

  private static final Map<String, Integer> wordMap = Map.of(
      "one", 1,
      "two", 2,
      "three", 3,
      "four", 4,
      "five", 5,
      "six", 6,
      "seven", 7,
      "eight", 8,
      "nine", 9,
      "zero", 0);

  private static Integer digitizeWordEntry(Map.Entry<Integer, String> wordEntry) {
    if (wordMap.containsKey(wordEntry.getValue())) {
      return wordMap.get(wordEntry.getValue());
    } else {
      return Integer.parseInt(wordEntry.getValue());
    }
  }

  private static final List<String> validWords = List.of("one", "two", "three", "four",
      "five", "six", "seven", "eight", "nine", "zero", "1", "2", "3", "4", "5", "6", "7", "8", "9",
      "0");

  private static TreeMap<Integer, String> buildFirstWordMap(String line) {
    TreeMap<Integer, String> wordMap = new TreeMap<>();
    validWords.forEach(word -> {
      int index = line.indexOf(word);
      if (index > -1) {
        wordMap.put(index, word);
      }
    });
    return wordMap;
  }

  private static TreeMap<Integer, String> buildLastWordMap(String line) {
    TreeMap<Integer, String> wordMap = new TreeMap<>();
    validWords.forEach(word -> {
      int index = line.lastIndexOf(word);
      if (index > -1) {
        wordMap.put(index, word);
      }
    });
    return wordMap;
  }

}
