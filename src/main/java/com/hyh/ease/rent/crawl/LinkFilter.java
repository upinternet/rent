package com.hyh.ease.rent.crawl;

public interface LinkFilter {
    public boolean accept(String url);
}