/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.linuxtools.internal.rdt.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.eclipse.remote.core.IRemoteProcess;

/**
 * Bundled state of a launched process including the threads linking the process
 * in/output to console documents.
 */
public class RemoteProcessClosure {

    /**
     * Thread which continuously reads from a input stream and pushes the read
     * data to an output stream which is immediately flushed afterwards.
     */
    protected static class ReaderThread extends Thread {

        private InputStream fInputStream;
        private OutputStream fOutputStream;
        private boolean fFinished = false;
        private String lineSeparator;
        /*
         * outputStream can be null
         */
        public ReaderThread(ThreadGroup group, String name, InputStream in, OutputStream out) {
            super(group, name);
            fOutputStream = out;
            fInputStream = in;
            setDaemon(true);
            lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
        }

        @Override
        public void run() {
            try {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fInputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line += lineSeparator;
                        fOutputStream.write(line.getBytes());
                    }
                } catch (IOException x) {
                    // ignore
                } finally {
                    try {
                        //                    writer.flush();
                        fOutputStream.flush();
                    } catch (IOException e) {
                        // ignore
                    }
                    try {
                        fInputStream.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            } finally {
                complete();
            }
        }

        public synchronized boolean finished() {
            return fFinished;
        }

        public synchronized void waitFor() {
            while (!fFinished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }

        public synchronized void complete() {
            fFinished = true;
            notify();
        }

        public void close() {
            try {
                fOutputStream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private static int fCounter = 0;

    private IRemoteProcess fProcess;

    private OutputStream fOutput;
    private OutputStream fError;

    private ReaderThread fOutputReader;
    private ReaderThread fErrorReader;

    /**
     * Creates a process closure and connects the launched process with a
     * console document.
     *
     * @param outputStream
     *            prcess stdout is written to this stream. Can be
     *            <code>null</code>, if not interested in reading the output
     * @param errorStream
     *            prcess stderr is written to this stream. Can be
     *            <code>null</code>, if not interested in reading the output
     */
    public RemoteProcessClosure(IRemoteProcess process, OutputStream outputStream, OutputStream errorStream) {
        fProcess = process;
        fOutput = outputStream;
        fError = errorStream;
    }

    /**
     * Live links the launched process with the configured in/out streams using
     * reader threads.
     */
    public void runNonBlocking() {
        ThreadGroup group = new ThreadGroup("CBuilder" + fCounter++); //$NON-NLS-1$

        InputStream stdin = fProcess.getInputStream();
        InputStream stderr = fProcess.getErrorStream();

        fOutputReader = new ReaderThread(group, "OutputReader", stdin, fOutput); //$NON-NLS-1$
        fErrorReader = new ReaderThread(group, "ErrorReader", stderr, fError); //$NON-NLS-1$

        fOutputReader.start();
        fErrorReader.start();
    }

    public boolean isAlive() {
        if (fProcess != null) {
            if (fOutputReader.isAlive() || fErrorReader.isAlive()) {
                return true;
            }
            fProcess = null;
            fOutputReader.close();
            fErrorReader.close();
            fOutputReader = null;
            fErrorReader = null;
        }
        return false;
    }

    /**
     * Forces the termination the launched process
     */
    public void terminate() {
        if (fProcess != null) {
            fProcess.destroy();
            fProcess = null;
        }
        if (!fOutputReader.finished()) {
            fOutputReader.waitFor();
        }
        if (!fErrorReader.finished()) {
            fErrorReader.waitFor();
        }
        fOutputReader.close();
        fErrorReader.close();
        fOutputReader = null;
        fErrorReader = null;
    }
}
