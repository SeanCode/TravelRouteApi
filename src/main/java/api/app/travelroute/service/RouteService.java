package api.app.travelroute.service;

import api.app.travelroute.entity.RouteEntity;
import api.app.travelroute.repository.RouteRepository;
import api.base.common.Util;
import api.base.exception.InvalidParamsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cc on 2017/4/17.
 */
@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepo;

    public Page<RouteEntity> getRouteListByDestId(long destId, Pageable pageable) {

        return routeRepo.findByDestId(destId, pageable);
    }

    public RouteEntity getRoute(long routeId) {

        return routeRepo.findOne(routeId);
    }

    public void delete(long id) {

        routeRepo.delete(id);
    }

    public RouteEntity saveRoute(long destId, long routeId, String name, Double price, String intro, String info, String notice, String img) {
        if (routeId == 0) {
            return createRoute(destId, name, price, intro, info, notice, img);
        }

        RouteEntity route = routeRepo.findOne(routeId);
        return route == null ? createRoute(destId, name, price, intro, info, notice, img) : updateRoute(route, name, price, intro, info, notice, img);
    }

    private RouteEntity createRoute(long destId, String name, Double price, String intro, String info, String notice, String img) {
        if (destId == 0 || StringUtils.isBlank(name) || price == null || StringUtils.isBlank(intro) || StringUtils.isBlank(info) || StringUtils.isBlank(notice) || StringUtils.isBlank(img)) {
            throw new InvalidParamsException();
        }
        RouteEntity route = new RouteEntity();
        route.setDestId(destId);
        route.setName(name);
        route.setPrice(price);
        route.setIntro(intro);
        route.setInfo(info);
        route.setNotice(notice);
        route.setImg(img);
        route.setCreateTime(Util.time());
        route.setUpdateTime(route.getCreateTime());

        return routeRepo.save(route);
    }

    private RouteEntity updateRoute(RouteEntity route, String name, Double price, String intro, String info, String notice, String img) {
        if (StringUtils.isNotBlank(name)) {
            route.setName(name);
        }
        if (price != null) {
            route.setPrice(price);
        }
        if (StringUtils.isNotBlank(intro)) {
            route.setIntro(intro);
        }
        if (StringUtils.isNotBlank(info)) {
            route.setInfo(info);
        }
        if (StringUtils.isNotBlank(notice)) {
            route.setNotice(notice);
        }
        if (StringUtils.isNotBlank(img)) {
            route.setImg(img);
        }
        route.setUpdateTime(Util.time());

        return routeRepo.save(route);
    }

}
