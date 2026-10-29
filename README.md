# Сервис аренды жилья

## Стек
- **Backend:** Java 21, Spring Boot 3.4
- **Frontend:** React 18, Vite 6
- **СУБД:** PostgreSQL (в планах)
- **CI:** GitHub Actions
- **Качество кода:** ESLint, JaCoCo, commitlint

## Структура
backend/ — Spring Boot приложение
frontend/ — React приложение (Vite)
.github/ — CI пайплайн

## Управление исходным кодом

### Conventional Commits
Сообщения коммитов проверяются через Husky + commitlint.
Формат: `type(scope): description`, где type ∈ {feat, fix, docs, style, refactor, test, chore}.

Пример: `feat(auth): add JWT login endpoint`

### Стратегия ветвления (GitHub Flow)
- `main` — стабильная ветка, защищена (PR + обязательные checks)
- `develop` — интеграционная ветка
- `feature/*` — для каждой задачи

### Слияние
Только через Pull Request с прохождением CI (lint + test + build).

### Релизы
Теги вида `v0.1.0`, релизы создаются через GitHub Releases.

## Локальный запуск

### Backend
cd backend
mvn spring-boot:run

### Frontend
cd frontend
npm install
npm run dev
