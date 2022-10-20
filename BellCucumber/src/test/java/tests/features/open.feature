Feature: Яндекс

  @Run
  Scenario Outline: Сервис яндекса
    Given Открыть yandex.ru
    Then  Клик по иконке яндекс маркет и переход на него
    When Выберем из каталога раздел из левого столбца '<раздел>' и значение '<товар>'
    And Выберем производителя: '<производитель>'
    And Проверяем, что на всех страницах содержится товар с названием: '<производитель>'
    Examples:
      | раздел      | товар     | производитель |
      | Электроника | Смартфоны | Apple         |




