package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager {
    static List<Command> commandList = new ArrayList<>();
    // Below instantiation of commands
    // @todo adding all commands
    private final Quit quitProgram = new Quit();
    private final AddCourse addCourse = new AddCourse();

    private void registerCommand (Command command) { commandList.add(command);}
    public List<Command> getCommands() {
        registerCommand(quitProgram);
        registerCommand(addCourse);
        return commandList;
    }
}
