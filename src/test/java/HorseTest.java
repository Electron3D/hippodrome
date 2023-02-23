import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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
    void constructor_nullNameProvided_exceptionExpected() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 20));
    }
    @Test
    void constructor_nullNameProvided_correctExceptionMessageExpected() {
        try {
            new Horse(null, 20);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be null.", e.getMessage());
        }
    }
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void constructor_incorrectNameProvided_exceptionExpected(String wrongName) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(wrongName, 20));
    }
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void constructor_incorrectNameProvided_correctExceptionMessageExpected(String wrongName) {
        try {
            new Horse(wrongName, 20);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be blank.", e.getMessage());
        }
    }
    @Test
    void constructor_negativeSpeedProvided_exceptionExpected() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("Horse", -20));
    }
    @Test
    void constructor_negativeSpeedProvided_correctExceptionMessageExpected() {
        try {
            new Horse("Horse", -20);
        } catch (IllegalArgumentException thrown) {
            Assertions.assertEquals("Speed cannot be negative.", thrown.getMessage());
        }
    }
    @Test
    void constructor_negativeDistanceProvided_exceptionExpected() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("Horse", 20, -20));
    }
    @Test
    void constructor_negativeDistanceProvided_correctExceptionMessageExpected() {
        try {
            new Horse("Horse", 20, -20);
        } catch (IllegalArgumentException thrown) {
            Assertions.assertEquals("Distance cannot be negative.", thrown.getMessage());
        }
    }
    @Test
    void getName_correctNameProvided_theSameNameExpected() {
        String horseName = "Horse";
        int horseSpeed = 20;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseName, horse.getName());
    }
    @Test
    void getSpeed_correctSpeedProvided_theSameSpeedExpected() {
        String horseName = "Horse";
        int horseSpeed = 20;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseSpeed, horse.getSpeed());
    }
    @Test
    void getDistance_distanceDidntProvided_defaultDistanceSetupExpected() {
        String horseName = "Horse";
        int horseSpeed = 20;
        int horseDistance = 0;
        Horse horse = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(horseDistance, horse.getDistance());
    }
    @Test
    void getDistance_correctDistanceProvided_theSameDistanceSetupExpected() {
        String horseName = "Horse";
        int horseSpeed = 20;
        int horseDistance = 0;
        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        Assertions.assertEquals(horseDistance, horse.getDistance());
    }
    @Test
    void move_getRandomDoubleMethodInvoked() {
        try (MockedStatic<Horse> horseClass = Mockito.mockStatic(Horse.class)) {
            Horse horse = new  Horse("Horse", 20);
            horse.move();
            horseClass.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @CsvSource({"0.2, 20", "0.8, 32"})
    void move_correctDistanceCalculationsExpected(double argument, double result) {
        try (MockedStatic<Horse> horseClass = Mockito.mockStatic(Horse.class)) {
            horseClass.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(argument);
            Horse horse = new  Horse("Horse", 20, 16);
            horse.move();
            Assertions.assertEquals(result, horse.getDistance());
        }
    }
}
