# LCOV formatting
Each class provided by this library has a dedicated `toString()` method returning the corresponding data formatted as [LCOV](https://github.com/linux-test-project/lcov) string.
All you have to do is to create the adequate structure using these different classes, and to export the final result:

```java
import io.belin.lcov.FunctionCoverage;
import io.belin.lcov.LineCoverage;
import io.belin.lcov.LineData;
import io.belin.lcov.Report;
import io.belin.lcov.SourceFile;
import java.nio.file.Path;
import java.util.List;

class Program {
  public static void main(String... args) {
    var sourceFile = new SourceFile(Path.of("/home/cedx/lcov.java/fixture.java"));
    sourceFile.functions = new FunctionCoverage(1, 1);
    sourceFile.lines = new LineCoverage(2, 2, List.of(
      new LineData(6, 2, "PF4Rz2r7RTliO9u6bZ7h6g"),
      new LineData(7, 2, "yGMB6FhEEAd8OyASe3Ni1w")
    ));

    var report = new Report("Example", List.of(sourceFile));
    System.out.println(report);
  }
}
```

The `Report.toString()` method will return a [LCOV](https://github.com/linux-test-project/lcov) report formatted like this:

```lcov
TN:Example
SF:/home/cedx/lcov.java/fixture.java
FNF:1
FNH:1
DA:6,2,PF4Rz2r7RTliO9u6bZ7h6g
DA:7,2,yGMB6FhEEAd8OyASe3Ni1w
LF:2
LH:2
end_of_record
```

> See the [API reference](api/) of this library for detailed information on the available classes.
