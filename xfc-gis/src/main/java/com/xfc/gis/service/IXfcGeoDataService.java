package com.xfc.gis.service;

import com.xfc.gis.dto.AddGeoDTO;
import com.xfc.gis.entities.XfcGeoData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.locationtech.jts.io.ParseException;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xfc
 * @since 2024-03-22
 */
public interface IXfcGeoDataService extends IService<XfcGeoData> {

    void insertGeoData(AddGeoDTO addGeoDTO) throws IOException, ParseException;
}
