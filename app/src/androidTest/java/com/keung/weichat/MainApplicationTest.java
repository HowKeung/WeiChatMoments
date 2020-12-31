package com.keung.weichat;

import android.app.Application;
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainApplicationTest extends Application {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContext() {
        assertEquals("com.keung.weichat", context.getPackageName());
    }

    @Test
    public void testAttachBaseContext(Context base) {
        assertEquals(base, context);
    }
}