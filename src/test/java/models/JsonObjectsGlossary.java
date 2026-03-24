package models;

import com.google.gson.annotations.SerializedName;
//Модель или DTO для файла test.json
public class JsonObjectsGlossary {
    public String title;
    @SerializedName("gloss_div")
    public GlossDiv glossDiv;

    public static class GlossDiv {
        public String title;
        public Boolean flag;
    }
}
