# slug-maker

A Clojure library for generating url encoded slugs from strings

## Usage

```
(slug-maker/encode "आपके")
(slug-maker/encode "आपके" 200)
(slug-maker/encode-unique "आपके" ["%e0%a4%86%e0%a4%aa%e0%a4%95%e0%a5%87"])
(slug-maker/encode-unique "आपके" ["%e0%a4%86%e0%a4%aa%e0%a4%95%e0%a5%87"] 200)
```

## License

Distributed under the MIT License.
