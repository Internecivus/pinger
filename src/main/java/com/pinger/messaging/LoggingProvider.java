package com.pinger.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.PrintStream;

public class LoggingProvider {

    private LoggingProvider() {
    }

    private final static Logger instance = LoggerFactory.getLogger(LoggingProvider.class);

    public static Logger getInstance() {
        return instance;
    }

    public static PrintStream createLoggingProxy(final PrintStream realPrintStream, final Level level) {
        return new PrintStream(realPrintStream) {

            void printProxy(final Object object) {
                //realPrintStream.print(object);
                printLogger(object);
            }

            void printlnProxy(final Object object) {
                //realPrintStream.println(object);
                printLogger(object);
            }

            void printLogger(final Object object) {
                switch (level) {
                    case INFO:
                        getInstance().info(object.toString());
                        break;

                    case ERROR:
                        getInstance().error(object.toString());
                        break;
                }
            }

            @Override
            public void print(final String string) {
                printProxy(string);
            }

            @Override
            public void print(int i) {
                printProxy(i);
            }

            @Override
            public void print(char c) {
                printProxy(c);
            }

            @Override
            public void print(boolean b) {
                printProxy(b);
            }

            @Override
            public void print(long l) {
                printProxy(l);
            }

            @Override
            public void print(float f) {
                printProxy(f);
            }

            @Override
            public void print(double d) {
                printProxy(d);
            }

            @Override
            public void print(char[] s) {
                printProxy(s);
            }

            @Override
            public void print(Object obj) {
                printProxy(obj);
            }

            @Override
            public void println(long x) {
                printlnProxy(x);
            }

            @Override
            public void println(float x) {
                printlnProxy(x);
            }

            @Override
            public void println(char[] x) {
                printlnProxy(x);
            }

            @Override
            public void println(double x) {
                printlnProxy(x);
            }

            @Override
            public void println(Object x) {
                printlnProxy(x);
            }

            @Override
            public void println(String x) {
                printlnProxy(x);
            }

            @Override
            public void println(boolean x) {
                printlnProxy(x);
            }

            @Override
            public void println(int x) {
                printlnProxy(x);
            }

            @Override
            public void println(char x) {
                printlnProxy(x);
            }

        };
    }
}
