import java.util.*;

public class PolyAlphabeticCipher {

    public static String trim(String s) { // Trim extra spaces from input string
        return s.trim();
    }

    public static boolean emptyInput(String s, String fieldName) { // Ensure that the input is not empty (plaintext/ciphertext/key)
        if (s.isEmpty()) {
            System.out.println("Error: " + fieldName + " cannot be empty. Please enter valid " + fieldName);
            return true;
        }
        return false;
    }

    public static boolean validateLettersOnly(String s, String fieldName) { // Validate input to only contain letters (assume there are no special characters (other than space between words) or numbers allowed)
        if (!s.matches("[a-zA-Z ]+")) { // Allow spaces in addition to letters
            System.out.println("Error: " + fieldName + " must contain only letters and spaces.");
            return false;
        }
        return true;
    }

    private static String toUpperCase(String input) { // Convert input to uppercase
        return input.toUpperCase();
    }

    // Encryption method --> handleEncryption, encrypt
    private static void handleEncryption(Scanner scanner) {
        System.out.print("Enter plaintext: ");
        String plaintext = trim(scanner.nextLine());
        if (emptyInput(plaintext, "Plaintext") || !validateLettersOnly(plaintext, "Plaintext"))
            return;
        System.out.println("Enter key: ");
        String key = trim(scanner.nextLine());
        if (emptyInput(key, "Key") || !validateLettersOnly(key, "Key") || key.replaceAll(" ", "").isEmpty())
            return;
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext + " ");
    }

    private static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = toUpperCase(plaintext); // Convert plaintext to uppercase
        key = toUpperCase(key); // Convert key to uppercase
        int keyLength = key.length();
        int keyIndex = 0;

        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = 'A'; // All characters are uppercase now
                char keyChar = key.charAt(keyIndex % keyLength);
                int shift = keyChar - base;
                char encryptedChar = (char) ((c - base + shift) % 26 + base);
                ciphertext.append(encryptedChar).append(' '); // Append space after each letter
                keyIndex++;
            } else if (c == ' ') {
                // Preserve spaces between words
                ciphertext.append(' ');
            }
        }
        return ciphertext.toString().trim(); // Trim any trailing space
    }

    // Decryption method --> handleDecryption, decrypt methods
    private static void handleDecryption(Scanner scanner) {
        System.out.print("Enter ciphertext: ");
        String ciphertext = trim(scanner.nextLine());
        if (emptyInput(ciphertext, "Ciphertext") || !validateLettersOnly(ciphertext, "Ciphertext"))
            return;
        System.out.print("Enter key: ");
        String key = trim(scanner.nextLine());
        if (emptyInput(key, "Key") || !validateLettersOnly(key, "Key") || key.replaceAll(" ", "").isEmpty())
            return;
        String plaintext = decrypt(ciphertext, key);
        System.out.println("Plaintext: " + plaintext + " ");
    }

    private static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        ciphertext = toUpperCase(ciphertext); // Convert ciphertext to uppercase
        key = toUpperCase(key); // Convert key to uppercase
        int keyLength = key.length();
        int keyIndex = 0;

        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = 'A';
                char keyChar = key.charAt(keyIndex % keyLength);
                int shift = keyChar - base;
                char decryptedChar = (char) ((c - base - shift + 26) % 26 + base);
                plaintext.append(decryptedChar).append(' '); // Append space after each letter
                keyIndex++;
            } else if (c == ' ') {
                // Preserve spaces between words
                plaintext.append(' ');
            }
        }
        return plaintext.toString().trim(); // Trim any trailing space
    }

    // Brute force method
    private static void handleBruteForce(Scanner scanner) {
        System.out.print("Enter ciphertext: ");
        String ciphertext = trim(scanner.nextLine());
        if (emptyInput(ciphertext, "Ciphertext") || !validateLettersOnly(ciphertext, "Ciphertext"))
            return;
        final int MAX_KEY_LENGTH = 3; // Adjust as needed
        System.out.println("Attempting brute force decryption...");
        long startTime = System.currentTimeMillis();
        for (int keyLength = 1; keyLength <= MAX_KEY_LENGTH; keyLength++) {
            char[] key = new char[keyLength];
            bruteForceRecursion(ciphertext, key, 0, keyLength);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Brute force completed in " + (endTime - startTime) + " milliseconds.");
    }

    private static void bruteForceRecursion(String ciphertext, char[] key, int position, int keyLength) {
        if (position == keyLength) {
            String keyString = new String(key);
            String plaintext = decrypt(ciphertext, keyString);
            System.out.println("Key: " + keyString + " -> Plaintext: " + plaintext + " ");
            return;
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            key[position] = c;
            bruteForceRecursion(ciphertext, key, position + 1, keyLength);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("Enter the corresponding choices \n Press 1 for Encryption \n Press 2 for Decryption\n Press 3 for Brute force approach \n Press 4 to Exit");
            int choice = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    System.out.print("Enter your choice (1/2/3/4): ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (choice < 1 || choice > 4) {
                        System.out.println("Error: Please enter a valid choice between 1 and 4.");
                    } else {
                        validChoice = true; // Exit loop if valid choice is entered
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
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
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
