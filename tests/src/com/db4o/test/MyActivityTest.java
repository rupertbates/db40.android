package com.db4o;

import android.test.ActivityInstrumentationTestCase2;
import com.db4o.test.MyActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.db4o.test.MyActivityTest \
 * com.db4o.tests/android.test.InstrumentationTestRunner
 */
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {

    public MyActivityTest() {
        super("com.db4o", MyActivity.class);
    }

}
