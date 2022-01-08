package atrea.game;

import lombok.Getter;

public class Command {

    private @Getter String target;
    private @Getter String command;

    public Command(String target, String command) {
        this.target = target;
        this.command = command;
    }
}