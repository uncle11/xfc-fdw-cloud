package com.xfc.gis.utils;

import lombok.extern.slf4j.Slf4j;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @version 1.0
 * @description: gis工具类
 * @author: chenss
 * @date 2024-03-14 16:19
 */
@Slf4j
public class GisUtil {
    public static void main(String[] args) {
        JSONObject Geojson=buildGeojsonFromShp("D:\\arcgisdata\\mesh4490.shp");
        log.info(Geojson.toJSONString());
//        List<String> wkts = getWktFromShp("D:\\arcgisdata\\mesh4490.shp");
//        for (String wkt : wkts) {
//            log.info(wkt);
//        }
    }

    /**
     * 构造Geojson结构体
     * @param featuresJson
     * @return
     */
    public static JSONObject buildGeoJson(JSONArray featuresJson) {
        JSONObject Geojson = new JSONObject();
        Geojson.put("type", "FeatureCollection");
        Geojson.put("features", featuresJson);
        return Geojson;
    }

    /**
     * 构造Geojson的features部分 单个
     *
     * @param geoObject
     * @param properties
     * @return
     */
    public static JSONObject buildFeature(Map geoObject, Map properties) {
        JSONObject featureObject = new JSONObject();
        Map featureMap = new HashMap();
        featureMap.put("type", "Feature");
        featureMap.putAll(geoObject);
        featureMap.put("properties", properties);
        featureObject.putAll(featureMap);
        return featureObject;
    }

    /**
     * 获取空间信息并构造为Map
     * @param wkt
     * @return
     */
    public static Map getGeoMap(String wkt) {
        Map<String, Object> geoMap = new HashMap<>();
        String json = null;
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON();
            g.write(geometry, writer);
            geoMap.put("geometry", writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geoMap;
    }

    /**
     * 只读取geo信息 wkt
     *
     * @param shpPath
     * @return
     */
    public static List<String> getWktFromShp(String shpPath) {
        List<String> shpList = new ArrayList<>();
        SimpleFeatureCollection simpleFeatureCollection = null;
        try {
            //获取文件
            File file = new File(shpPath);
            // 读取到数据存储中
            FileDataStore dataStore = FileDataStoreFinder.getDataStore(file);
            // 获取特征资源
            SimpleFeatureSource simpleFeatureSource = dataStore.getFeatureSource();
            // 要素集合
            simpleFeatureCollection = simpleFeatureSource.getFeatures();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取要素迭代器
        SimpleFeatureIterator featureIterator = simpleFeatureCollection.features();
        while (featureIterator.hasNext()) {
            // 要素对象
            SimpleFeature feature = featureIterator.next();
            Object geometryText = feature.getDefaultGeometry();
            log.info(geometryText.toString());
            shpList.add(geometryText.toString());

        }
        featureIterator.close();
        return shpList;
    }

    /**
     * 基于shp构造geojson并返回
     *
     * @param shpPath
     * @return
     */
    public static JSONObject buildGeojsonFromShp(String shpPath) {
        JSONArray featureArray = new JSONArray();
//        List<String>shpList=new ArrayList<>();
        SimpleFeatureCollection simpleFeatureCollection = null;
        try {
//            要素合集
            //获取文件
            File file = new File(shpPath);
            // 读取到数据存储中
            ShapefileDataStore dataStore = (ShapefileDataStore) FileDataStoreFinder.getDataStore(file);
            dataStore.setCharset(Charset.forName("GBK"));
            // 获取特征资源
            SimpleFeatureSource simpleFeatureSource = dataStore.getFeatureSource();
            // 要素集合
            simpleFeatureCollection = simpleFeatureSource.getFeatures();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleFeatureIterator featureIterator = simpleFeatureCollection.features();
//            // 要素数量
        int featureSize = simpleFeatureCollection.size();
//            log.info("要素数量"+featureSize);
        //创建properties  Map
        while (featureIterator.hasNext()) {
            // 要素对象
            SimpleFeature feature = featureIterator.next();
            Collection<Property> propertyCollection = (Collection<Property>) feature.getValue();
            //填充属性map
            Map<String, Object> properMap = new HashMap<>();
            for (Property property : propertyCollection) {
                if (property.getName().toString().equals("the_geom")) {
                    continue;
                }
                properMap.put(property.getName().toString(), property.getValue());
            }
            //获取geo信息
            Object geometryText = feature.getDefaultGeometry();
            Map geoMap = getGeoMap(geometryText.toString());
            JSONObject featureObject = buildFeature(geoMap, properMap);
            featureArray.add(featureObject);
        }
        featureIterator.close();
        JSONObject GeoJson = buildGeoJson(featureArray);

        return GeoJson;
    }

    /**
     * 根据给定的wkt面求出中心点，并以wkt形式返回
     */
    public static String calculateCenter(String wktPolygon) throws ParseException {
        // 创建 WKT 解析器和写入器
        WKTReader reader = new WKTReader(new GeometryFactory());
        WKTWriter writer = new WKTWriter();

        // 解析面的几何对象
        Geometry geometry = reader.read(wktPolygon);

        // 计算面的中心点
        Point center = geometry.getCentroid();

        // 将中心点转换为 WKT 格式
        String wktCenter = writer.write(center);

        return wktCenter;
    }


}
