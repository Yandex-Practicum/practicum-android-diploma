
# Правила разработки

- [Именование](#именование)
- [Оформление коммитов](#оформление-коммитов)

# Именование

Общее правило: следуем [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html). Имена пишем на английском, без транслита и без сокращений, кроме общепринятых (`id`, `url`, `dto`, `ui`).

## Код

| Что | Стиль | Пример |
|---|---|---|
| Пакет | строчные буквы, без `_` | `ru.practicum.android.diploma.domain.models` |
| Класс, интерфейс, object, enum | PascalCase | `VacancyDetails`, `SearchInteractor` |
| Composable-функция | PascalCase, существительное | `VacancyListItem`, `SearchScreen` |
| Функция, переменная, параметр | camelCase, функция начинается с глагола | `searchVacancies()`, `vacancyId` |
| Boolean | camelCase с `is` / `has` / `should` | `isFavorite`, `hasSalary` |
| Константа (`const val`, значение в `companion object`) | UPPER_SNAKE_CASE | `PAGE_SIZE`, `SEARCH_DEBOUNCE_DELAY_MS` |
| Значение enum | UPPER_SNAKE_CASE | `NO_INTERNET`, `SERVER_ERROR` |
| Размер в Compose (`Dimens.kt`, `private val`) | camelCase | `paddingMedium`, `cardCornerRadius` |

Один публичный класс - один файл, имя файла совпадает с именем класса.

## Суффиксы по слоям

| Слой | Что | Шаблон имени | Пример |
|---|---|---|---|
| domain | Модель | без суффикса | `Vacancy`, `Salary`, `FilterSettings` |
| domain | Интерфейс интерактора / реализация | `<Feature>Interactor` / `<Feature>InteractorImpl` | `SearchInteractor`, `SearchInteractorImpl` |
| domain | Интерфейс репозитория | `<Feature>Repository` | `VacancyRepository` |
| data | Реализация репозитория | `<Feature>RepositoryImpl` | `VacancyRepositoryImpl` |
| data | Модели сети (запрос / ответ) | `<Name>Request` / `<Name>Response`, `<Name>Dto` | `VacancySearchRequest`, `VacancyDto` |
| data | Retrofit-интерфейс, сетевой клиент | `<Name>Api`, `<Name>Client` | `VacancyApi`, `RetrofitNetworkClient` |
| data | Room: таблица, DAO, база | `<Name>Entity`, `<Name>Dao`, `AppDatabase` | `VacancyEntity`, `VacancyDao` |
| data | Маппер | `<Name>Mapper` или функции-расширения `toDomain()` / `toEntity()` | `VacancyMapper` |
| presentation | ViewModel | `<Screen>ViewModel` | `SearchViewModel` |
| presentation | Состояние экрана | `<Screen>State` (sealed interface) | `SearchState.Loading` |
| ui | Фрагмент | `<Screen>Fragment` | `VacancyFragment` |
| ui | Composable экрана | `<Screen>Screen` | `FavoritesScreen` |
| ui | Общий компонент | по смыслу, без суффикса | `VacancyListItem`, `Placeholder` |
| ui | Preview | `<Name>Preview`, `private` | `VacancyListItemPreview` |
| di | Koin-модуль | `<layer>Module` | `dataModule`, `viewModelModule` |

Приватное изменяемое состояние во ViewModel - с подчёркиванием, публичное - без: `private val _state` / `val state`.

## Ресурсы Android

Только строчные буквы, слова разделяем `_`, в начале - префикс типа ресурса.

| Ресурс | Шаблон | Пример |
|---|---|---|
| Иконка, иллюстрация (`drawable`) | `ic_<name>_<size>` | `ic_search_24`, `ic_logo_placeholder_32`, `ic_no_internet_328x223` |
| Layout | `activity_<name>`, `fragment_<name>`, `item_<name>` | `activity_root`, `fragment_search` |
| Строка | `<feature>_<meaning>`, общие - без префикса фичи | `search_hint`, `vacancy_salary_not_specified`, `retry` |
| Файл строк | `strings_<feature>.xml`, общие - `strings.xml` | `strings_search.xml` |
| Цвет | по смыслу, не по значению | `blue`, `red`, `light_gray` |
| Граф навигации | `<name>_navigation_graph` | `main_navigation_graph` |
| Меню | `<name>_menu` | `bottom_navigation_menu` |
| id в XML | camelCase | `searchFragment`, `bottomNavigationView` |

Размер иконки - в dp из макета: для квадратной одно число (`_24`), для остальных `<ширина>x<высота>` с латинской `x` (`_328x223`).

# Оформление коммитов

В проекте используется формат **Conventional Commits**.

## Формат коммита

`type(scope): description`

- `type` — тип изменения.
- `scope` — область проекта, которую затрагивает изменение.
- `description` — краткое описание изменений.

## Типы коммитов

| Тип | Описание |
|---|---|
| `feat` | Добавление новой функциональности |
| `fix` | Исправление ошибок |
| `refactor` | Рефакторинг без изменения функциональности |
| `style` | Форматирование и стилистические изменения кода |
| `test` | Добавление и изменение тестов |
| `docs` | Изменение документации |
| `build` | Изменение конфигурации сборки и зависимостей |
| `chore` | Прочие технические изменения |

## Области проекта (scope)

- `search` — поиск вакансий
- `vacancy` — информация о вакансиях
- `pagination` — постраничная загрузка
- `favorites` — избранное
- `filter` — фильтрация
- `team` — экран команды
- `navigation` — навигация
- `network` — сетевые запросы
- `database` — база данных
- `di` — внедрение зависимостей
- `ui` — UI-компоненты
- `design-system` — дизайн-система
- `gradle` — настройки Gradle
- `git` — настройки Git
- `core` - общие утилиты и классы
- `app` - Application, RootActivity, манифест

## Правила оформления

1. Использовать английский язык.
2. Писать описание строчными буквами.
3. Начинать описание с глагола: `add`, `fix`, `update`, `remove`, `implement`.
4. Не ставить точку в конце.
5. Формулировать описание кратко и понятно.
6. Один коммит должен содержать одно логически связанное изменение.

## Примеры

```text
feat(search): add vacancy search screen
fix(network): handle server errors
refactor(navigation): simplify routes
test(search): add viewmodel tests
docs(git): add commit conventions
chore(git): update gitignore
```
