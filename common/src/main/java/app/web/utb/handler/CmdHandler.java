package app.web.utb.handler;

public interface CmdHandler <T> {
    void handle(T command);
}
