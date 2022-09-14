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

这个api是用来存取键值对的,键值对的存取主要是围绕着 DataStore<Preferences>对象来进行操作的。dataStore对象的获取十分简单：

```kotlin
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data")
```
- 通过委托的方式获取DataStore<Preferences>对象
- preferencesDataStore方法接受一个参数，和SharedPreference类似系统会在data/data/包名/files/datastore/xxx.preference_pb 文件
- 一般将DataStore<Preferences>定义问顶层属性形式，然后再通过扩展函数扩展下，这样不仅单例，而且代码中任意部分都能使用，十分方便。

有了DataStore<Preferences>对象后我们就能通过其data属性来读取数据，通过其edit属性来写数据了。

键值对也即key-value，PreferencesKeys这个类中已经定义好了：

```kotlin
@JvmName("intKey")
public fun intPreferencesKey(name: String): Preferences.Key<Int> = Preferences.Key(name)

@JvmName("doubleKey")
public fun doublePreferencesKey(name: String): Preferences.Key<Double> = Preferences.Key(name)

@JvmName("stringKey")
public fun stringPreferencesKey(name: String): Preferences.Key<String> = Preferences.Key(name)

@JvmName("booleanKey")
public fun booleanPreferencesKey(name: String): Preferences.Key<Boolean> = Preferences.Key(name)

@JvmName("floatKey")
public fun floatPreferencesKey(name: String): Preferences.Key<Float> = Preferences.Key(name)

@JvmName("longKey")
public fun longPreferencesKey(name: String): Preferences.Key<Long> = Preferences.Key(name)

@JvmName("stringSetKey")
public fun stringSetPreferencesKey(name: String): Preferences.Key<Set<String>> =
    Preferences.Key(name)
```
可见我们只需要传递个String类型的key即可获取对应的value，但是DataStore<Preferences>并不是以这种方式进行直接存储的，而是继续包装了下:

```kotlin
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data")

object DataStore {

    /**
     * get a string from DataStore
     * */
    fun readString(context: Context, key: String): Flow<String> {
        return context.dataStore.data
            .map { preferences  -> //preferences是Preferences类型
                preferences[stringPreferencesKey(key)]?:""//这里不是类型安全的
            }
    }

    /**
     * write a string to DataStore
     * */
    suspend fun writeString(context: Context, key: String, value: String) {
        context.dataStore.edit { settings -> // settings是MutablePreferences类型
            settings[stringPreferencesKey(key)] = value
        }
    }
}
```

最后就是测试代码了，直接在MainActivity中进行测试~

```groovy
    // 协程核心库
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    // 协程Android支持库
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
    // lifecycle对于协程的扩展封装
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
```

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val key = "name"
        val value = "Serendipity"

       lifecycleScope.launch {
           DataStore.writeString(applicationContext,key,value)
           val value = DataStore.readString(applicationContext,key)
           value.collect {
               Log.d("MY-TAG","value:$it")
           }

       }
    }
}
```

###### 2、Proto DataStore

Proto DataStore 实现使用 DataStore 和协议缓冲区将类型化的对象保留在磁盘上。

啥叫协议缓冲区呢？

[参考](https://www.bilibili.com/video/BV1434y1Y7Ck/?spm_id_from=pageDriver&vd_source=f1408610c4243752036771041ce6443b)

[官方文档](https://developer.android.google.cn/topic/libraries/architecture/datastore)