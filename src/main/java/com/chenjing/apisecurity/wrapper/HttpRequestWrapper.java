package com.chenjing.apisecurity.wrapper;


import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 两个方法都注明方法只能被调用一次，由于RequestBody是流的形式读取，
 * 那么流读了一次就没有了，所以只能被调用一次。
 * 既然是因为流只能读一次的原因，那么只要将流的内容保存下来，就可以实现反复读取了
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {
    private static final String ENCODING = "UTF-8";
    private static final String EMPTY_STRING = "";

    private final byte[] payload;

    public HttpRequestWrapper(final HttpServletRequest request) throws IOException {
        super(request);
        this.payload = IOUtils.toByteArray(getRequest().getInputStream());
    }

    /**
     * 获取请求Body，并转换为字符串返回。
     *
     * @return 请求Body
     * @throws IOException 读取Body失败时抛出
     */
    public String getPayloadAsString() throws IOException {
        if (payload == null) {
            return EMPTY_STRING;
        }

        return IOUtils.toString(payload, ENCODING);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachingServletInputStream(payload);
    }

    private static final class CachingServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream bis;

        CachingServletInputStream(byte[] payload) {
            bis = new ByteArrayInputStream(payload);
        }

        @Override
        public int read() {
            return bis.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            //
        }
    }

}

