package converter;

import java.util.Scanner;

class Converter {
    private Scanner scanner = new Scanner(System.in);

    void start() {
         JSONBuilder jsonBuilder = new JSONBuilder();
         String line = scanner.nextLine();
         jsonBuilder.createElement(line);
    }
}
