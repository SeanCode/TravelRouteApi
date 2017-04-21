package api.app.travelroute.service;

import api.app.travelroute.entity.DestinationEntity;
import api.app.travelroute.repository.DestinationRepository;
import api.base.common.Util;
import api.base.exception.InvalidParamsException;
import api.base.exception.NotExistsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by cc on 2017/4/17.
 */
@Service
public class DestinationService {

    @Autowired
    DestinationRepository destRepo;

    public Page<DestinationEntity> queryDestination(String dest, Pageable pageable) {

        return destRepo.findByDestLike(dest, pageable);
    }

    public Page<DestinationEntity> getDestinationList(Pageable pageable) {

        return destRepo.findAll(pageable);
    }

    public DestinationEntity saveDestination(long id, String name, String intro, String info, String dest, String img) {
        if (id == 0) {
            return createDestination(name, intro, info, dest, img);
        }
        DestinationEntity destination = destRepo.findOne(id);
        return destination == null ? createDestination(name, intro, info, dest, img) : updateDestination(destination, name, intro, info, dest, img);
    }

    private DestinationEntity updateDestination(DestinationEntity destination, String name, String intro, String info, String dest, String img) {
        if (StringUtils.isNotBlank(name)) {
            destination.setName(name);
        }
        if (StringUtils.isNotBlank(intro)) {
            destination.setIntro(intro);
        }
        if (StringUtils.isNotBlank(info)) {
            destination.setInfo(info);
        }
        if (StringUtils.isNotBlank(dest)) {
            destination.setDest(dest);
        }
        if (StringUtils.isNotBlank(img)) {
            destination.setImg(img);
        }
        destination.setUpdateTime(Util.time());

        return destRepo.save(destination);
    }

    private DestinationEntity createDestination(String name, String intro, String info, String dest, String img) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(intro) || StringUtils.isBlank(info) || StringUtils.isBlank(dest) || StringUtils.isBlank(img)) {
            throw new InvalidParamsException();
        }
        DestinationEntity destination = new DestinationEntity();
        destination.setName(name);
        destination.setIntro(intro);
        destination.setInfo(info);
        destination.setDest(dest);
        destination.setImg(img);
        destination.setCreateTime(Util.time());
        destination.setUpdateTime(destination.getCreateTime());

        return destRepo.save(destination);
    }

    public DestinationEntity getDestination(long id) {
        DestinationEntity dest = destRepo.findOne(id);
        if (dest == null) {
            throw new NotExistsException();
        }
        return dest;
    }
    public void delete(long id) {
        DestinationEntity dest = destRepo.findOne(id);
        if (dest == null) {
            throw new NotExistsException();
        }
        destRepo.delete(dest);
    }

}
