package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import models.JsonObjectsGlossary;
import models.MidjourneyResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTests {


    ClassLoader cl = FilesParsingTests.class.getClassLoader();

    @Test
    void pdfParseTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedPdf = $("a[href='_exports/junit-user-guide-6.0.3.pdf#overview']").download();
        PDF content = new PDF(downloadedPdf);
        assertThat(content.text).contains("Overview");
    }

    @Test
    void xlsParseTest() throws Exception {
        try (InputStream resourceAsStream = cl.getResourceAsStream("test.xlsx")) {
            XLS content = new XLS(resourceAsStream);
            assertThat(content.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue()).contains("Dulce"); // 2 столбец, 1 строка B1
        }
    }

    @Test
    void csvParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("test.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource))
        ) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)[1]).contains("name");
        }
    }

    @Test
    void zipTxtParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("test.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).contains("test.txt");
            }
        }
    }
    @Test
    void zipCsvParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("test.csv.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).contains("test.csv");
            }
        }
    }
    @Test
    void zipXlsxParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("test.xlsx.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).contains("test.xlsx");
            }
        }
    }
    @Test
    void zipPdfParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("test.pdf.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).contains("test.pdf");
            }
        }
    }

    @Test
    void jsonParseTest() throws Exception {
        Gson gson = new Gson();
        try (
                InputStream resource = cl.getResourceAsStream("test.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class); // jsonObject универсальный тип для json, но не очень удобно работать
            assertThat(jsonObject.get("title").getAsString()).isEqualTo("example glossary");
            assertThat(jsonObject.get("gloss_div").getAsJsonObject().get("title").getAsString()).isEqualTo("S");
            assertThat(jsonObject.get("gloss_div").getAsJsonObject().get("flag").getAsBoolean()).isTrue();
        }
    }
    @Test
    void jsonParseTestWithModel() throws Exception {
        Gson gson = new Gson();
        try (
                InputStream resource = cl.getResourceAsStream("test.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            JsonObjectsGlossary jsonObject = gson.fromJson(reader, JsonObjectsGlossary.class);
            assertThat(jsonObject.title).isEqualTo("example glossary");
            assertThat(jsonObject.glossDiv.title).isEqualTo("S");
            assertThat(jsonObject.glossDiv.flag).isTrue();
        }
    }

    @Test
    void parseMidjourneyJsonTest() throws Exception {
        // 1. Создаем ObjectMapper — это и есть основной инструмент Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. Получаем поток данных из файла, который лежит в src/test/resources/
        //    Убедись, что файл называется midjourney_response.json и лежит в правильной папке
        try (InputStream inputStream = cl.getResourceAsStream("mg_response.json")) {

            // 3. Самый главный шаг: Jackson читает JSON из потока
            //    и создает объект MidjourneyResponse, заполняя все поля
            MidjourneyResponse response = objectMapper.readValue(inputStream, MidjourneyResponse.class);

            // 4. Теперь мы можем проверять данные, как с любым Java-объектом
            //    (используем assertJ, как в твоих других тестах)

            // Проверяем простые поля
            assertThat(response.status).isEqualTo("success");
            assertThat(response.http_code).isEqualTo(200);
            assertThat(response.model).isEqualTo("Midjourney V6.1");

            // Проверяем вложенные объекты
            assertThat(response.prompt_info.original_prompt).contains("cosmic tiger");
            assertThat(response.prompt_info.parameters.aspect_ratio).isEqualTo("16:9");
            assertThat(response.prompt_info.parameters.stylize).isEqualTo(250);

            // Проверяем массив
            assertThat(response.generations).hasSize(3);

            // Проверяем первую генерацию
            MidjourneyResponse.Generation firstGen = response.generations.get(0);
            assertThat(firstGen.index).isEqualTo(1);
            assertThat(firstGen.status).isEqualTo("completed");
            assertThat(firstGen.image_url).isNotEmpty();

            // Проверяем генерацию с ошибкой
            MidjourneyResponse.Generation failedGen = response.generations.get(2);
            assertThat(failedGen.status).isEqualTo("failed");
            assertThat(failedGen.error_message).isEqualTo("Content moderation filter triggered");

            System.out.println("✅ JSON успешно распарсен! Найдено генераций: " + response.generations.size());
        }
    }

}
