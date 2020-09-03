package com.plugin.test
/**
 * 已扫描到接口或者codeInsertToClassName jar的信息
 */
class ScanHarvest {
    List<Harvest> harvestList = new ArrayList<>()
    class Harvest {
        String className
        String interfaceName
        boolean isInitClass
        String processName
    }
}