import java.util.*;

public class CaesarCipher {
    
    //helper method 1
    //to trim space before/after the input string (plaintext, ciphertext, key)
    public static String trim(String s) { 
        return s.trim();
    }

    //helper method 2
    //to check if the entered input is empty or not 
    public static boolean emptyInput(String s, String fieldName) { 
        if (s.isEmpty()) {
            System.out.println("Error: " + fieldName + " cannot be empty. Please enter valid " + fieldName); 
            //to print respective error messages based on the input i.e., either plaintext, ciphertext or key respectively
            return true;
        }
        return false; 
    }

    //helper method 3
    public static boolean validateLettersOnly(String s, String fieldName) { 
        // Made an assumption that the inputs contain only alphabets(no numbers or special characters allowed)
        if (!s.matches("[a-zA-Z ]+")) { // Allow spaces in addition to letters
            System.out.println("Error: " + fieldName + " must contain only alphabets");
            return false;
        }
        return true;
    }

    //helper method 4
    //to convert the entered string to uppercase lettters
    private static String toUpperCase(String input) { // Converts to uppercase 
        return input.toUpperCase();
    }

    // Encryption method
    private static void handleEncryption(Scanner scanner) {
        System.out.print("Enter the plaintext: ");
        String plaintext = trim(scanner.nextLine()); //using helper method 1
        if (emptyInput(plaintext, "Plaintext") || !validateLettersOnly(plaintext, "Plaintext"))
            return;
        System.out.print("Enter key (0-25): ");
        int key = -1;
        try {
            key = Integer.parseInt(trim(scanner.nextLine()));
        } catch (NumberFormatException e) {
            //the input value for key should be positive numbers 
            //function to handle exceptions when negative inputs are entered
            System.out.println("Error: Key must be an integer between 0 and 25.");
            return;
        }
        if (key < 0 || key > 25) {
            //i.e., the alphabets from A-Z are numbered from 0-25, if the key value exceeds the given range error is thrown
            System.out.println("Error: Key must be between 0 and 25.");
            return;
        }
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
    }

    private static String encrypt(String plaintext, int key) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = toUpperCase(plaintext); // Converting plaintext to uppercase (helper method 4)
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = 'A'; // All characters are uppercase now
                char encryptedChar = (char) ((c - base + key) % 26 + base);
                ciphertext.append(encryptedChar+" ");
            }//  else if (c == ' ') {
            //     ciphertext.append(' ');
            // }
        }
        return ciphertext.toString(); //StringBuilder to String conversion
    }

    // Decryption method
    private static void handleDecryption(Scanner scanner) {
        System.out.print("Enter ciphertext: ");
        String ciphertext = trim(scanner.nextLine());
        if (emptyInput(ciphertext, "Ciphertext") || !validateLettersOnly(ciphertext, "Ciphertext"))
            return;
        System.out.print("Enter key (0-25): ");
        int key = -1;
        try {
            key = Integer.parseInt(trim(scanner.nextLine()));
        } catch (NumberFormatException e) {
            //for ciphertext as well, the key entered should be in the range 0-25 (A=0 - Z=25)
            System.out.println("Error: Key must be an integer between 0 and 25.");
            return;
        }
        if (key < 0 || key > 25) {
            System.out.println("Error: Key must be between 0 and 25.");
            return;
        }
        String plaintext = decrypt(ciphertext, key);
        System.out.println("Plaintext: " + plaintext);
    }

    private static String decrypt(String ciphertext, int key) {
        //in order to append each character to the output string, we are using stringbuilder class
        //we cannot use string as strings are immutable, we cannot add/append each letter one by one to the same string 
        StringBuilder plaintext = new StringBuilder();
        ciphertext = toUpperCase(ciphertext); // Converting ciphertext to uppercase letters
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = 'A';
                char decryptedChar = (char) ((c - base - key + 26) % 26 + base);
                plaintext.append(decryptedChar+" ");
            }//  else if (c == ' ') {
            //     plaintext.append(' ');
            // }
        }
        return plaintext.toString(); //StringBuilder to String conversion
    }

    // Brute force method
    private static void handleBruteForce(Scanner scanner) {
        System.out.print("Enter ciphertext: ");
        //helper methods usage to validate the input
        String ciphertext = trim(scanner.nextLine());
        if (emptyInput(ciphertext, "Ciphertext") || !validateLettersOnly(ciphertext, "Ciphertext")) 
            return;
        System.out.println("Bruteforce approach ");
        //here, we use loop to attempt bruteforce approach for all the possible keys shift(ie., 0-25)
        for (int key = 0; key < 26; key++) {
            String plaintext = decrypt(ciphertext, key);
            System.out.println("Key: " + key + " -> Plaintext: " + plaintext);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option: \n 1. Encryption \n 2. Decryption \n 3. Brute Force \n 4. Exit");
            int choice = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    System.out.print("Enter your choice (1/2/3/4): ");
                    String input = scanner.nextLine().trim(); // Read input as a string and trim whitespace
    
                    if (input.isEmpty()) {
                        System.out.println("Error: Choice cannot be empty. Please enter a valid choice.");
                    } else {
                        choice = Integer.parseInt(input); // Attempt to parse the input to an integer
    
                        if (choice < 1 || choice > 4) {
                            System.out.println("Error: Please enter a valid choice between 1 and 4.");
                        } else {
                            validChoice = true; // Exit loop if entered choice is correct
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input. Please enter a number between 1 and 4.");
                }
            }
    
            switch (choice) {
                case 1:
                    handleEncryption(scanner);
                    break;
                case 2:
                    handleDecryption(scanner);
                    break;
                case 3:
                    handleBruteForce(scanner);
                    break;
                case 4:
                    System.out.println("Exiting, Thank you!!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    
    }}