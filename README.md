# DataStore

# 简介

Jetpack DataStore 是一种数据存储解决方案，功能十分类似SharedPreference。

DataStore 使用 Kotlin 协程和 Flow 以异步、一致的事务方式存储数据。

DataStore 提供两种不同的实现：

- Preferences DataStore 以键值对的形式存储/访问常见类型的数据。不确保数据的类型安全。
- Proto DataStore 将数据作为自定义数据类型的实例进行存储。此实现要求您使用协议缓冲区来定义架构，可以确保类型安全。

# 依赖


```groovy
  implementation "androidx.datastore:datastore-preferences:1.0.0"
  implementation "androidx.datastore:datastore:1.0.0"
```

# 使用

###### 1、Preferences DataStore

这个api是用来存取键值对的

[官方文档](https://developer.android.google.cn/topic/libraries/architecture/datastore)