import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @Test
    void nullHorses() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }
    @Test
    void nullHorsesMessage() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Horses cannot be null.", e.getMessage());
        }
    }
    @Test
    void emptyHorses() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void emptyHorsesMessage() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }
    @Test
    void getHorses(){
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse", 20));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Assertions.assertIterableEquals(horses, hippodrome.getHorses());
    }
    @Test
    void moveInvokedForEachHorse() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        horses.forEach((horseMock) -> Mockito.verify(horseMock).move());
    }
    @Test
    void getWinner() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Horse winnerHorse = new Horse("Winner", 20, 50);
        horses.add(winnerHorse);
        Hippodrome hippodrome = new Hippodrome(horses);
        Assertions.assertEquals(winnerHorse, hippodrome.getWinner());
    }
}
