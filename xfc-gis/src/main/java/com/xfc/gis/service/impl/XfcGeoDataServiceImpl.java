package com.xfc.gis.service.impl;

import com.xfc.gis.dto.AddGeoDTO;
import com.xfc.gis.entities.XfcGeoData;
import com.xfc.gis.mapper.XfcGeoDataMapper;
import com.xfc.gis.service.IXfcGeoDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfc.gis.utils.GisUtil;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.io.ParseException;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-03-22
 */
@Service
public class XfcGeoDataServiceImpl extends ServiceImpl<XfcGeoDataMapper, XfcGeoData> implements IXfcGeoDataService {

    @Override
    public void insertGeoData(AddGeoDTO addGeoDTO) throws IOException, ParseException {
        String shpPath=addGeoDTO.getShpPath();
        List<Map<String, Object>> proMaps = getProMapByFeatureCollection(shpPath);
        for (Map<String, Object> proMap : proMaps) {
            XfcGeoData xfcGeoData=new XfcGeoData();
            xfcGeoData.setExtent(proMap.get("the_geom").toString());
            xfcGeoData.setCenter(GisUtil.calculateCenter(proMap.get("the_geom").toString()));
            baseMapper.insert(xfcGeoData);
        }
    }
    /**
     * 根据featureCollection获取属性Map
     */
    public List<Map<String, Object>> getProMapByFeatureCollection(String shpPath) throws IOException {
        File file = new File(shpPath);
        ShapefileDataStore dataStore = (ShapefileDataStore) FileDataStoreFinder.getDataStore(file);
        dataStore.setCharset(Charset.forName("GBK"));
        SimpleFeatureSource simpleFeatureSource = dataStore.getFeatureSource();
        SimpleFeatureCollection simpleFeatureCollection = simpleFeatureSource.getFeatures();
        SimpleFeatureIterator featureIterator = simpleFeatureCollection.features();
        List<Map<String, Object>>properMaps=new ArrayList<>();
        while (featureIterator.hasNext()) {
            // 要素对象
            SimpleFeature feature = featureIterator.next();
            Collection<Property> propertyCollection = (Collection<Property>) feature.getValue();
            //填充属性map
            Map<String, Object> properMap = new HashMap<>();
            for (Property property : propertyCollection) {
                properMap.put(property.getName().toString(), property.getValue());
            }
            properMaps.add(properMap);
        }
        return properMaps;
    }
}
