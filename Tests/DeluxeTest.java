import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeluxeTest {

    @Test
    void getInterestRate() {
        Deluxe obj = new Deluxe("999","","","","");
        assertEquals(0.15, Deluxe.getInterestRate());
    }
}