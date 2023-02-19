import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    public static Stream<String> argsProviderFactory() {
        return Stream.of("", " ", " ");
    }
    @Test
    void horseWithoutNameException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 20));
    }
    @Test
    void horseWithoutNameExceptionMessage() {
        try {
            new Horse(null, 20);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be null.", e.getMessage());
        }
    }
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void horseWithIncorrectNameException(String wrongName) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(wrongName, 20));
    }
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void horseWithIncorrectNameExceptionMessage(String wrongName) {
        try {
            new Horse(wrongName, 20);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be blank.", e.getMessage());
        }
    }
    @Test
    void horseWithNegativeSpeedException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("Horse", -20));
        Assertions.assertEquals("Speed cannot be negative.", thrown.getMessage());
    }
    @Test
    void horseWithNegativeDistanceException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("Horse", 20, -20));
        Assertions.assertEquals("Distance cannot be negative.", thrown.getMessage());
    }
    @Test
    void getName() {
        String horseName = "Horse";
        int horseSpeed = 20;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseName, horse.getName());
    }
    @Test
    void getSpeed() {
        String horseName = "Horse";
        int horseSpeed = 20;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseSpeed, horse.getSpeed());
    }
    @Test
    void getDistanceWithoutSettingDistance() {
        String horseName = "Horse";
        int horseSpeed = 20;
        int horseDistance = 0;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseDistance, horse.getDistance());
    }
    @Test
    void getDistanceWithSettingDistance() {
        String horseName = "Horse";
        int horseSpeed = 20;
        int horseDistance = 0;
        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        Assertions.assertEquals(horseDistance, horse.getDistance());
    }
    @Test
    void getRandomDoubleFromMoveMethod() {
        try (MockedStatic<Horse> horseClass = Mockito.mockStatic(Horse.class)) {
            Horse horse = new  Horse("Horse", 20);
            horse.move();
            horseClass.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.55})
    void setCorrectDistanceInMoveMethod(double argument) {
        try (MockedStatic<Horse> horseClass = Mockito.mockStatic(Horse.class)) {
            horseClass.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(argument);
            Horse horse = new  Horse("Horse", 20);
            horse.move();
            Assertions.assertEquals(11, horse.getDistance());
        }
    }
}
