// gen-assets.js — генерирует app/src/main/assets/catalog.json и вплетает
// каталог из openchamber-plugins/catalog.json в assets/index.html.
// Запуск: node gen-assets.js  (в корне проекта OpenChamberPluginsAndroid)
// Идемпотентно: работает и для шаблона с /*CATALOG_PLACEHOLDER*/, и повторно после вплетения.
const fs = require("node:fs");
const path = require("node:path");

const ROOT = __dirname;
const CATALOG_SRC = path.join(ROOT, "../openchamber-plugins/catalog.json");
const OUT = path.join(ROOT, "app/src/main/assets/catalog.json");
const PAGE = path.join(ROOT, "app/src/main/assets/index.html");

const catalog = JSON.parse(fs.readFileSync(CATALOG_SRC, "utf8"));
fs.writeFileSync(OUT, JSON.stringify(catalog.entries, null, 2));

const json = JSON.stringify(catalog.entries, null, 2);
let html = fs.readFileSync(PAGE, "utf8");
const inlinedRe = /const CATALOG = \[[\s\S]*?\n\]\s*;?/;
if (html.includes("/*CATALOG_PLACEHOLDER*/")) {
  html = html.replace(/\/\*CATALOG_PLACEHOLDER\*\/\s*\[\s*\]\s*;?/, "const CATALOG = " + json + ";");
} else if (inlinedRe.test(html)) {
  html = html.replace(inlinedRe, "const CATALOG = " + json + ";");
} else {
  throw new Error("index.html: не найден ни placeholder, ни const CATALOG");
}
fs.writeFileSync(PAGE, html);
console.log("catalog.json и index.html обновлены:", catalog.entries.length, "записей");
