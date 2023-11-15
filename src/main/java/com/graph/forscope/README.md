# 测试运行GraphScope GIE 

**1. DataModel** 

![数据模型](../../../../resources/DataModel/FileStepParallelzation/pickture/StepParallelzationDataModelForDebug.png "test")


**Gremlin 语句**

```groovy
g.V("1:marko")
 .out()
 .out()
 .count()
```


**GIE Paper**

