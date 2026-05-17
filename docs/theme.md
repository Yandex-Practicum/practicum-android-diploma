# Тема приложения

Документация по использованию цветов, типографики и темы в Compose-экранах.

## Что добавлено в проекте

| Файл | Что внутри |
|---|---|
| `app/src/main/java/ru/practicum/android/diploma/ui/theme/Color.kt` | Все цвета палитры из Figma как Compose `Color` |
| `app/src/main/java/ru/practicum/android/diploma/ui/theme/Type.kt` | Шрифт `YS Display` и `AppTypography` со слотами Material 3 |
| `app/src/main/java/ru/practicum/android/diploma/ui/theme/Theme.kt` | `AppTheme` — обёртка `MaterialTheme` со светлой и тёмной схемами |
| `app/src/main/res/font/ys_display_*.ttf` | TTF-файлы шрифта YS Display (Regular / Medium / Bold) |
| `app/src/main/res/values/themes.xml` | XML-тема `Theme.PracticumAndroidDiploma` — наследник `Theme.Material3.DayNight.NoActionBar`, без кастомизации |
| `app/src/main/res/values-night/themes.xml` | То же для тёмной темы (`-night` qualifier) |

**Единственный источник цветов — `Color.kt`**. В XML-теме цвета не описаны намеренно: первые ~миллисекунды запуска Activity статус-бар и фон будут в дефолтных Material 3 цветах, а потом Compose перекрасит окно в наши цвета. Это допустимая мелкая визуальная вспышка, зато избегаем дублирования палитры между Kotlin и XML.

## Как использовать тему на экране

Любой Compose-экран нужно оборачивать в `AppTheme { ... }` — это даёт доступ к цветам и типографике через `MaterialTheme.*`.

```kotlin
@Composable
fun SearchScreen() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            // содержимое экрана
        }
    }
}
```

В реальном приложении `AppTheme` будет один раз в `MainActivity.setContent { }`, а не в каждом экране. Но в `@Preview` оборачиваем каждый экран отдельно — см. секцию [Превью](#превью).

## Цвета

### Откуда брать цвет

В composable **не пиши** `Color(0xFF3772E7)` — это нарушение критерия ревью «цвета не хардкодятся». Бери из темы:

```kotlin
Text(text = "Поиск", color = MaterialTheme.colorScheme.primary)
```

### Маппинг Figma → Material 3

| Когда нужно | Бери | Что это в Figma |
|---|---|---|
| Акцент (кнопки, ссылки, активная вкладка) | `MaterialTheme.colorScheme.primary` | `Blue` (#3772E7) |
| Текст/иконка на акцентном фоне | `MaterialTheme.colorScheme.onPrimary` | `White Universal` (#FDFDFD) |
| Фон экрана | `MaterialTheme.colorScheme.background` | `White[Day]` в Light, `White[Night]` в Dark |
| Основной текст и иконки | `MaterialTheme.colorScheme.onBackground` | `Black[Day]` в Light, `Black[Night]` в Dark |
| Фон карточек / поверхностей | `MaterialTheme.colorScheme.surface` | Совпадает с `background` |
| Текст на surface | `MaterialTheme.colorScheme.onSurface` | Совпадает с `onBackground` |
| Подсказки, серый текст, иконки-плейсхолдеры | `MaterialTheme.colorScheme.onSurfaceVariant` | `Gray` (#AEAFB4) |
| Разделители, рамки полей | `MaterialTheme.colorScheme.outline` | `LightGray` (#E6E8EB) в Light, `Gray` в Dark |
| Ошибка (текст, иконка, плейсхолдер) | `MaterialTheme.colorScheme.error` | `Red` (#F56B6C) |
| Полупрозрачный затемнитель (для модалок) | `MaterialTheme.colorScheme.scrim` | `Background` (rgba(26,27,34,0.5)) |

Цвета **автоматически меняются** при переключении системной темы (Light ↔ Dark) — благодаря `isSystemInDarkTheme()` внутри `AppTheme`.

### Universal-цвета

Иногда нужен цвет, который **не меняется** при смене темы (например, белый текст на цветной кнопке — он белый и в светлой, и в тёмной теме). Для этого используем `BlackUniversal` / `WhiteUniversal` напрямую из пакета:

```kotlin

Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
    Text("Применить", color = WhiteUniversal)  // всегда белый
}
```

Через `MaterialTheme.colorScheme.onPrimary` обычно тоже работает — но если нужен явный «не зависит от темы», бери `WhiteUniversal` / `BlackUniversal`.

## Типографика

Стили из Figma уже разложены по слотам Material 3. Используй `MaterialTheme.typography.*`:

| Figma | Слот Material 3 | Где применять |
|---|---|---|
| `Bold/32` | `MaterialTheme.typography.headlineLarge` | Заголовки экранов: «Главная», «Фильтрация», «Избранное» |
| `Medium/22` | `MaterialTheme.typography.headlineMedium` | Подзаголовки секций, крупные элементы |
| `Medium/16` | `MaterialTheme.typography.titleMedium` | Заголовок вакансии в списке, текст кнопок |
| `Regular/16` | `MaterialTheme.typography.bodyMedium` | Основной текст: имя работодателя, зарплата, описание |
| `Regular/12` | `MaterialTheme.typography.labelSmall` | Мелкие подписи, метки табов, footnote |

### Пример

```kotlin
Column {
    Text(
        text = "Поиск",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Text(
        text = "Введите запрос",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
```

Размер шрифта, межстрочный интервал и FontWeight уже заданы в `AppTypography` — отдельно их указывать не нужно.

### Если в Figma какой-то размер не в этой таблице

В макете может встретиться, например, `Bold/22` или `Medium/14` — стили, которых нет в нашей `AppTypography`. Тогда:

1. **Не хардкодь** `fontSize = 22.sp` в composable.
2. **Добавь** этот стиль в `Type.kt` (`AppTypography`), выбрав подходящий слот Material 3 — `titleLarge`, `bodyLarge`, `labelMedium` и т.д.
3. Используй через `MaterialTheme.typography.<новыйСлот>`.

## Превью

Каждой composable желательно делать `@Preview` — IDE покажет компонент без запуска приложения. Шаблон:

```kotlin

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun MyComponentPreview() {
    AppTheme {
        MyComponent(/* mock-данные */)
    }
}
```

Что важно:
- **Две `@Preview`** — Light и Dark. Это сразу покажет, что компонент корректно выглядит в обеих темах.
- **`AppTheme { ... }`** обязательно — без него `MaterialTheme.colorScheme` вернёт дефолтные Material 3 цвета, а не наши.
- **`showBackground = true`** — рендерит фон экрана, иначе будет прозрачно.
- Превью-функция **`private`** — её не должно быть в публичном API.

## Антипаттерны — чего НЕ делать

```kotlin
// ❌ Хардкод hex-цвета — нарушение критерия ревью.
Text(text = "Hello", color = Color(0xFF3772E7))

// ❌ Хардкод размера шрифта.
Text(text = "Hello", fontSize = 16.sp, fontWeight = FontWeight.Bold)

// ❌ Использование FontFamily.Default — потеряется YS Display.
Text(text = "Hello", fontFamily = FontFamily.Default)

// ❌ Composable без AppTheme в превью — увидишь дефолтные Material-цвета.
@Preview
@Composable
fun BadPreview() { MyComponent() }
```

```kotlin
// ✅ Цвет из темы.
Text(text = "Hello", color = MaterialTheme.colorScheme.primary)

// ✅ Стиль из типографики.
Text(text = "Hello", style = MaterialTheme.typography.titleMedium)

// ✅ Превью с темой.
@Preview(showBackground = true)
@Composable
private fun GoodPreview() { AppTheme { MyComponent() } }
```

## Если нужен новый цвет

Если в Figma появился цвет, которого нет в `Color.kt`:

1. Добавь `val` в `Color.kt`:
   ```kotlin
   val Orange = Color(0xFFFF6F00)
   ```
2. (По возможности) подбери ему слот Material 3 в `lightColorScheme` / `darkColorScheme` в `Theme.kt` — например, `secondary` или `tertiary`.
3. Используй через `MaterialTheme.colorScheme.secondary`. Если слот не подошёл по смыслу — импортируй напрямую `import ru.practicum.android.diploma.ui.theme.Orange` (но это редкий случай).
4. В XML-теме цвета не дублируем — она намеренно минимальная (см. секцию «Что добавлено в проекте»).

## Вопросы

Если что-то непонятно или нашёл расхождение макета с темой — пиши в чат команды.
