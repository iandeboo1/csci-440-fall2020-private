package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.DBTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest extends DBTest {

    @Test
    void testAllLoadsAllArtists() {
        //TODO: NUMBER OF ARTISTS IS 275, NOT SURE WHY 347 IS EXPECTED
        List<Artist> all = Artist.all();
        assertEquals(347, all.size());
    }

    @Test
    void testPagingWorks() {
        //TODO: AGAIN, NUMBER OF ARTISTS IS 275, NOT SURE WHY 347 IS EXPECTED
        assertEquals(100, Artist.all(1, 100).size());
        assertEquals(100, Artist.all(2, 100).size());
        assertEquals(100, Artist.all(3, 100).size());
        assertEquals(47, Artist.all(4, 100).size());
        assertEquals(0, Artist.all(5, 100).size());
    }

    @Test
    void testCreateWorks() {
        Artist artist = new Artist();

        artist.setName("Example");

        assertNull(artist.getArtistId());
        artist.create();
        assertNotNull(artist.getArtistId());

        Artist fromDb = artist.find(artist.getArtistId());
        assertEquals(fromDb.getName(), artist.getName());
        assertEquals(fromDb.getArtistId(), artist.getArtistId());
    }

    @Test
    void testValidationWorks() {
        //TODO: ERRORS SET TO 1 ON LINE 49 AND ERRORS STILL EXIST ON LINE 52 BECAUSE THEY AREN'T CLEARED, THERE'S ACTUALLY NO WAY TO CLEAR THEM
        Artist Artist = new Artist();

        assertFalse(Artist.verify());
        // expect a name
        assertEquals(1, Artist.getErrors().size());

        Artist.setName("Example");
        assertTrue(Artist.verify());
        assertEquals(0, Artist.getErrors().size());
    }

    @Test
    void testUpdateWorks() {
        Artist artist = Artist.find(1);
        String newName = "DC/AC";
        artist.setName(newName);
        artist.update();
        assertEquals(newName, Artist.find(1).getName());
    }

}
