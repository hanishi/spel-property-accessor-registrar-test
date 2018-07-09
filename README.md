# spel-property-accessor-registrar-test

With the

```
   @Bean
    public SpelPropertyAccessorRegistrar spelPropertyAccessorRegistrar(JsonPropertyAccessor jsonPropertyAccessor) {
        return new SpelPropertyAccessorRegistrar(jsonPropertyAccessor);
    }
```

The test will fail with the following

```
java.lang.AssertionError: 
Expected: is "10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.3"
     but: was "1.0E139"
Expected :10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.3
Actual   :1.0E139
```

The test will pass however when the `@Bean` definiton is the following:
```
    @Bean
    public SpelPropertyAccessorRegistrar spelPropertyAccessorRegistrar(JsonPropertyAccessor jsonPropertyAccessor) {
        Map<String, PropertyAccessor> map = new HashMap<>();
        map.put("someKey", jsonPropertyAccessor);
        return new SpelPropertyAccessorRegistrar(map);
    }
```
