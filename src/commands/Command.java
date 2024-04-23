package commands;

public interface Command {
    int getCommandNumber();
    String getCommandName();
    void execute();
}
