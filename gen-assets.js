// gen-assets.js — генерирует app/src/main/assets/catalog.json и вплетает
// каталог из openchamber-plugins/catalog.json в assets/index.html.
// Запуск: node gen-assets.js  (в корне проекта OpenChamberPluginsAndroid)
const fs = require("node:fs");
const path = require("node:path");

const ROOT = __dirname;
const CATALOG_SRC = path.join(ROOT, "../openchamber-plugins/catalog.json");
const OUT = path.join(ROOT, "app/src/main/assets/catalog.json");
const PAGE = path.join(ROOT, "app/src/main/assets/index.html");

const catalog = JSON.parse(fs.readFileSync(CATALOG_SRC, "utf8"));
fs.writeFileSync(OUT, JSON.stringify(catalog.entries, null, 2));

let html = fs.readFileSync(PAGE, "utf8");
const placeholder = "/*CATALOG_PLACEHOLDER*/ [];";
if (!html.includes(placeholder)) throw new Error("placeholder не найден в index.html");
html = html.replace(placeholder, JSON.stringify(catalog.entries, null, 2));
fs.writeFileSync(PAGE, html);
console.log("catalog.json и index.html обновлены:", catalog.entries.length, "записей");
