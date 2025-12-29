package ru.mephi.hibernatefinal;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("ru.mephi.hibernatefinal.repository")
public class HibernateFinalApplicationTests {
    // Для запуска его в IDE. Прогонит все тесты в пакете repository
}
