package com.crispysnippets.brexitapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InitialActivityTest {

    @Rule
    public ActivityTestRule<InitialActivity> mActivityTestRule = new ActivityTestRule<>(InitialActivity.class);

    @Test
    public void initialActivityTest() {
    }

}
