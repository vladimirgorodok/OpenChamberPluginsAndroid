# OpenChamberPlugins · Android-каталог плагинов

Приложение-каталог плагинов OpenCode, MCP-серверов и дополнений OpenChamber.
Web-интерфейс в `app/src/main/assets/index.html` (+ `catalog.json`) выполняется
в WebView; команды установки запускаются через `Android.run(cmd)`
(`sh -c` в `MainActivity.kt`).

Данные каталога генерируются из `../openchamber-plugins/catalog.json`:

```sh
node gen-assets.js
```

## Сборка

```sh
./gradlew assembleRelease
```

На aarch64 можно использовать обёртку с переопределением aapt2 (см. `build-local.sh`):

```sh
./build-local.sh
```

APK: `app/build/outputs/apk/release/app-release.apk`.

Каталог сервера: `../openchamber-plugins` (`node server.js`, по умолчанию `http://127.0.0.1:8787/`).
