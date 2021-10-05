package ui;

public class InputValidator {
    private static final String INVALID_INPUT_STRING = "Вы ввели некорректные данные! Попробуйте снова!";

    public static boolean isInputValid(String input, String[] values){
        input = input.trim();
        for(String value: values){
            if(input.equals(value)){
                return true;
            }
        }
        System.out.println(INVALID_INPUT_STRING);
        return false;
    }
}
