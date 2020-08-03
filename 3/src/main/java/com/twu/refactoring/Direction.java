package com.twu.refactoring;

import java.util.Arrays;
import java.util.List;

public class Direction {
    private final char direction;
    private final static List<Character> scope = Arrays.asList('E', 'S', 'W', 'N');

    public Direction(char direction) {
        this.direction = direction;
    }

    public Direction turnRight() {
        return turn(1);
    }

    public Direction turnLeft() {
        return turn(scope.size() - 1);
    }

    private Direction turn(int number) {
        if (scope.contains(direction)) {
            int index = (scope.indexOf(direction) + number) % scope.size();
            return new Direction(scope.get(index));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direction direction1 = (Direction) o;

        if (direction != direction1.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) direction;
    }

    @Override
    public String toString() {
        return "Direction{direction=" + direction + '}';
    }
}
