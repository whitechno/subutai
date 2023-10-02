Spark4 Java version note
========================
October 2, 2023

Spark 4.x dropped Java 8 and 11 support on Sep 22, 2023. Also, they dropped
support for Scala 2.12 in Spark 4 from the beginning.
```
commit 46ebed2b3714c56661577336653f716a440f0c77
Author: yangjie01 <yangjie01@baidu.com>
Date:   Fri Sep 22 05:36:39 2023 -0700

    [SPARK-44112][BUILD][INFRA][DOCS] Drop support for Java 8 and Java 11
    
    ### What changes were proposed in this pull request?
    The main purpose of this pr is to remove support for Java 8 and Java 11 in Apache Spark 4.0, the specific work includes:
    1. `pom.xml`: change `java.version` from 1.8 to 17, change `target:jvm-1.8` to `target:17`
    2. `SparkBuild.scala`: change `-target:jvm-${javaVersion.value}` to `-target:${javaVersion.value}`
    3. workflow files: change the default Java version from 8 to 17, and ensure that branch-3.x still uses Java 8. Removed the daily job for Java 11 and Java 17.
    4. docs: replace parts of Java 8 and 11 with Java 17.
    
    ### Why are the changes needed?
    The minimum supported Java version for Apache Spark 4.0 is Java 17
    
    ### Does this PR introduce _any_ user-facing change?
    Yes,  Apache will no longer support Java 8 and Java 11
    
    ### How was this patch tested?
    - Pass Github Actions
    
    ### Was this patch authored or co-authored using generative AI tooling?
    No
    
    Closes #43005 from LuciferYang/SPARK-44112.
    
    Authored-by: yangjie01 <yangjie01@baidu.com>
    Signed-off-by: Dongjoon Hyun <dhyun@apple.com>
```

https://github.com/apache/spark/commit/46ebed2b3714c56661577336653f716a440f0c77

https://issues.apache.org/jira/browse/SPARK-44112

[Vector API](https://openjdk.org/jeps/448)
Introduce an API to express vector computations that reliably compile at runtime
to optimal vector instructions on supported CPU architectures, thus achieving
performance superior to equivalent scalar computations.
