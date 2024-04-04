package data.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"data.steps"},
        plugin = {"pretty","html:target/result/report.html"},
        tags = "@test",
        snippets = CucumberOptions.SnippetType.CAMELCASE)

public class ReqresTestRunner {
}
