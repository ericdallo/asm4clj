# asm4clj

## Build

`clj -T:build native-cli`

## Run

`./asm4clj /path/to/Foo.class`

Example:

```clojure
;; ./asm4clj src-java/asm4clj/Foo.class
 
 {:bases #{java.lang.Object},
 :flags #{:public},
 :members
 #{{:name toString,
    :return-type java.lang.String,
    :declaring-class src-java/asm4clj/Foo.class,
    :parameter-types [],
    :exception-types [],
    :flags #{:public}}
   {:name run,
    :return-type void,
    :declaring-class src-java/asm4clj/Foo.class,
    :parameter-types [java.lang.String java.lang.String],
    :exception-types [],
    :flags #{:public}}
   {:name other,
    :type java.lang.String,
    :declaring-class src-java/asm4clj/Foo.class,
    :flags #{:private}}
   {:name src-java/asm4clj/Foo.class,
    :declaring-class src-java/asm4clj/Foo.class,
    :parameter-types [],
    :exception-types [],
    :flags #{:public}}
   {:name some,
    :type int,
    :declaring-class src-java/asm4clj/Foo.class,
    :flags #{:private}}}}

```
