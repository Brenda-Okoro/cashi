package com.example.cashi

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration
import java.util.concurrent.TimeUnit

class PaymentAppUITest {

    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities().apply {
            setCapability("platformName", "Android")
            setCapability("deviceName", "Android Emulator")
            setCapability("appPackage", "com.example.cashi")
            setCapability("appActivity", ".MainActivity")
            setCapability("automationName", "UiAutomator2")
            setCapability("noReset", true)
        }

        driver = AndroidDriver(URL("http://localhost:4723"), capabilities)
        wait = WebDriverWait(driver, Duration.ofSeconds(10))
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    }

    @Test
    fun testSendPaymentFlow() {
        val sendPaymentTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendPaymentTab.click()

        val emailField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Recipient Email\")"))
            )
        )
        emailField.click()
        emailField.sendKeys("test@example.com")

        val amountField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Amount\")"))
            )
        )
        amountField.click()
        amountField.sendKeys("100.0")

        val currencyField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Currency\")"))
            )
        )
        currencyField.click()

        val usdOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"USD ($)\")"))
            )
        )
        usdOption.click()

        val sendButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendButton.click()

        val historyTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Transaction History\")"))
            )
        )
        historyTab.click()

        val transactionItem = wait.until(
            ExpectedConditions.visibilityOf(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"test@example.com\")"))
            )
        )
        assertTrue("Transaction should appear in history", transactionItem.isDisplayed)
    }

    @Test
    fun testValidationErrors() {
        val sendPaymentTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendPaymentTab.click()

        val sendButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendButton.click()

        val errorMessage = wait.until(
            ExpectedConditions.visibilityOf(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Invalid email\")"))
            )
        )
        assertTrue("Error message should be displayed", errorMessage.isDisplayed)
    }

    @Test
    fun testInvalidEmailValidation() {
        val sendPaymentTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendPaymentTab.click()

        val emailField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Recipient Email\")"))
            )
        )
        emailField.click()
        emailField.sendKeys("invalid-email")

        val amountField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Amount\")"))
            )
        )
        amountField.click()
        amountField.sendKeys("50.0")

        val sendButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendButton.click()

        val emailError = wait.until(
            ExpectedConditions.visibilityOf(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Invalid email format\")"))
            )
        )
        assertTrue("Email validation error should be displayed", emailError.isDisplayed)
    }

    @Test
    fun testInvalidAmountValidation() {
        val sendPaymentTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendPaymentTab.click()

        val emailField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Recipient Email\")"))
            )
        )
        emailField.click()
        emailField.sendKeys("valid@example.com")

        val amountField = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Amount\")"))
            )
        )
        amountField.click()
        amountField.sendKeys("-10.0")

        val sendButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Send Payment\")"))
            )
        )
        sendButton.click()

        val amountError = wait.until(
            ExpectedConditions.visibilityOf(
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Amount must be greater than zero\")"))
            )
        )
        assertTrue("Amount validation error should be displayed", amountError.isDisplayed)
    }

    @After
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }
}
