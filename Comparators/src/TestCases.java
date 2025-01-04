import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      ArtistComparator artistComp = new ArtistComparator();
      assertTrue(artistComp.compare(songs[0], songs[1]) < 0 );
      assertTrue(artistComp.compare(songs[1], songs[2]) > 0 );
      assertTrue(artistComp.compare(songs[4], songs[4]) == 0 );
   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> titleComp = (s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle());

      assertTrue(titleComp.compare(songs[0], songs[1]) > 0 );
      assertTrue(titleComp.compare(songs[1], songs[2]) < 0 );
      assertTrue(titleComp.compare(songs[5], songs[5]) == 0 );
   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> yearCompare = Comparator.comparing(Song::getYear).reversed();   // Decending order

      assertTrue(yearCompare.compare(songs[5], songs[6]) < 0 );
      assertTrue(yearCompare.compare(songs[3], songs[4]) > 0 );
      assertTrue(yearCompare.compare(songs[7], songs[7]) == 0 );
   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> c1 = new ArtistComparator();
      Comparator<Song> c2 = Comparator.comparing(Song::getYear);
      Comparator<Song> composedComp = new ComposedComparator(c1, c2);

      assertTrue(composedComp.compare(songs[3], songs[7]) > 0 );
      assertTrue(composedComp.compare(songs[4], songs[3]) < 0 );
      assertTrue(composedComp.compare(songs[7], songs[7]) == 0 );
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> thenComp = (s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle());
      thenComp = thenComp.thenComparing(new ArtistComparator());

      assertTrue(thenComp.compare(songs[7], songs[5]) > 0 );
      assertTrue(thenComp.compare(songs[5], songs[7]) < 0 );
      assertTrue(thenComp.compare(songs[7], songs[3]) == 0 );
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> comp = new ArtistComparator();
      comp = comp.thenComparing((s1, s2)-> s1.getTitle().compareToIgnoreCase(s2.getTitle()));
      comp = comp.thenComparing(Song::getYear);

      songList.sort(comp);

      assertEquals(songList, expectedList);
   }
}
