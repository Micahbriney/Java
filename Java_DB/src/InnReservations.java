import java.sql.*;
import java.util.Scanner;


public class InnReservations {

    public static void main(String[] args) throws SQLException {
        InnReservations ir = new InnReservations();

        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Pick an option from the following: (USAGE: <#>)\n" +
                    "1: Rooms and Rates.\n" +
                    "2: Reservations.\n" +
                    "3: Reservation Change.\n" +
                    "4: Reservation Cancellation.\n" +
                    "5: Detailed Reservation Information.\n" +
                    "6: Revenue.\n" +
                    "7: Exit.");
            int menuOption = Integer.parseInt(scanner.nextLine());


            switch (menuOption) {
                case 1 -> ir.Rooms_and_Rates();
                case 2 -> ir.Reservations();
                case 3 -> ir.Reservation_Change();
                case 4 -> ir.Reservation_Cancellation();
                case 5 -> ir.Detailed_Reservation_Information();
                case 6 -> ir.Revenue();
                case 7 -> System.exit(1);
            }

        }

    }

    private void Rooms_and_Rates() throws SQLException {

        System.out.println("Rooms and Rates: List Rooms by recent popularity in descending order.\n" +
                "Next available check in date.\n" +
                "Length of most recent stay.");

        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

            // Step 2: Construct SQL statement
            String sql = "Select Room, round((Sum(Total_Days) / 180),2) popularity, Max(MC) Next_CheckIn_Date, DATEDIFF(Max(MC), Max(MI)) Length from\n" +
                    "    (select Room, Sum(DATEDIFF(Checkout, CheckIn)) AS Total_Days, Max(Checkout) AS MC, Max(CheckIn) AS MI\n" +
                    "    from lab7_reservations\n" +
                    "    where CheckIn between (CURRENT_DATE - INTERVAL 180 DAY) AND CURRENT_DATE\n" +
                    "    group by Room, CheckIn, Checkout) AS T\n" +
                    "group by Room\n" +
                    "order by popularity DESC\n";

            // Step 3: (omitted in this example) Start transaction


            // Step 4: Send SQL statement to DBMS
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {


                // Step 5: Handle results
                System.out.println("Room | Popularity | Next_CheckIn_Date | Last_Stay_Lasted");
                while (rs.next()) {
                    String room = rs.getString("Room");
                    Float popularity = rs.getFloat("Popularity");
                    String Next_CheckIn_Date = rs.getString("Next_CheckIn_Date");
                    Integer Length = rs.getInt("Length");
                    System.out.format("%s  | %.2f       | %s        | %d %n", room, popularity, Next_CheckIn_Date, Length);
                }
                System.out.println("\n--------------------------------------------------------\n");

                Scanner scanner = new Scanner(System.in);
                System.out.println("Back to main menu? USAGE <Yes | No>");
                char continueInput = scanner.nextLine().toLowerCase().charAt(0);
                if (continueInput != 'y') { // Exit on No, no, n, or wrong input
                    System.exit(1);
                }

            }

            // Step 6: (omitted in this example) Commit or rollback transaction
        }


    }

    private void Reservations() throws SQLException {
        PreparedStatement pstmt;
        int count = 0; // row counter
        // Prompt user for information that will be needed for reservation
        System.out.println("\nReservation: Please provide the following information: " +
                "1: First name.\n" +
                "2: Last name.\n" +
                "3: Room Code.\n" +
                "4: Bed Type.\n" +
                "5: CheckIn Date.\n" +
                "6: Checkout Date.\n" +
                "7: Number of children.\n" +
                "8: Number of adults.\n" +
                "\nWould you like to Continue. \"Yes\" or \"No\"?");
        Scanner scanner = new Scanner(System.in);   // Wait for user to continue or exit
        char continueInput = scanner.nextLine().toLowerCase().charAt(0); // Normalize user input
        if (continueInput != 'y') { // Exit on No, no, n, or wrong input
            return;
        }

        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

            // Gather user information
            System.out.println("1: Input First name:");
            String firstNameInput = scanner.nextLine().toUpperCase();
            System.out.println("2: Input Last name:");
            String lastNameInput = scanner.nextLine().toUpperCase();
            System.out.println("3: Input Room Code or \"Any\" for no preference:");
            String roomCodeInput = scanner.nextLine().toUpperCase();
            System.out.println("4: Input Bed Type or \"Any\" for no preference:");
            String bedTypeInput = scanner.nextLine();
            System.out.println("5: Input CheckIn Date: YYYY-MM-DD");
            String checkInDateInput = scanner.nextLine();
            System.out.println("6: Input Checkout Date: YYYY-MM-DD");
            String checkOutDateInput = scanner.nextLine();
            System.out.println("7: Input Number of children:");
            int numChildrenInput = scanner.nextInt();
            System.out.println("8: Input Number of adults:");
            int numAdultInput = scanner.nextInt();

            // SQL for a specific bed type and a specific Room Code
            String RoomCodeSQL = "select DISTINCT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor\n" +
                    "from lab7_rooms\n" +
                    "join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       maxOcc >= ? AND\n" +
                    "       RoomCode = ? AND\n" +
                    "       bedType = ?\n" +
                    "where RoomCode NOT IN\n" +
                    "  (select RoomCode -- Occupied RoomCodes\n" +
                    "  from lab7_rooms\n" +
                    "  join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       RoomCode = ?\n" +
                    "  where Checkin < ? AND\n" +
                    "        Checkout > ?)";
            // SQL for a specific bed type and any Room Code
            String anyRoomsSQL = "select DISTINCT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor\n" +
                    "from lab7_rooms\n" +
                    "join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       maxOcc >= ? AND\n" +
                    "       bedType = ?\n" +
                    "where RoomCode IN\n" +
                    "  (select DISTINCT RoomCode -- Not Occupied RoomCodes\n" +
                    "  from lab7_rooms\n" +
                    "  join lab7_reservations\n" +
                    "    on RoomCode = Room\n" +
                    "  where Checkin < ? AND\n" +
                    "        Checkout > ?)";
            // SQL for any bed type and a specific Room Code
            String RoomCodeAnyBedSQL = "select DISTINCT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor\n" +
                    "from lab7_rooms\n" +
                    "join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       maxOcc >= ? AND\n" +
                    "       RoomCode = ?\n" +
                    "where RoomCode NOT IN\n" +
                    "  (select RoomCode -- Occupied RoomCodes\n" +
                    "  from lab7_rooms\n" +
                    "  join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       RoomCode = ?\n" +
                    "  where Checkin < ? AND\n" +
                    "        Checkout > ?)";
            // SQL for any bed type and and Room code
            String anyRoomAnyBedSQL = "select DISTINCT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor\n" +
                    "from lab7_rooms\n" +
                    "join lab7_reservations\n" +
                    "    on RoomCode = Room AND\n" +
                    "       maxOcc >= ?\n" +
                    "where RoomCode IN\n" +
                    "  (select DISTINCT RoomCode -- Not Occupied RoomCodes\n" +
                    "  from lab7_rooms\n" +
                    "  join lab7_reservations\n" +
                    "    on RoomCode = Room\n" +
                    "  where Checkin < ? AND\n" +
                    "      Checkout > ?)";

            String insSQL = "select * from lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids)\n" +
                    "values(?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?,\n" +
                    "       ?)";

            // To many occupants case
            if (numAdultInput + numChildrenInput > 4) {
                System.out.println("You exceed max capacity of all rooms. To reserve a block of rooms then submit\n" +
                        "multiple reservations");
                return;
            }
            // Check for user preferences for RoomCode and Bed Type
            if (roomCodeInput.equals("ANY") && bedTypeInput.equals("ANY")) { // No preferences
                pstmt = conn.prepareStatement(anyRoomAnyBedSQL);
                pstmt.setInt(1, numChildrenInput + numAdultInput);
                pstmt.setDate(2, java.sql.Date.valueOf(checkOutDateInput));
                pstmt.setDate(3, java.sql.Date.valueOf(checkInDateInput));
            } else if (bedTypeInput.equals("ANY")) { // Bed type preference
                pstmt = conn.prepareStatement(RoomCodeAnyBedSQL);
                pstmt.setInt(1, numChildrenInput + numAdultInput);
                pstmt.setString(2, roomCodeInput);
                pstmt.setString(3, roomCodeInput);
                pstmt.setDate(4, java.sql.Date.valueOf(checkOutDateInput));
                pstmt.setDate(5, java.sql.Date.valueOf(checkInDateInput));
            } else if (roomCodeInput.equals("ANY")) { // Room Code preference
                pstmt = conn.prepareStatement(anyRoomsSQL);
                pstmt.setInt(1, numChildrenInput + numAdultInput);
                pstmt.setString(2, bedTypeInput);
                pstmt.setDate(3, java.sql.Date.valueOf(checkOutDateInput));
                pstmt.setDate(4, java.sql.Date.valueOf(checkInDateInput));
            } else { // Both Room code and Bed Type preference
                pstmt = conn.prepareStatement(RoomCodeSQL);
                pstmt.setInt(1, numChildrenInput + numAdultInput);
                pstmt.setString(2, roomCodeInput);
                pstmt.setString(3, bedTypeInput);
                pstmt.setString(4, roomCodeInput);
                pstmt.setDate(5, java.sql.Date.valueOf(checkOutDateInput));
                pstmt.setDate(6, java.sql.Date.valueOf(checkInDateInput));
            }

            // (3) Execute the statement
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Listing | RoomCode | RoomName                 | Beds | bedType | maxOcc | basePrice | decor");
            while (rs.next()) {
                String RoomCode = rs.getString("RoomCode");
                String RoomName = rs.getString("RoomName");
                int Beds = rs.getInt("Beds");
                String bedType = rs.getString("bedType");
                int maxOcc = rs.getInt("maxOcc");
                float basePrice = rs.getFloat("basePrice");
                String decor = rs.getString("decor");
                count++;
                System.out.format("%-7d | %-8s | %-24s | %d    | %-7s | %d      | $%-8.2f |%s %n",
                        count, RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor);
            }



            // TODO Insert statement is not inserting
            System.out.println("To book a room type in the available listing's number");
            int listing = scanner.nextInt();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if(count == listing){
                    String RoomCode = rs.getString("RoomCode");
                    float basePrice = rs.getFloat("basePrice");
                    try(PreparedStatement pstmt2 = conn.prepareStatement(insSQL)){
                        pstmt2.setInt(1, 10000); // TODO This will need to auto increment somehow
                        pstmt2.setString(2, RoomCode);
                        pstmt2.setDate(3, java.sql.Date.valueOf(checkInDateInput));
                        pstmt2.setDate(4, java.sql.Date.valueOf(checkOutDateInput));
                        pstmt2.setFloat(5, basePrice);
                        pstmt2.setString(6, firstNameInput);
                        pstmt2.setString(7, lastNameInput);
                        pstmt2.setInt(8, numAdultInput);
                        pstmt2.setInt(9, numChildrenInput);

                    } catch (SQLException e) {
                    // Handle exception appropriately
                }
                    break;
                }
                count++;
            }

            System.out.println("-----------------------------------------------------------------\n");

            // TODO Confirmation screen


            //
            //        CASE:With this information, the system shall produce a numbered list of available rooms,
            //             along with a prompt to allow booking by option number.
            //
            //        CASE: If no exact matches are found, suggest 5 possibilities for different rooms or dates.
            //          These 5 possibilities should be chosen based on similarity to the desired reservation
            //          (“similarity” defined as nearby dates, rooms with similar
            //          features or decor, or logic of your own choosing) For every option presented, maximum room
            //        occupancy must be considered and the dates must not overlap with another existing reservation.
            //
            //        CASE: If the requested person count (children plus adults) exceeds the maximum capacity of any one
            //              room at the Inn, print a message indicating that no suitable rooms are available.
            //
            //        To reserve a block of rooms, it would be up to the user to submit multiple reservation requests.
            //
            //        CASE: At the prompt, the user may decide to cancel the current request, which will return the user to
            //              the main menu.
            //
            //        If the user chooses to book one of the room options presented, they will enter
            //        the option number at the prompt.
            //
            //        After a choice is made, provide the user with a confirmation screen that displays the following:
            //• First name, last name
            //• Room code, room name, bed type
            //• Begin and end date of stay
            //• Number of adults
            //• Number of children
            //• Total cost of stay, based on a sum of the following:
            //– Number of weekdays multipled by room base rate
            //– Number of weekend days multiplied by 110% of the room base rate
        }

    }

    // TODO
    private void Reservation_Change() throws SQLException {
        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

        }

    }
    // TODO
    private void Reservation_Cancellation() throws SQLException {
        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

        }

    }
    // TODO
    private void Detailed_Reservation_Information() throws SQLException {
        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

        }

    }

    private void Revenue() throws SQLException {
        System.out.println("Revenue for each month and the total for the year.");

        // Connect to server
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load JDBC Driver");
            System.exit(-1);
        }

        // Step 1: Establish connection to RDBMS
        try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
                System.getenv("HP_JDBC_USER"),
                System.getenv("HP_JDBC_PW"))) {

            // Step 2: Construct SQL statements

            // Create a "temp" table and populate it with the monthly data
            // TODO The monthly data is not currently calculated correctly. Still need to figure out where the issue is
            // TODO Also Total revenue is not limited to a year. It calculates total of all reservations
            String dropsql = "drop table if exists Inn_Revenue";
            String createsql = "CREATE TABLE if not exists Inn_Revenue(\n" +
                               "JanRev Integer,\n" +
                               "FebRev Integer,\n" +
                               "MarRev Integer,\n" +
                               "AprRev Integer,\n" +
                               "MayRev Integer,\n" +
                               "JunRev Integer,\n" +
                               "JulRev Integer,\n" +
                               "AugRev Integer,\n" +
                               "SepRev Integer,\n" +
                               "OctRev Integer,\n" +
                               "NovRev Integer,\n" +
                               "DecRev Integer,\n" +
                               "TotRev Integer\n" +
                               ");";
            String insertsql = "insert into Inn_Revenue (JanRev, FebRev, MarRev, AprRev, MayRev, JunRev, JulRev, AugRev, SepRev, OctRev, NovRev, DecRev, TotRev)\n" +
                    "values(\n" +
                    "    (select Sum(JanRev) AS JanRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS JanRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 1 AND Month(Checkout) = 1\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS JanRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 1 AND Month(Checkout) = 1)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS JanRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 1 AND Month(Checkout) <> 1) AS JAN), -- January\n" +
                    "\n" +
                    "    (select Sum(FebRev) AS FebRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS FebRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 2 AND Month(Checkout) = 2\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS FebRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 2 AND Month(Checkout) = 2)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS FebRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 2 AND Month(Checkout) <> 2) AS FEB), -- February\n" +
                    "\n" +
                    "    (select Sum(MarRev) AS MarRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS MarRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 3 AND Month(Checkout) = 3\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS MarRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 3 AND Month(Checkout) = 3)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS MarRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 3 AND Month(Checkout) <> 3) AS MAR), -- March\n" +
                    "\n" +
                    "    (select Sum(AprRev) AS AprRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS AprRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 4 AND Month(Checkout) = 4\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS AprRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 4 AND Month(Checkout) = 4)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS AprRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 4 AND Month(Checkout) <> 4) AS APR), -- April\n" +
                    "\n" +
                    "    (select Sum(MayRev) AS MayRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS MayRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 5 AND Month(Checkout) = 5\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS MayRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 5 AND Month(Checkout) = 5)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS MayRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 5 AND Month(Checkout) <> 5) AS MAY), -- May\n" +
                    "\n" +
                    "    (select Sum(JunRev) AS JunRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS JunRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 6 AND Month(Checkout) = 6\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS JunRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 6 AND Month(Checkout) = 6)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS JunRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 6 AND Month(Checkout) <> 6) AS JUN), -- June\n" +
                    "\n" +
                    "    (select Sum(JulRev) AS JulRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS JulRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 7 AND Month(Checkout) = 7\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS JulRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 7 AND Month(Checkout) = 7)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS JulRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 7 AND Month(Checkout) <> 7) AS JUL), -- July\n" +
                    "\n" +
                    "    (select Sum(AugRev) AS AugRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS AugRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 8 AND Month(Checkout) = 8\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS AugRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 8 AND Month(Checkout) = 8)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS AugRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 8 AND Month(Checkout) <> 8) AS AUG), -- August\n" +
                    "\n" +
                    "    (select Sum(SepRev) AS SepRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS SepRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 9 AND Month(Checkout) = 9\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS SepRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 9 AND Month(Checkout) = 9)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS SepRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 9 AND Month(Checkout) <> 9) AS SEP), -- September\n" +
                    "\n" +
                    "    (select Sum(OctRev) AS OctRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS OctRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 10 AND Month(Checkout) = 10\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS OctRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 10 AND Month(Checkout) = 10)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS OctRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 10 AND Month(Checkout) <> 10) AS OCT), -- October\n" +
                    "\n" +
                    "    (select Sum(NovRev) AS NovRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS NovRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 11 AND Month(Checkout) = 11\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS NovRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 11 AND Month(Checkout) = 11)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS NovRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 11 AND Month(Checkout) <> 11) AS OCT), -- October\n" +
                    "\n" +
                    "    (select Sum(DecRev) AS DecRev\n" +
                    "    from\n" +
                    "      (select round((Rate * (Day(Checkout) - Day(CheckIn))),0) AS DecRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 12 AND Month(Checkout) = 12\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn))),0) AS DecRev\n" +
                    "      from lab7_reservations\n" +
                    "      where (Month(CheckIn) <> 12 AND Month(Checkout) = 12)\n" +
                    "      \n" +
                    "      union\n" +
                    "      \n" +
                    "      select round((Rate * (Day(LAST_DAY(CheckIn)) - Day(CheckIn) + 1)),0) AS DecRev\n" +
                    "      from lab7_reservations\n" +
                    "      where Month(CheckIn) = 12 AND Month(Checkout) <> 12) AS DECE), -- December\n" +
                    "\n" +
                    "    (select Sum(TotRev) AS TotRev \n" +
                    "    from                       \n" +
                    "      (select *, round((Rate * Datediff(Checkout, CheckIn))) AS TotRev\n" +
                    "      from lab7_reservations) AS TOT) -- Total\n" +
                    "  )";

            String revenuesql = "Select JanRev, FebRev, MarRev, AprRev, MayRev, JunRev, " +
                                "JulRev, AugRev, SepRev, OctRev, NovRev, DecRev, TotRev\n" +
                                "from Inn_Revenue";

            // Step 3: (omitted in this example) Start transaction


            // Step 4: Send SQL statements to DBMS
            try (Statement stmtdrop = conn.createStatement()) { // Drop "temp" table

                int rs = stmtdrop.executeUpdate(dropsql);
            }

            try (Statement stmtcreate = conn.createStatement()) { // Create "temp" table

                int rs = stmtcreate.executeUpdate(createsql);
            }

            try (Statement stmtinsert = conn.createStatement()) { // Populate "temp" table

                int rs = stmtinsert.executeUpdate(insertsql);
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(revenuesql)) { // Select contents from "temp" table


                // Step 5: Handle results
                System.out.println("JanRev | FebRev | MarRev | AprRev | MayRev | JunRev | " +
                        "JulRev | AugRev | SepRev | OctRev | NovRev | DecRev | TotRev");
                while (rs.next()) {
                    Integer JanRev = rs.getInt("JanRev");
                    Integer FebRev = rs.getInt("FebRev");
                    Integer MarRev = rs.getInt("MarRev");
                    Integer AprRev = rs.getInt("AprRev");
                    Integer MayRev = rs.getInt("MayRev");
                    Integer JunRev = rs.getInt("JunRev");
                    Integer JulRev = rs.getInt("JulRev");
                    Integer AugRev = rs.getInt("AugRev");
                    Integer SepRev = rs.getInt("SepRev");
                    Integer OctRev = rs.getInt("OctRev");
                    Integer NovRev = rs.getInt("NovRev");
                    Integer DecRev = rs.getInt("DecRev");
                    Integer TotRev = rs.getInt("TotRev");
                    System.out.format("%d  | %d  | %d  | %d  | %d  | %d  | %d  | %d  | %d  | %d  | %d  | %d  | %d%n",
                            JanRev, FebRev, MarRev, AprRev, MayRev, JunRev,
                            JulRev, AugRev, SepRev, OctRev, NovRev, DecRev,
                            TotRev);
                }
            }

            try (Statement stmtdrop2 = conn.createStatement()) { // Drop "temp" table

                int rs = stmtdrop2.executeUpdate(dropsql);
            }

            System.out.println("\n--------------------------------------------------------" +
                               "----------------------------------------------------------\n");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Continue Yes or No?");
            char continueInput = scanner.nextLine().toLowerCase().charAt(0);
            if (continueInput != 'y') { // Exit on No, no, n, or wrong input
                System.exit(1);
            }
        }
    }
}
