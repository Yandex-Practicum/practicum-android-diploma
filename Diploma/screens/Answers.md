# Ответы на вопросы по декомпозиции

Документ содержит в себе набор ответов на дополнительные вопросы студентов о декомпозиции дипломного приложения.

## Экран поиска вакансий

> Как выглядит плейсхолдер логотипа компании, если идёт загрузка картинки или произошла ошибка загрузки логотипа?

Иконку плейсхолдера можно
отыскать [на макетах](https://www.figma.com/file/sNxcqFQJNbNwM2SP1qjmKY/HH-(YP)?type=design&node-id=24%3A4324&mode=design&t=rFuAvXhlqtbsYFvO-1).

> Как выглядит экран, если у нас пустая выдача (нет результатов или запрос ещё не сделан)?

Ответ на оба вопроса можно отыскать на макетах:

- [Если пользователь ещё не сделал ни одного запроса](https://www.figma.com/file/sNxcqFQJNbNwM2SP1qjmKY/HH-(YP)?type=design&node-id=24%3A4461&mode=design&t=Ii9ZFLfx87Xr3Sfw-1).
- [Если поиск не дал результатов](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A6918&mode=design&t=hyeCQURU44AYF5Vy-1).

> Как выглядит выдача, если при запросе ПЕРВОЙ страницы случилась ошибка?

На это есть ряд дополнительных макетов:

- Если у пользователя не было интернета, экран
  выглядит [вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A6832&mode=design&t=hyeCQURU44AYF5Vy-1).
- Если случилась ошибка сервера, экран
  выглядит [вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=7%3A2926&mode=design&t=hyeCQURU44AYF5Vy-1).

> Как выглядит выдача, когда у нас при запросе ВТОРОЙ ИЛИ ПОСЛЕДУЮЩИХ страниц случились ошибки?

В этом случае крутящийся progress bar скрывается, и появляется `Toast` с текстом ошибки (либо "Произошла ошибка
загрузки, попробуйте ещё раз", либо "Нет интернет-соединения").

> Как выглядит экран загрузки выдачи при запросе ПЕРВОЙ страницы?

На это есть отдельный
макет, [выглядит вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A6509&mode=design&t=hyeCQURU44AYF5Vy-1).

> Нужно ли при отображении числа найденных вакансий разбивать число на разряды?

Нет, не нужно.

## Фильтрация

> Нужно ли при вводе ожидаемой зарплаты разбивать вводимое число на разряды?

Нет, не нужно.

> Можно ли указывать дробные числа при вводе ожидаемой зарплаты?

Нет, можно вводить только целые числа.

> Нужно ли запрещать ввод нескольких стартовых нулей в поле ожидаемой зарплаты?

Нет, не нужно.

> Как выглядит экран выбора региона, если мы получили пустой список в результате?

На это есть отдельный
макет, [выглядит вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A7353&mode=design&t=hyeCQURU44AYF5Vy-1).

> Как выглядит экран выбора региона, если в результате запроса произошла ошибка?

- Если у пользователя не было интернета, экран
  выглядит [вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A6832&mode=design&t=hyeCQURU44AYF5Vy-1).
- Если случилась ошибка сервера, экран
  выглядит [вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=7%3A2926&mode=design&t=hyeCQURU44AYF5Vy-1).
- Если случилась какая-то другая ошибка, экран
  выглядит [вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=9%3A7698&mode=design&t=hyeCQURU44AYF5Vy-1).

## Экран деталей вакансии

> Как выглядит плейсхолдер логотипа компании, если идёт загрузка картинки или произошла ошибка загрузки этого логотипа?

Иконку плейсхолдера можно
отыскать [на макетах](https://www.figma.com/file/sNxcqFQJNbNwM2SP1qjmKY/HH-(YP)?type=design&node-id=24%3A4324&mode=design&t=rFuAvXhlqtbsYFvO-1).

> Как выглядит экран, когда идёт загрузка деталей вакансии?

На это есть отдельный макет, [выглядит вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=30%3A1343&mode=design&t=oH3W2XAaSfCBfm0L-1).

> Как выглядит экран, если в ходе загрузки деталей вакансий произошла ошибка?

На это есть отдельный макет, [выглядит вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=30%3A1433&mode=design&t=oH3W2XAaSfCBfm0L-1).


## Экран избранных вакансий

> Как выглядит плейсхолдер логотипа компании, если идёт загрузка картинки или в ходе загрузки картинки произошла ошибка?

Иконку плейсхолдера можно
отыскать [на макетах](https://www.figma.com/file/sNxcqFQJNbNwM2SP1qjmKY/HH-(YP)?type=design&node-id=24%3A4783&mode=design&t=rFuAvXhlqtbsYFvO-1).

> Как выглядит экран, если у нас пустая выдача (в базе нет ни одной избранной вакансии)?

Ответ можно
найти [на макете](https://www.figma.com/file/sNxcqFQJNbNwM2SP1qjmKY/HH-(YP)?type=design&node-id=24%3A7699&mode=design&t=rFuAvXhlqtbsYFvO-1)

> Есть ли ограничение на количество вакансий, которые можно добавить в "Избранное"?

Ограничений нет, можно добавлять сколько угодно вакансий.

> Как выглядит выдача, если при запросе ПЕРВОЙ страницы случилась ошибка?

На это есть отдельный
макет, [выглядит вот так](https://www.figma.com/file/NbfNWDqi3nGTIqtqH5X1Fz/HH-Sample-(YP)?type=design&node-id=8%3A3194&mode=design&t=hyeCQURU44AYF5Vy-1).

> Нужно ли хранить логотип компании в хранилище приложения, чтобы его можно было видеть в офлайн режиме?

Нет, не нужно. Картинки должны загружаться из интернета, а если интернета нет - должен отображаться плейсхолдер.
