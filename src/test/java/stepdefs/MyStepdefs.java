package stepdefs;

import lombok.extern.slf4j.Slf4j;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.И;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class MyStepdefs {

    @Дано("^(?:пользователь|он) зашел настраницу \"([^\"]*)\"$")
    public static void goTo(String site) {
        open(site);
    }

    @И("^вывести в консоль значение поля \"([^\"]*)\"$")
    public void consoleLog(String elementName) {
        switch (elementName) {
            case "Первого в поиске":
                System.out.println(getElement(By.xpath("//div[@class = 'popup__content']//li[1]")).innerText());
                break;
        }
    }

    @И("^(?:пользователь|он) нажимает на (?:кнопку|поле|элемент|текст|чекбокс|радиобаттон) \"([^\"]*)\"$")
    public void clickOnElement(String elementName) {
        switch (elementName) {
            case "Маркет":
                getElement(By.xpath("//a[text() = 'Маркет']")).click();
                break;
            case "Все категории":
                getElement(By.xpath("//noindex//span[text() = 'Все категории']")).click();
                break;
            case "Электроника":
                getElement(By.xpath("//span[text() = 'Электроника']")).click();
                break;
            case "Смартфоны":
                getElement(By.xpath("//a[text() = 'Смартфоны']")).click();
                break;
            case "Samsung":
                getElement(By.xpath("//legend[text() ='Производитель']/..//span[text() = 'Samsung']")).click();
                break;
            case "Имя первого товара":
                getElement(By.xpath("(//div[@class = 'n-snippet-cell2__header']//a)[1]")).click();
                break;
            case "Наушники и Bluetooth-гарнитуры":
                getElement(By.xpath("//a[text() = 'Наушники и Bluetooth-гарнитуры']")).click();
                break;
            case "Beats":
                getElement(By.xpath("//legend[text() ='Производитель']/..//span[text() = 'Beats']")).click();
                break;
            case "Найти":
                getElement(By.xpath("")).click();
                break;
            case "AlfaBank.ru":
                getElement(By.xpath("//*[text() =  'AlfaBank.ru']")).click();
                break;
            case "Вакансии":
                getElement(By.xpath("")).click();
                break;
            case "О нас":
                getElement(By.xpath("//a[text() ='О нас']")).click();
                break;
            case "О наdс":
                getElement(By.xpath("//a[text() ='О нас']")).click();
                break;
        }
    }

    @И("^(?:пользователь|он) вводит \"([^\"]*)\" в поле \"([^\"]*)\"$")
    public void sendTextToField(String text, String elementName) {
        switch (elementName) {
            case "Цена от":
                getElement(By.xpath("//legend[text() ='Цена, \u20BD']/..//input[@name = 'Цена от']")).scrollTo().setValue(text);
                break;
            case "Цена до":
                getElement(By.xpath("//legend[text() ='Цена, \u20BD']/..//input[@name = 'Цена до']")).scrollTo().setValue(text);
                break;
            case "Поиск яндекс":
                getElement(By.xpath("//class[@name ='text']")).scrollTo().setValue(text);
                break;
            case "Поиск гугл":
                getElement(By.xpath("//input[@name ='q']")).scrollTo().setValue(text);
                break;
        }
    }

    @И("^(?:пользователь|он) запоминает текст элемента \"([^\"]*)\"$")
    public void saveTextOnMemory(String elementName) {
        switch (elementName) {
            case "Имя первого товара":
                String text = getElement(By.xpath("(//div[@class = 'n-snippet-cell2__header']//a)[1]")).getText();
                putMemoryValue(elementName, text);
                break;
        }
    }

    @И("^(?:пользователь|он) вводит \"([^\"]*)\" в поле \"([^\"]*)\" и нажимает на Enter$")
    public void clickEnter(String text, String elementName) {
        switch (elementName) {
            case "Поиск гугл":
                getElement(By.xpath("//input[@name ='q']")).scrollTo().setValue(text).pressEnter();
                break;
        }
    }

    @И("^(?:пользователь|он) беспощадно убивает злые кружочки$")
    public void killBolls() throws InterruptedException {
        for (; ;) {
            if (getElement(By.xpath("//div[@class ='zr_zergling_container']")).isDisplayed()){
                int i;
                for (i = 0; i<3; i++){
                    getElement(By.xpath("//div[@class ='zr_zergling_container']")).scrollTo().click();
                }
            }
            else {
                Thread.sleep(2000);
            }
        }
    }

    @И("^(?:пользователь|он) сверяет (?:значение|текст) (?:ключа|элемента) \"([^\"]*)\" из памяти со значением в поле \"([^\"]*)\"$")
    public void verifyTextValueOutMemory(String memoryNameKey, String elementName) {
        switch (elementName) {
            case "Заголовок описания":
                String valueMemory = getMemoryValue(memoryNameKey);
                getElement(By.xpath("//h1")).shouldHave(text(valueMemory));
                break;
        }
    }

    //для сохранения переменных и их значений -------------------------
    private Map<String, String> memory = new HashMap<>();

    public void putMemoryValue(String key, String value){
        this.memory.put(key, value);
        log.debug("В память была положена пара {} {}", key, value);
    }

    public String getMemoryValue(String key){
        if (!memory.containsKey(key)){
            String msg = "В памяти нет значения для ключа " + key;
            log.error(msg);
        }

        String value = this.memory.get(key);
        log.debug("Из памяти было извлечено значение {} по ключу {}", value, key);
        return this.memory.get(key);
    }
    //-------------------------------------------------------

    @И("^установлено ожидание в \"([^\"]*)\" секунд$")
    public void userWait(String time) throws Throwable {
        Thread.sleep((int) Double.parseDouble(time) * 1000);
    }

    @И("^(?:пользователь|он) сохраняет текст блока \"([^\"]*)\" в файл$")
    public void saveTextOnFile(String elementName) {
        switch (elementName) {
            case "Имя первого товара":
                String text = getElement(By.xpath("(//div[@class = 'n-snippet-cell2__header']//a)[1]")).getText();
                putMemoryValue(elementName, text);
                break;
        }
    }
}