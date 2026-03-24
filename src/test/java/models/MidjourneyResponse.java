package models;

import java.util.List;

public class MidjourneyResponse {
    // Model or DTO
    // Поля должны называться ТОЧНО так же, как ключи в JSON
    public String status;
    public int http_code;
    public String generation_id;
    public String model;
    public PromptInfo prompt_info; // Вложенный объект
    public String created_at;
    public String user_id;
    public List<Generation> generations; // Массив

    // Внутренний класс для "prompt_info"
    public static class PromptInfo {
        public String original_prompt;
        public String translated_prompt;
        public Parameters parameters; // Еще один вложенный объект
    }

    // Внутренний класс для "parameters"
    public static class Parameters {
        public String aspect_ratio;
        public int stylize;
        public int chaos;
        public String quality;
        public String version;
    }

    // Внутренний класс для элементов массива "generations"
    public static class Generation {
        public int index;
        public String image_url;
        public String status;
        public String error_message; // Этого поля может не быть, это нормально
    }
}