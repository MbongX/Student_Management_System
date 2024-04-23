import User.AccessLevel;
import User.Person.Course;
import commands.Command;
import commands.CommandsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputManager {
    private static final Scanner scanner = new Scanner(System.in);
    public static List<Course> courses = new ArrayList<>();
    protected AccessLevel userRole;

    public static void startProgram() {
        // @TODO 1. Loading date from CSV files or Json file
        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        CommandsManager commandsManager = new CommandsManager();
        while (true) {
            System.out.println("Select by number your choice");
            displayCommands(commandsManager);
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("q")) {
                System.out.println("Exiting the program.");
                break;
            }

        }

    }

    // Add role as argument to display commands according to each role ?
    private static void displayCommands(CommandsManager commandsManager) {
        System.out.println("Commands available:");
        for (Command command : commandsManager.getCommands()) {
            System.out.println(STR."\{command.getCommandNumber()} - \{command.getCommandName()}");
        }
    }

}
