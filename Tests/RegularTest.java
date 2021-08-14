import static org.junit.jupiter.api.Assertions.*;

class RegularTest {

    @org.junit.jupiter.api.Test
    void getInterestRate() {
        Regular obj = new Regular("999","","","","");
        assertEquals(0.10, Regular.getInterestRate());
    }
}