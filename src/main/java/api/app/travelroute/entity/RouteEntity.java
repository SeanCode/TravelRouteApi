package api.app.travelroute.entity;

import api.base.common.OutputEntityJsonView;
import api.base.common.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cc on 2017/4/16.
 */
@Entity
@Table(name = "route", schema = "travel_grad", catalog = "")
public class RouteEntity {
    private long id;
    private long destId = 0;
    private String name = "";
    private double price = 0.00;
    private String intro = "";
    private String info = "";
    private String notice = "";
    private String img = "";
    private long createTime = 0;
    private long updateTime = 0;

    private List<String> imgList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dest_id", nullable = false)
    @JsonProperty("dest_id")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public long getDestId() {
        return destId;
    }

    public void setDestId(long destId) {
        this.destId = destId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 199)
    @JsonProperty("name")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    @JsonProperty("price")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "intro", nullable = false, length = 199)
    @JsonProperty("intro")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "info", nullable = false, length = 255)
    @JsonProperty("info")
    @JsonView({OutputEntityJsonView.Detail.class})
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "notice", nullable = false, length = 255)
    @JsonProperty("notice")
    @JsonView({OutputEntityJsonView.Detail.class})
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "img", nullable = false, length = 255)
    @JsonProperty("img")
    @JsonView({OutputEntityJsonView.Basic.class})
    public String getImg() {

        imgList = Util.explodeUrlString(img);
        return imgList.size() > 0 ? imgList.get(0) : "";
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    @JsonProperty("create_time")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    @JsonProperty("update_time")
    @JsonView({OutputEntityJsonView.Basic.class, OutputEntityJsonView.Detail.class})
    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Transient
    @JsonProperty("img_list")
    @JsonView({OutputEntityJsonView.Detail.class})
    public List<String> getImgList() {
        imgList = Util.explodeUrlString(img);
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEntity that = (RouteEntity) o;

        if (id != that.id) return false;
        if (destId != that.destId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (createTime != that.createTime) return false;
        if (updateTime != that.updateTime) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (intro != null ? !intro.equals(that.intro) : that.intro != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (notice != null ? !notice.equals(that.notice) : that.notice != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (destId ^ (destId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        return result;
    }
}
