package edu.csci412.assignment2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Assignment6UiTest {
    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000)
    }

    @Test
    fun launchAppAndOpenSecondActivity() {
        val appIcon = device.findObject(By.desc("CSCI 412 App"))
        appIcon.click()

        val appOpened = device.wait(
            Until.hasObject(By.pkg("edu.csci412.assignment2").depth(0)),
            5000
        )
        assertTrue(appOpened)

        val explicitButton = device.findObject(By.text("Start Activity Explicitly"))
        explicitButton.click()

        val challengeDisplayed = device.wait(
            Until.hasObject(By.text("1. Supporting many screen sizes")),
            5000
        )
        assertTrue(challengeDisplayed)
    }
}
