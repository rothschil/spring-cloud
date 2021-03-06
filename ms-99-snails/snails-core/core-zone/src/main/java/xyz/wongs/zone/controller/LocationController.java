package xyz.wongs.zone.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.zone.ZoneCodeStringUtils;
import xyz.wongs.zone.entity.Location;
import xyz.wongs.zone.service.LocationService;
import xyz.wongs.zone.service.ProcessService;


import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title:LocationController </p>
 * <p>@Description: </p>
 * <p>Company: </p>
 * ----------------------------------------
 * \\	 /\/\   /\|	|/\   /\/\	 //
 * \\^^  ^^^  ^^|_|^^  ^^^  ^^//
 * \\^   ^^^  ^/Ϡ\^   ^^^  ^//
 * \\^ ^    ^/___\^    ^ ^//
 * \\^ ^^ ^//   \\^ ^^ ^//
 * \\	^^/(/     \)\^^ //
 * \\^'//       \\'^//
 * .==.   खान          .==.
 * ----------------------------------------
 *
 * @author: <a href="wcngs@qq.com">WCNGS</a>
 * @date: 2017年8月5日 下午11:08:19  *
 * @since JDK 1.7
 */
@Api(description = "", value = "location")
@RestController
@RequestMapping(value = "/locations")
public class LocationController {

    private static final String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";

    @Autowired
    @Qualifier("locationService")
    private LocationService locationService;

    @Autowired
    @Qualifier("processService")
    private ProcessService processService;


    /**
     * @param lv
     * @return List<LocationEntity>
     * @Title: getLocationListByLevel
     * @Description: 请求参数在URL中，需要在 @ApiImplicitParam 中加上 "paramType="path""
     */
    @ApiOperation(value = "获取行政区域列表", notes = "根据层级获取行政列表")
    @ApiImplicitParam(name = "lv", value = "层级", required = true, dataType = "Integer", paramType = "path")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/{lv}", method = RequestMethod.GET)
    public List<Location> getLocationListByLevel(@PathVariable(value = "lv") Integer lv) {

        List<Location> r = locationService.getLocationListByLevel(lv);
        return r;
    }

    @ApiOperation(value = "获取行政区域", notes = "根据主键获取行政区域")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "id={id}", method = RequestMethod.GET)
    public List<Location> getLocationListByLevel(@PathVariable Long id) {
        List<Location> r = locationService.getLocationListById(id);
        return r;
    }


    @ApiOperation(value = "初始化行政区域", notes = "第一级")
    @RequestMapping(value = "/intiLevelToFirst", method = RequestMethod.GET)
    public void intiLevelToFirst() {
        List<Location> root = locationService.getLocationListByLevel(0);
        for (Location location : root) {
            processService.getHTML(url + location.getUrl(), location);
        }
    }

    public void secondLevelResolve(int pageNumber) {
        //初始化0
        try {
            Page<Location> pageLocation = locationService.getLocationsByLevel(1, pageNumber);
            Iterator<Location> iter = pageLocation.iterator();
            while (iter.hasNext()) {
                Location location = iter.next();
                String url2 = new StringBuilder().append(url).append(location.getUrl()).toString();
                processService.getHTML2(url2, location);
                locationService.updateLocationFlag("Y", location.getId());
            }

            int times = new Random().nextInt(10000);
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "初始化行政区域", notes = "第二级")
    @RequestMapping(value = "/intiLevelToSecond", method = RequestMethod.GET)
    public void intiLevelToSecond() {
        int totalPages = locationService.getLocationCountsByLevel(1);

        for (int i = 1; i <= totalPages; i++) {
            secondLevelResolve(i);
        }
    }

    @ApiOperation(value = "初始化行政区域-III", notes = "第三级")
    @RequestMapping(value = "/intiLevelToThrid", method = RequestMethod.GET)
    public void intiLevelToThrid() {
        int totalPages = locationService.getLocationCountsByLevel(2);
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(10, 15, 5, TimeUnit.SECONDS, new SynchronousQueue<>());

        for (int i = 1; i <= totalPages; i++) {
            int ii = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
//                    thridLevelResolve(ii);
                    Page<Location> pageLocation = locationService.getLocationsByLevel(2, ii);

                    Iterator<Location> iter = pageLocation.iterator();
                    while (iter.hasNext()) {
                        Location location = iter.next();
                        String url2 = new StringBuilder().append(url).append(ZoneCodeStringUtils.getUrlStrByLocationCode(location.getLocalCode(), 2)).append(location.getUrl()).toString();
                        processService.thridLevelResolve(url2, location);

                    }
                    try {
                        int times = new Random().nextInt(30000);
                        Thread.sleep(times);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public void thridLevelResolve(int pageNumber) {
        Page<Location> pageLocation = locationService.getLocationsByLevel(2, pageNumber);

        Iterator<Location> iter = pageLocation.iterator();
        while (iter.hasNext()) {
            Location location = iter.next();
            String url2 = new StringBuilder().append(url).append(ZoneCodeStringUtils.getUrlStrByLocationCode(location.getLocalCode(), 2)).append(location.getUrl()).toString();
            processService.thridLevelResolve(url2, location);

        }
    }


    @ApiOperation(value = "初始化行政区域-IV", notes = "第四级")
    @RequestMapping(value = "/intiLevelToFourth", method = RequestMethod.GET)
    public void intiLevelToFourth() {
        for (int i = 0; i < 5; i++) {
            processService.getLocationThrid();
        }
    }

    @ApiOperation(value = "getLocationByID", notes = "根据ID获取区域")
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Location> getLocationByID(@PathVariable(value = "id") Integer id) {
        Long lid = Long.valueOf(id);
        return locationService.getLocationListById(lid);

    }

    @ApiOperation(value = "getLocationByLevel", notes = "根据层级获取区域")
    @RequestMapping(value = "/level/{lv}", method = RequestMethod.GET)
    @ResponseBody
    public List<Location> getLocationByLv(@PathVariable(value = "lv") Integer lv) {
        return locationService.getLocationListByLevel(lv);

    }
}
