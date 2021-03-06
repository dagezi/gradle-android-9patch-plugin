package io.github.dagezi.ninepatch

public class NinePatch {
    public static class Range {
        public String usage;
        public int from;
        public int to;

        public Range clone() {
            return new Range(usage:usage, from: from, to: to)
        }

        public void ensure(int max) {
            if (from < 0) {
                from += max;
            }
            if (to < 0) {
                to += max;
            }
            if (from < 0 || from > max || to < 0 || to > max || from >= to) {
                throw new IllegalArgumentException(
                    "The ${usage} (${from}, ${to}) must be inside (0, ${max})")
            }
        }

        public void zoom(double zoom) {
            to *= zoom;
            from *= zoom;
        }
    }

    public String name
    public Integer width
    public Integer height
    public List<String> srcs = []
    public List<Range> vStretch = []
    public List<Range> hStretch = []

    public Range hPadding
    public Range vPadding

    public NinePatch(String name) {
        this.name = name
    }

    public void pngSize(int width, int height) {
        this.width = width
        this.height = height
    }

    public void hStretch(int from, int to) {
        def range = new Range(usage: 'hStratch', from: from, to: to)
        if (width) range.ensure(width)
        hStretch << range
    }

    public void vStretch(int from, int to) {
        def range = new Range(usage: 'vStretch', from: from, to: to)
        if (height) range.ensure(height)
        vStretch << range
    }

    public void hPadding(int from, int to) {
        hPadding = new Range(usage: 'hPadding', from: from, to: to)
        if (width) hPadding.ensure(width)
    }

    public void vPadding(int from, int to) {
        vPadding = new Range(usage: 'vPadding', from: from, to: to)
        if (height) vPadding.ensure(height)
    }

    public void src(String src) {
        srcs << src
    }

    public List<String> getSrcs() {
        return srcs.isEmpty() ? [name] : srcs
    }
}
