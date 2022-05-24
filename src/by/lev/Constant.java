package by.lev;

public enum Constant {
    //Controller
    INCORRECT_INPUT(">>> НЕВЕРНЫЙ ВВОД <<<");

    private String message;

    Constant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
