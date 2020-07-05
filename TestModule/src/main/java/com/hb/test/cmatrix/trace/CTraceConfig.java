package com.hb.test.cmatrix.trace;

public class CTraceConfig implements CIDefaultConfig {
    public boolean fpsEnable;

    @Override
    public boolean isFPSEnable() {
        return fpsEnable;
    }

    public static class Builder {
        private CTraceConfig config;

        public Builder() {
            config = new CTraceConfig();
        }

        public Builder enableFPS(boolean enable) {
            config.fpsEnable = enable;
            return this;
        }

        public CTraceConfig build() {
            return config;
        }
    }
}
