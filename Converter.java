package converter;

import java.util.Scanner;

class Converter {
    private Scanner scanner = new Scanner(System.in);

    void start() {
         JSONBuilder jsonBuilder = new JSONBuilder();
         XMLBuilder xmlBuilder = new XMLBuilder();


         String line = scanner.nextLine().trim();
         if (line.startsWith("<")) {
             jsonBuilder.createElement(line);
         } else {
             xmlBuilder.createElement(line);
         }
    }
}
