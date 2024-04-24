package commands;

public class Quit implements Command{
    @Override
    public int getCommandNumber() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "Quit";
    }

    @Override
    public void execute() {
        System.out.println("Exiting the program.");
        System.exit(0);;
    }
}
