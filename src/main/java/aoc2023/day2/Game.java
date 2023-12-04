package aoc2023.day2;

public class Game {
  Integer gameNumber;
  Integer redCubes = 0;
  Integer greenCubes = 0;
  Integer blueCubes = 0;

  public Integer getGameNumber() {
    return gameNumber;
  }

  public Integer getPower() {
    return getRedCubes() * getGreenCubes() * getBlueCubes();
  }

  public void bumpCount(String color, Integer count) {
    switch (color) {
      case "red" -> {
        if (count > getRedCubes()) {
          setRedCubes(count);
        }
      }
      case "green" -> {
        if (count > getGreenCubes()) {
          setGreenCubes(count);
        }
      }
      case "blue" -> {
        if (count > getBlueCubes()) {
          setBlueCubes(count);
        }
      }
    }
  }

  public void setGameNumber(Integer gameNumber) {
    this.gameNumber = gameNumber;
  }

  public Integer getRedCubes() {
    return redCubes;
  }

  public void setRedCubes(Integer redCubes) {
    this.redCubes = redCubes;
  }

  public Integer getGreenCubes() {
    return greenCubes;
  }

  public void setGreenCubes(Integer greenCubes) {
    this.greenCubes = greenCubes;
  }

  public Integer getBlueCubes() {
    return blueCubes;
  }

  public void setBlueCubes(Integer blueCubes) {
    this.blueCubes = blueCubes;
  }
}
