package time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {
	
	@Test
    void testGetTotalHoursGood() {
        assertEquals(12, Time.getTotalHours("12:05:45"), "Good test failed: Expected 12 hours.");
        assertEquals(0, Time.getTotalHours("00:59:59"), "Good test failed: Expected 0 hours.");
    }

	@Test
	void testGetTotalHoursBad() {
	    assertThrows(StringIndexOutOfBoundsException.class, () -> Time.getTotalHours("1"),
	        "Expected exception for incomplete input but did not occur.");
	   
	}


    @Test
    void testGetTotalHoursBoundary() {
        assertEquals(0, Time.getTotalHours("00:00:00"), "Boundary test failed: Expected 0 hours at minimum.");
        assertEquals(23, Time.getTotalHours("23:59:59"), "Boundary test failed: Expected 23 hours at maximum.");
    }

    // Test for getTotalMinutes
    @Test
    void testGetTotalMinutesGood() {
        assertEquals(5, Time.getTotalMinutes("12:05:45"), "Good test failed: Expected 5 minutes.");
        assertEquals(59, Time.getTotalMinutes("00:59:59"), "Good test failed: Expected 59 minutes.");
    }

    @Test
    void testGetTotalMinutesBad() {
        assertThrows(NumberFormatException.class, () -> Time.getTotalMinutes("12:xx:45"), "Bad test failed: Did not throw exception for non-numeric minutes.");
        assertThrows(StringIndexOutOfBoundsException.class, () -> Time.getTotalMinutes("12"), "Bad test failed: Did not throw exception for incomplete input.");
    }

    @Test
    void testGetTotalMinutesBoundary() {
        assertEquals(0, Time.getTotalMinutes("12:00:00:00"), "Boundary test failed: Expected 0 minutes at minimum.");
        assertEquals(59, Time.getTotalMinutes("12:59:59:00"), "Boundary test failed: Expected 59 minutes at maximum.");
    }
    
    @Test
    void testGetSecondsGood() {
        int seconds = Time.getSeconds("12:05:45:00");
        assertEquals(45, seconds, "Seconds were not extracted properly.");
    }

    // Bad Test for getSeconds
    @Test
    void testGetSecondsBad() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            Time.getSeconds("12:05"); // Missing seconds in input
        }, "Expected StringIndexOutOfBoundsException for bad input.");
    }

    // Boundary Test for getSeconds
    @Test
    void testGetSecondsBoundary() {
        int seconds = Time.getSeconds("12:05:00");
        assertEquals(0, seconds, "Boundary test failed for input '12:05:00'.");
    }

	@Test
	public void testGetTotalSecondsGood() {
	    int seconds = Time.getTotalSeconds("05:05:05:00");
	    assertTrue("The seconds were not calculated properly", seconds == 18305);
	}

	@Test
	public void testGetTotalSecondsBad() {
	    assertThrows(StringIndexOutOfBoundsException.class, () -> {
	        Time.getTotalSeconds("10:00");
	    });
	}

	@ParameterizedTest
	@ValueSource(strings = {"00:00:00:00", "00:00:00:00"})
	public void testGetTotalSecondsBoundary(String input) {
	    int seconds = Time.getTotalSeconds(input);
	    int expected = Time.getTotalHours(input)*3600 + Time.getTotalSeconds(input)+Time.getTotalMinutes(input)*60+(Time.getMilliseconds(input)/1000);
	    assertTrue("Boundary test failed for input: " + input , seconds == expected );
	}

	@Test
	public void testGetMillisecondsGood() {
	    int milliseconds = Time.getMilliseconds("00:00:00:00");
	    assertTrue("Milliseconds were not extracted properly", milliseconds == 00);
	}

	@Test
	public void testGetMillisecondsBad() {
	    assertThrows(StringIndexOutOfBoundsException.class, () -> {
	        Time.getMilliseconds("12:05:05");
	    });
	}

	@ParameterizedTest
	@ValueSource(strings = {"00:00:00:000", "23:59:59:999"})
	public void testGetMillisecondsBoundary(String input) {
	    int milliseconds = Time.getMilliseconds(input);
	    assertTrue("Boundary test failed for input: " + input, milliseconds >= 0 && milliseconds <= 999);
	}
}