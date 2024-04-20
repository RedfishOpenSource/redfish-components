package com.redfish.components.common.model.query;

/**
 * 1，接口排序字段在一个业务接口中一定是明确的。
 * 如果客户端任意指定排序字段，有以下坏处。
 * 1）接口不明确，定位不准。
 * 2）存储服务设计上，不建议设置为任意字段排序，会降低性能。
 * 另外，基本不存在需要客户端任意指定排序字段的场景。
 *
 * 2，各字段排序先后。。
 * 各个字段排序先后按照实体字段声明先后来。
 * 业务开发中，不会有各字段排序先后由客户端任意指定的场景。
 *
 * 3，建议给每个排序字段设置一个默认值。减少后续空判断等风险。
 *
 * 案例：
 * public class ProductSortInfo extends SortInfo {
 *
 *     // 创建日期
 *     private String createDate = DESC;
 *
 *     // 生产地区
 *     private String area = ASC;
 *
 * }
 *
 */
public abstract class SortInfo {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

}
