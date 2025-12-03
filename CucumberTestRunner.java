package com.parabank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = {
        "src/test/resources/features/login.feature",
        "src/test/resources/features/account-overview.feature",
        "src/test/resources/features/bill_pay.feature",
        "src/test/resources/features/transfer_funds.feature",
        "src/test/resources/features/new_account.feature"
    },
    glue = {
        "com.parabank.stepdefinitions",
        "com.parabank.hooks"
    },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}