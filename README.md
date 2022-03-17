# Simple Yaml2Java Transformer

<details open>
<summary><b>Table of Contents</b></summary>
<!-- MarkdownTOC -->

* [About](#About)
* [Usage examples](#Usage-examples)
* [Maven dependency](#Maven-dependency)

<!-- /MarkdownTOC -->
</details>

## About

Provides quick transformation from __.yaml or .yml__ files in classloader or filesystem to java class instances.

Library based on [snakeyaml](https://mvnrepository.com/artifact/org.yaml/snakeyaml).

## Usage examples

#### Transforming content from yaml file to java object:

__file.yml:__

  ```yaml
string: value
integer: 1
integerArray:
    - 1
    - 2
    - 3
```

__Pojo.java:__
```java
package com.example;

public class Pojo {
    
    private String string;
    private int integer;
    private int[] integerArray;
    
    // Constructor, Getters n Setters, etc
}
```

__Clazz.java:__

```java
package com.example;

import dev.demmage.simpleyamlreader.YamlTransformer;

public class Clazz {

    private static final YamlTransformer transformer = new YamlTransformer();
    
    // Get file from class loader
    public Pojo getTransformedPojoFromClassLoaderFile() {
        return transformer.getTransformedObjectFromClassLoader("file.yml", Pojo.class);
    }

    // Get file from filesystem
    public Pojo getTransformedPojoFromFilesystem() {
        return transformer.getTransformedObjectFromDirectory("dir1/dir2/file.yml", Pojo.class);
    }
}
```

#### Result

```java
[main] INFO YamlTransformerTest - Pojo(string=value, integer=1, integerArray=[1, 2, 3])
```

## Maven dependency

```xml
TBD
```