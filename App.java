import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class App {

    // Global variables
    private static int[][] planeSeats = null;
    private static int[] pricePerRow = null;
    private static Payment [] payment_records = new Payment[100];

    public static void main(String[] args) {
        System.out.println("Welcome to Flying Java!");
        initialiseRows();
        runMenu();
    }

    public static void initialiseRows() {
        planeSeats = new int[4][]; // total rows - multidimensional array
        planeSeats[0] = new int[16]; // row 1 - initialised at 0 all available
        planeSeats[1] = new int[22]; // row 2 - initialised at 0 all available
        planeSeats[2] = new int[22]; // row 2 - initialised at 0 all available
        planeSeats[3] = new int[16]; // row 2 - initialised at 0 all available

        //price of a seat in each roww
        pricePerRow = new int[4];
        pricePerRow[0] = 50;
        pricePerRow[1] = 80;
        pricePerRow[2] = 80;
        pricePerRow[3] = 50;
    }

    public static void runMenu() {
        int option;
        boolean cont = true;

        while (cont) {
            option = getOption();
            switch (option) {
                case 0:
                    cont = false;
                    break;
                case 1:
                    showSeatingArea();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    searchPayment();
                    break;
                case 4:
                    SaveTO();
                    break;
                default:
                    System.out.println("Option not available. Please select a valid option: ");
            }
        }
    }



    private static int getOption() {

        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int option = -1;
        do {
            System.out.println();
            System.out.println("+---------------------------------------------+");
            System.out.println("|                MAIN MENU                    |");
            System.out.println("+---------------------------------------------+");
            System.out.println("|  1) Show seating area and available seats   |");
            System.out.println("|  2) Buy a plane ticket                      |");
            System.out.println("|  3) Search payment                          |");
            System.out.println("|  4) Save information to file                |");
            System.out.println("|  0) Quit                                    |");
            System.out.println("+---------------------------------------------+");
            System.out.print("Please select an option: ");
            try {
                option = input.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("This option not valid.");
            }
        } while (!valid);
        return option;

    }

    private static void showSeatingArea() {

        int rows = planeSeats.length;
        char aisle = '|';

        System.out.println("=".repeat(76));
        System.out.println("                              PLANE SEATING MAP ");
        System.out.println("=".repeat(76));

        for (int row = 0; row < rows; row++) {
            System.out.print("Row " + (row+1) + "(£" + pricePerRow[row]+ ")  ");
            int seatsPerRow = planeSeats[row].length;
            for (int seat = 0; seat < seatsPerRow; seat++) {
                if (seat == 9) { // Create aisles
                    System.out.print(" " + aisle + " ");
                }
                if (planeSeats[row][seat] == 0) { //available
                    System.out.print("[O]");
                } else { // not available
                    System.out.print("[X]");
                }
            }
            System.out.println();
        }
        System.out.println("=".repeat(76));
        System.out.println("LEGEND: [O] = Seat available, [X] = Seat not available, | = Aisle");
        System.out.println("=".repeat(76));
        System.out.println();

    }

    //selecting a seat & paying for the ticket
    private static void buyTicket() {
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your email : ");
        String email =input.nextLine();

        double payment_amount = 0;

        int row = 0;
        int seat = 0;
        boolean val_con = true;

        while(val_con){
            System.out.print("Enter row number: ");
            row = input.nextInt() - 1;
            val_con = false;
            if (row < 0 || row > 3){
                System.out.println("Invalid row number");
                val_con = true;
            }

        }

        val_con = true;
        while(val_con){
            val_con = false;
            System.out.print("Enter seat number: ");
            seat = input.nextInt() - 1;

            if(seat < 0){
                System.out.println("Invalid seat number");
                val_con = true;
            }
            if(row == 0 && seat > 15){
                System.out.println("Invalid seat number for row " + (row+1));
                val_con = true;
            }
            if(row == 1 && seat > 21){
                System.out.println("Invalid seat number for row " + (row+1));
                val_con = true;
            }
            if(row == 2 && seat > 21){
                System.out.println("Invalid seat number for row " + (row+1));
                val_con = true;
            }
            if(row == 3 && seat > 15){
                System.out.println("Invalid seat number for row " + (row+1));
                val_con = true;
            }
        }

        // Check if the seat is available or not & make payments
        val_con = true;
        if (planeSeats[row][seat] == 0) {
            planeSeats[row][seat] = 1;
            while(val_con){
                val_con = false;
                System.out.print("Please enter payment amount : ");
                payment_amount = input.nextDouble();
                if(payment_amount < pricePerRow[row]){
                    System.out.println("Purchase unsuccessful!.");
                    System.out.println("Price per seat in row " + (row+1) + " = £" + pricePerRow[row]);
                    val_con = true;
                }

            }

            System.out.println("Purchase successful.");
            if(row == 0){
                payment_records[seat] = new Payment(email,payment_amount);
                showSeatingArea();
            }
            if(row == 1){
                payment_records[16 + seat] = new Payment(email,payment_amount);
                showSeatingArea();
            }
            if(row == 2){
                payment_records[39 + seat] = new Payment(email,payment_amount);
                showSeatingArea();
            }
            if(row == 3){
                payment_records[62 + seat] = new Payment(email,payment_amount);
                showSeatingArea();
            }

        } else {
            System.out.println("This seat is already taken.");
        }

    }

    //Search for payment information
    public static void searchPayment(){
        Scanner input = new Scanner(System.in);
        int search_row = 0;
        int search_seat = 0;
        int option_search = -1;
        boolean valid_search = false;
        do {
            System.out.println();
            System.out.println("+---------------------------------------------+");
            System.out.println("|             SEARCH METHOD                   |");
            System.out.println("+---------------------------------------------+");
            System.out.println("|  1) By seat & row number                    |");
            System.out.println("|  2) By payment amount                       |");
            System.out.println("|  3) By email                                |");
            System.out.println("|  0) Quit                                    |");
            System.out.println("+---------------------------------------------+");
            System.out.print("Please select an option: ");
            try {
                option_search = input.nextInt();
                valid_search = true;
            } catch (Exception e) {
                System.out.println("This option not valid.");
            }
        } while (!valid_search);


        switch (option_search){
            case 0:
                break;
            case 1:
                while(valid_search){
                    System.out.print("Enter row number: ");
                    search_row = input.nextInt() - 1;
                    valid_search = false;
                    if (search_row < 0 || search_row > 3){
                        System.out.println("Invalid row number");
                        valid_search = true;
                    }

                }

                valid_search = true;
                while(valid_search){
                    valid_search = false;
                    System.out.print("Enter seat number: ");
                    search_seat = input.nextInt() - 1;

                    if(search_seat < 0){
                        System.out.println("Invalid seat number");
                        valid_search = true;
                    }
                    if(search_row == 0 && search_seat > 15){
                        System.out.println("Invalid seat number for row " + (search_row+1));
                        valid_search = true;
                    }
                    if(search_row == 1 && search_seat > 21){
                        System.out.println("Invalid seat number for row " + (search_row+1));
                        valid_search = true;
                    }
                    if(search_row == 2 && search_seat > 21){
                        System.out.println("Invalid seat number for row " + (search_row+1));
                        valid_search = true;
                    }
                    if(search_row == 3 && search_seat > 15){
                        System.out.println("Invalid seat number for row " + (search_row+1));
                        valid_search = true;
                    }
                }

                if(search_row == 0){
                    if(payment_records[search_seat] != null){
                        System.out.println("Purchase details of row " + (search_row+1) + " Seat " + (search_seat+1) + " : ");
                        System.out.println("     Email: " + payment_records[search_seat].getEmail());
                        System.out.println("     Payment:" + payment_records[search_seat].getPayment_amount());
                    }
                    else{
                        System.out.println("Seat not purchased");
                    }
                }
                if(search_row == 1){
                    if(payment_records[16 + search_seat] != null){
                        System.out.println("Purchase details of row " + (search_row+1) + " Seat " + (search_seat+1) + " : ");
                        System.out.println("     Email: " + payment_records[16 + search_seat].getEmail());
                        System.out.println("     Payment:" + payment_records[16 + search_seat].getPayment_amount());
                    }
                    else{
                        System.out.println("Seat not purchased");
                    }
                }
                if(search_row == 2){
                    if(payment_records[39 + search_seat] != null){
                        System.out.println("Purchase details of row " + (search_row+1) + " Seat " + (search_seat+1) + " : ");
                        System.out.println("     Email: " + payment_records[39+ search_seat].getEmail());
                        System.out.println("     Payment:" + payment_records[39 + search_seat].getPayment_amount());
                    }
                    else{
                        System.out.println("Seat not purchased");
                    }
                }
                if(search_row == 3){
                    if(payment_records[62 + search_seat] != null){
                        System.out.println("Purchase details of row " + (search_row+1) + " Seat " + (search_seat+1) + " : ");
                        System.out.println("     Email: " + payment_records[62 + search_seat].getEmail());
                        System.out.println("     Payment:" + payment_records[62 + search_seat].getPayment_amount());
                    }
                    else{
                        System.out.println("Seat not purchased");
                    }
                }
                break;

            case 2:
                System.out.print("Please enter search payment amount : ");
                double payment_search = input.nextDouble();
                boolean payment_found = false;
                System.out.println("Records of £" + payment_search + " :");
                System.out.println(" ");
                for(int i = 0 ; i < payment_records.length ; i++){
                    if(payment_records[i] != null && payment_records[i].getPayment_amount() == payment_search){
                        payment_found = true;
                        if(i < 16){
                            System.out.println("      Email: " + payment_records[i].getEmail());
                            System.out.println("      Row no: 1");
                            System.out.println("      Seat no: " + (i+1));
                            System.out.println(" ");
                        }
                        if(15<i && i<39){
                            System.out.println("      Email: " + payment_records[i].getEmail());
                            System.out.println("      Row no: 2");
                            System.out.println("      Seat no: " + (i-15));
                            System.out.println(" ");
                        }
                        if(38<i && i<62){
                            System.out.println("      Email: " + payment_records[i].getEmail());
                            System.out.println("      Row no: 3");
                            System.out.println("      Seat no: " + (i-38));
                            System.out.println(" ");
                        }
                        if(61<i){
                            System.out.println("      Email: " + payment_records[i].getEmail());
                            System.out.println("      Row no: 4");
                            System.out.println("      Seat no: " + (i-61));
                            System.out.println(" ");
                        }

                    }
                }
                if(!payment_found){
                    System.out.println("      Payment not found");
                }
                break;

            case 3:
                System.out.print("Please enter search email : ");
                input.nextLine();
                String search_email =input.nextLine();
                boolean email_found = false;
                System.out.println("Records of " + search_email + " :");
                System.out.println(" ");

                for(int i = 0 ; i < payment_records.length ; i++){
                    if(payment_records[i] != null && payment_records[i].getEmail().toLowerCase().equals(search_email.toLowerCase())){
                        email_found = true;
                        if(i < 16){
                            System.out.println("      Paid amount: " + payment_records[i].getPayment_amount());
                            System.out.println("      Row no: 1");
                            System.out.println("      Seat no: " + (i+1));
                            System.out.println(" ");
                        }
                        if(15<i && i<39){
                            System.out.println("      Paid amount: " + payment_records[i].getPayment_amount());
                            System.out.println("      Row no: 2");
                            System.out.println("      Seat no: " + (i-15));
                            System.out.println(" ");
                        }
                        if(38<i && i<62){
                            System.out.println("      Paid amount: " + payment_records[i].getPayment_amount());
                            System.out.println("      Row no: 3");
                            System.out.println("      Seat no: " + (i-38));
                            System.out.println(" ");
                        }
                        if(61<i){
                            System.out.println("      Paid amount: " + payment_records[i].getPayment_amount());
                            System.out.println("      Row no: 4");
                            System.out.println("      Seat no: " + (i-61));
                            System.out.println(" ");
                        }

                    }
                }
                if(!email_found){
                    System.out.println("      Email not found");
                }

                break;

            default:
                System.out.println("Invalid option.");
                break;
        }


    }

    //Save payment information to a text file
    private static void SaveTO(){
        File file = new File("payment.text");

        try{
            FileWriter fileWriter = new FileWriter(file,true);

            if(file.createNewFile() || file.exists()){
                for(int i=0; i < payment_records.length ; i++){
                    if(payment_records[i] != null){
                        if(i < 16){
                            if(i<10){
                                fileWriter.write("Row: 1 Seat: " + (i+1) + "    Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }
                            else{
                                fileWriter.write("Row: 1 Seat: " + (i+1) + "   Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }

                        }
                        if(15<i && i<39){
                            if((i-15)<10){
                                fileWriter.write("Row: 2 Seat: " + (i-15) + "    Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }
                            else{
                                fileWriter.write("Row: 2 Seat: " + (i-15) + "   Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }

                        }
                        if(38<i && i<62){
                            if((i-38)<10){
                                fileWriter.write("Row: 3 Seat: " + (i-38) + "    Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }
                            else{
                                fileWriter.write("Row: 3 Seat: " + (i-38) + "   Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }

                        }
                        if(61<i){
                            if((i-61)<10){
                                fileWriter.write("Row: 4 Seat: " + (i-61) + "    Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }
                            else{
                                fileWriter.write("Row: 4 Seat: " + (i-61) + "   Email: " + payment_records[i].getEmail()
                                        + "   " + "Payment: " + payment_records[i].getPayment_amount() + "\n");
                            }

                        }

                    }
                }
                fileWriter.flush();
                fileWriter.close();
            }
            Scanner file_reader = new Scanner(file);
            while(file_reader.hasNext()){
                String line = file_reader.nextLine();
                System.out.println(line);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}
