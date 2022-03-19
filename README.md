[![](https://jitpack.io/v/DeMmAge/Simple-Yaml-Transformer.svg)](https://jitpack.io/#DeMmAge/Simple-Yaml-Transformer)

# Simple Yaml2Java Transformer

<details open>
<summary><b>Table of Contents</b></summary>
<!-- MarkdownTOC -->

* [About](#About)
* [Usage examples](#Usage-examples)
* [Dependencies](#Dependencies)
    * [Maven](#Maven)
    * [Jitpack](#Jitpack)
    * [Github packages](#Github-packages)
    * [Maven Central](#Maven-Central)
    * [Gradle](#Gradle)
* [Contacts, Trello Board, Chats](#Contacts)

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
[main]INFO YamlTransformerTest-Pojo(string=value,integer=1,integerArray=[1,2,3])
```

## Dependencies

### Maven

#### Jitpack

Add Jitpack repository in `<repositories>` section in __`pom.xml`__:

```xml
<repositories>

    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>

</repositories>
```

```xml
<dependency>
    <groupId>com.github.DeMmAge</groupId>
    <artifactId>Simple-Yaml-Transformer</artifactId>
    <version>${project.version}</version>
</dependency>
```

#### Github packages

[Github packages](https://github.com/DeMmAge?tab=packages&repo_name=Simple-Yaml-Transformer)

```xml
<dependency>
    <groupId>dev.demmage</groupId>
    <artifactId>simple-yaml-reader</artifactId>
    <version>${project.version}</version>
</dependency>
```

#### Maven Central

```xml
TBD
```

### Gradle

#### Add repository at ``repositories`` section:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Add implementation:

```groovy
dependencies {
    implementation 'com.github.DeMmAge:Simple-Yaml-Transformer:Tag'
}
```

## Contacts

* [Trello board](https://trello.com/b/sPo2h5qC)