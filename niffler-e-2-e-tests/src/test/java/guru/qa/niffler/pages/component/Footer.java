package guru.qa.niffler.pages.component;

import static com.codeborne.selenide.Selenide.$;

public class Footer extends BaseComponent<Footer> {

    public Footer() {
        super($(".footer"));
    }
}
