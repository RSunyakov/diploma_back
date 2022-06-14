package ru.kpfu.itis.voice_assistans_skill.utility;

public enum Command {
    Test("тестирование"),
    List("список тестов"),
    Menu("меню"),
    Results("результаты");

    private final String command;

    public String getCommand() {
        return this.command;
    }

    Command(String command) {
        this.command = command;
    }

    public static Command ifContains(String line){
        for(Command enumValue:values()){
            System.out.println("test: " + enumValue);
            System.out.println("line1: " + line);
            if(line.contains(enumValue.command)){
                System.out.println("line: " + line);
                System.out.println("command: " + enumValue.command);
                return enumValue;
            }
        }
        return null;
    }
}
