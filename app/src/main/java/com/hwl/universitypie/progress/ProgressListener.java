package com.hwl.universitypie.progress;

public interface ProgressListener {

    void progress(long bytesRead, long contentLength, boolean done);

}
