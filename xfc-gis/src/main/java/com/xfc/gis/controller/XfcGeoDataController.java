package com.xfc.gis.controller;

import com.xfc.gis.dto.AddGeoDTO;
import com.xfc.gis.service.IXfcGeoDataService;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/xfc-geo-data")
public class XfcGeoDataController {
    @Autowired
    IXfcGeoDataService xfcGeoDataService;
    @PostMapping("")
    public void insertGeoData(@Validated @RequestBody AddGeoDTO addGeoDTO) throws IOException, ParseException {
       xfcGeoDataService.insertGeoData(addGeoDTO);
    }

}
