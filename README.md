# Тестовое задание DT Developer Text
## Запуск
Запуск производится через команду 
```sh
./gradlew run
```
## Интерфейс
### Отображение Галереи
Для отображения галереи я выбрал три изображения. При попытке загрузить 500 картинок приложение загружалось более 10 секунд. Чтобы оптимизировать работу приложения и минимизировать нагрузку на память, было принято решение загружать только пять изображений одновременно. Это позволяет быстро переключаться между ними без значительных задержек.

### Поиск Изображений
В строке поиска галереи пользователи могут вводить как простое имя файла, так и имя с расширением. Это добавляет гибкости и удобства в процессе поиска нужного изображения.

### Просмотр в Полном Экране
При нажатии на центральное изображение происходит его отображение на весь экран.
## Сторонние файлы
### Main.properties
Из данного файла загружаются важные настройки приложения, включая:

1) Начальная Папка: Устанавливается директория, в которой хранятся изображения.

2) Перечисление Расширений: Содержит список допустимых расширений для изображений. Это гарантирует, что приложение загружает только поддерживаемые форматы, улучшая функциональность и предотвращая возможные ошибки.
