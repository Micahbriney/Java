import java.util.Comparator;

public class ArtistComparator implements Comparator<Song> {

    @Override
    public int compare(Song s1, Song s2) {
        // Returns decending order.
        // Example: o1's lexical order is smaller than o2 than compareToIgnoreCase returns negative
        // s1 = Aplha, s2 = Beta; return negative
        // s1 = Beta, s2 = Alpha; return positive
        // s1 = Aplha, s2 = Alpha; return zero
        return s1.getArtist().compareToIgnoreCase(s2.getArtist());
    }

}

//    Comparator<TV> comp1 = Comparator.comparing(TV::getTime);
//    Comparator<TV> comp2 = Comparator.comparing(TV::getName).reversed();
//    Comparator<TV> myComp = comp1.thenComparing(comp2);
//
//    Comparator<TV> myComp = (p1 , p2) -> {
//        if(p1.getTime() > p2.getTime())
//            return -1;
//        if(p1.getTime() < p2.getTime())
//            return 1;
//        return p1.getName().compareTo(p2.getName()).reversed();
//    }



//orders by time
// breaks tie title in decending order
    //breaking ties with title in descending order (reverse alphabetical).
// For full credit, you must use one method reference operator and one lambda.