package atrea.server.game.entities.ecs.command;

public class MessageScript extends Script {

    private String message;

    public MessageScript(String args) {
        super(args);
    }

    @Override public void setUpScript(String args) {
        String[] values = args.split(",");

        duration = Integer.parseInt(values[0]);
        this.message = values[1];
    }

    @Override public void script() {

    }
}